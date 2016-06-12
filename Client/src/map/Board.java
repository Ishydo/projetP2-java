package map;


import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import networking.KClient;
import networking.KView;
import networking.packets.EntityInfo;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;



public class Board extends Pane implements KView {
    private parseMap m;
    private boolean freezePlayer = true;

    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Chair> chairs = new ArrayList<>();

    // All players in the game
    private ArrayList<PlayerLine> allPlayers = new ArrayList<>();
    private Map<String, BaseCharacter> playersMap = new HashMap<>();

    private KClient netClient;
    private Main parent;

    private AudioClip music = new AudioClip(getClass().getClassLoader().getResource("music.mp3").toString());
    private AudioClip win = new AudioClip(getClass().getClassLoader().getResource("win.mp3").toString());

    // Relance la partie
    public void reinit(){

        // Replace le joueur
        player.setLayoutX(player.x);
        player.setLayoutY(player.y);

        // Efface les chaises
        Platform.runLater(() -> {
            getChildren().removeAll(chairs);
            chairs.clear();
        });
    }

    public Board(Main main) throws ParserConfigurationException {

        // Récupération du main pour mise à jour des éléments visuels
        this.parent = main;

        //Va lire le fichier et nous générer les tableaux pour nos éléments
        m = new parseMap();
        //Donne un fond noir au board.
        this.setStyle("-fx-background-color: black;");
        //Affiche les murs.
        this.getChildren().add(new ImageView("/newmap2.png"));
        for (Point p : m.getTabSpawnWall()) {
            walls.add(new Wall(p.getX(),p.getY()));
        }


        player = new Player(JOptionPane.showInputDialog("votre nom ?"),100, 120, walls, m);

        this.getChildren().addAll(chairs);
        this.getChildren().addAll(enemies);
        this.getChildren().add(player);
        move(player);

        try {
            netClient = new KClient(5555,5559,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    public void moveCircleOnKeyPress(KeyCode code) {
        if (code == KeyCode.UP) {
            player.UP = true;
            player.DOWN = false;
            player.LEFT = false;
            player.RIGHT = false;
        }
        if (code == KeyCode.DOWN) {
            player.DOWN = true;
            player.UP = false;
            player.LEFT = false;
            player.RIGHT = false;
        }
        if (code == KeyCode.LEFT) {
            player.LEFT = true;
            player.RIGHT = false;
            player.UP = false;
            player.DOWN = false;
        }
        if (code == KeyCode.RIGHT) {
            player.RIGHT = true;
            player.LEFT = false;
            player.UP = false;
            player.DOWN = false;
        }
    }

    public void moveCircleOnKeyRelease(KeyCode code) {

        if(code == KeyCode.R){
            netClient.sendReady();
        }
    }

    private void move(Circle circle) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Chair c = player.isOnChair();
                if(c != null && !c.isOccupied()){
                    netClient.sendChairTaken(chairs.indexOf(c));
                    freezePlayer = true;
                }else if(!freezePlayer){
                    player.move();
                }
             }
        }, 0, 30);
    }

    @Override
    public void onPlayersPosReceived(EntityInfo[] players) {
        Platform.runLater(() -> {
            for(EntityInfo ei : players){
                if(!player.name.equals(ei.name)){
                    int index = enemies.indexOf(ei);
                    if(index != -1) {
                        enemies.get(index).moveTo(ei.x, ei.y);
                    }else {
                        Enemy e = new Enemy(ei.name,ei.x,ei.y);
                        enemies.add(e);
                        this.getChildren().add(e);
                    }
                }
            }
        });
    }

    @Override
    public EntityInfo getPlayerInfo() {
        EntityInfo e = new EntityInfo();
        e.name = player.getName();
        e.x = (float)player.getCenterX();
        e.y = (float)player.getCenterY();
        e.score = player.score;
        return e;
    }

    @Override
    public void onNewPlayerConnected(EntityInfo[] player) {
        allPlayers.clear();
        for(int i = 0; i < player.length; i++){
            if(player[i].name.equals(this.player.name)){
                this.player.score = player[i].score;
            }
            PlayerLine np = new PlayerLine(player[i].name, "" + player[i].score, player[i].ready);
            allPlayers.add(np);
        }

        Platform.runLater(() -> updatePlayersList());

    }

    @Override
    public void onPlayerReady(EntityInfo player) {
        for(int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).playername.textProperty().getValue().equals(player.name)){
                PlayerLine np = new PlayerLine(player.name, "" + player.score, player.ready);
                allPlayers.remove(i);
                allPlayers.add(np);
            }
        }
        Platform.runLater(() -> updatePlayersList());
    }

    @Override
    public void onTimeToShowChairs(int[] chairsIndex) {

        // On arrête la musique
        music.stop();

        ArrayList<Point> chairsList =  m.getTabSpawnChairs();
        for(int i = 0; i < chairsIndex.length; i++){
            chairs.add(new Chair(chairsList.get(chairsIndex[i]).getX(), chairsList.get(chairsIndex[i]).getY()));
        }
        Platform.runLater(() -> this.getChildren().addAll(chairs));
        player.setChairs(chairs);

    }

    @Override
    public void onChairTaken(int index) {
        // Effet musical
        Platform.runLater(() -> win.play());
        chairs.get(index).setOccupied(true);
    }

    @Override
    public void onGameStart() {
        // Relance la musique
        music.play();
        System.out.println("Le jeu commence !");
        freezePlayer = false;
    }

    @Override
    public void onGameEnd(EntityInfo[] players) {
        onNewPlayerConnected(players);
        reinit();
    }

    private void updatePlayersList(){
        parent.thePlayersList.getChildren().clear();
        for(int i = 0; i <= allPlayers.size()-1; i++){
            parent.thePlayersList.getChildren().add(allPlayers.get(i));
        }
    }



    public KClient getNetClient() {
        return netClient;
    }

    public void setNetClient(KClient netClient) {
        this.netClient = netClient;
    }
}
