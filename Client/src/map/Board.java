package map;


import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
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

    private ArrayList<Point> spawns;

    // All players in the game
    private ArrayList<PlayerLine> allPlayers = new ArrayList<>();
    private Map<String, BaseCharacter> playersMap = new HashMap<>();

    private KClient netClient;
    private Main parent;

    private AudioClip music = new AudioClip(getClass().getClassLoader().getResource("music.mp3").toString());
    private AudioClip win = new AudioClip(getClass().getClassLoader().getResource("win.mp3").toString());

    private TitlePane startGameMsg = new TitlePane("Préparez-vous à jouer !", "En attente des joueurs pour la première partie...");
    private TitlePane newGameMsg = new TitlePane("Fin de la manche !", "En attente des joueurs pour la suivante...");
    private FadeTransition fadeNewGameMsgIn = new FadeTransition(Duration.millis(1000), newGameMsg);
    private FadeTransition fadeNewGameMsgOut = new FadeTransition(Duration.millis(1000), newGameMsg);
    private FadeTransition fadeStartGameMsgOut = new FadeTransition(Duration.millis(1000), startGameMsg);

    private int manche = 0;

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

        // In and Out animations fade
        fadeNewGameMsgIn.setFromValue(0.0);
        fadeNewGameMsgIn.setToValue(1.0);
        fadeNewGameMsgOut.setFromValue(1.0);
        fadeNewGameMsgOut.setToValue(0.0);
        fadeStartGameMsgOut.setFromValue(1.0);
        fadeStartGameMsgOut.setToValue(0.0);

        //Donne un fond noir au board.
        this.setStyle("-fx-background-color: black;");

        //Affiche les murs.
        this.getChildren().add(new ImageView("/newmap2.png"));
        for (Point p : m.getTabSpawnWall()) {
            walls.add(new Wall(p.getX(),p.getY()));
        }
        spawns = m.getTabSpawnPlayers();

        player = new Player(JOptionPane.showInputDialog("votre nom ?"),100, 120, walls, m);
        player.setUuid(KClient.uuid);

        this.getChildren().addAll(chairs);
        this.getChildren().addAll(enemies);
        this.getChildren().add(player);

        newGameMsg.setOpacity(0);
        this.getChildren().add(newGameMsg);
        this.getChildren().add(startGameMsg);

        this.getChildren().add(player.label);

        move(player);

        try {
            netClient = new KClient(5555,5559,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    public void moveCircleOnKeyPress(KeyCode code) {
        if (code == KeyCode.UP) {
            player.stop();
            player.UP = true;
        }
        if (code == KeyCode.DOWN) {
            player.stop();
            player.DOWN = true;
        }
        if (code == KeyCode.LEFT) {
            player.stop();
            player.LEFT = true;
        }
        if (code == KeyCode.RIGHT) {
            player.stop();
            player.RIGHT = true;
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
                    for(Enemy e : enemies)
                        e.placeLabel();
                }
             }
        }, 0, 30);
    }

    @Override
    public void onPlayersPosReceived(EntityInfo[] players) {
        Platform.runLater(() -> {
            for(EntityInfo ei : players){
                if(!player.getUuid().equals(ei.uuid)){
                    int index = enemies.indexOf(ei);
                    if(index != -1) {
                        enemies.get(index).moveTo(ei.x, ei.y);
                    }else {
                        Enemy e = new Enemy(ei.name,ei.x,ei.y);
                        e.setUuid(ei.uuid);
                        enemies.add(e);
                        this.getChildren().add(e);
                        this.getChildren().add(e.label);
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
            if(player[i].uuid.equals(this.player.getUuid())){
                this.player.score = player[i].score;
                Point spawn = spawns.get(player[i].index);
                this.player.setCenterX(spawn.getX());
                this.player.setCenterY(spawn.getY());
                this.player.setX(spawn.getX());
                this.player.setY(spawn.getY());
                this.player.placeLabel();
            }
            PlayerLine np = new PlayerLine(player[i].name,player[i].uuid, "" + player[i].score, player[i].ready);
            allPlayers.add(np);
        }

        Platform.runLater(() -> updatePlayersList());

    }

    @Override
    public void onPlayerReady(EntityInfo player) {
        for(int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).uuid.equals(player.uuid)){
                PlayerLine np = new PlayerLine(player.name,player.uuid, "" + player.score, player.ready);
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
        manche++;
        music.play();
        System.out.println("Le jeu commence !");

       if(manche > 1){
            Platform.runLater(() -> fadeNewGameMsgOut.play());
        }else{
           Platform.runLater(() -> fadeStartGameMsgOut.play());
       }


        freezePlayer = false;
    }

    @Override
    public void onGameEnd(EntityInfo[] players) {
        freezePlayer = true;
        onNewPlayerConnected(players);
        Platform.runLater(() -> fadeNewGameMsgIn.play());
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
