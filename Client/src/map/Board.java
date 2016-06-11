package map;


import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import networking.KClient;
import networking.KView;
import networking.packets.EntityInfo;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Board extends Pane implements KView {
    private parseMap m;
    private boolean freezePlayer = false;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Chair> chairs = new ArrayList<>();
    private KClient netClient;


    public Board() throws ParserConfigurationException {



        //Va lire le fichier et nous générer les tableaux pour nos éléments
        m = new parseMap();
        //Donne un fond noir au board.
        this.setStyle("-fx-background-color: black;");
        //Affiche les murs.
        this.getChildren().add(new ImageView("/background.png"));
        for (Point p : m.getTabSpawnWall()) {
            walls.add(new Wall(p.getX(),p.getY()));
        }

        /*for(Point p : m.getTabSpawnPlayers()){
            enemies.add(new Enemy("Enemy", p.getX(), p.getY()));
        }*/




        player = new Player(JOptionPane.showInputDialog("votre nom ?"),100, 100, walls, m);



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
        }
        if (code == KeyCode.DOWN) {
            player.DOWN = true;
        }
        if (code == KeyCode.LEFT) {
            player.LEFT = true;
        }
        if (code == KeyCode.RIGHT) {
            player.RIGHT = true;
        }
    }

    public void moveCircleOnKeyRelease(KeyCode code) {
        if (code == KeyCode.UP) {
            player.UP = false;
        }
        if (code == KeyCode.DOWN) {
            player.DOWN = false;
        }
        if (code == KeyCode.LEFT) {
            player.LEFT = false;
        }
        if (code == KeyCode.RIGHT) {
            player.RIGHT = false;
        }

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
        }, 0, 50);
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
        return e;
    }

    @Override
    public void onNewPlayerConnected(EntityInfo[] player) {
        System.out.println(player.length);
    }

    @Override
    public void onPlayerReady(EntityInfo player) {
        System.out.println("READY : " + player.toString());
    }

    @Override
    public void onTimeToShowChairs(int[] chairsIndex) {
        ArrayList<Point> chairsList =  m.getTabSpawnChairs();
        for(int i = 0; i < chairsIndex.length; i++){
            chairs.add(new Chair(chairsList.get(chairsIndex[i]).getX(), chairsList.get(chairsIndex[i]).getY()));
        }

        Platform.runLater(() -> this.getChildren().addAll(chairs));

        player.setChairs(chairs);

    }

    @Override
    public void onChairTaken(int index) {
        chairs.get(index).setOccupied(true);
    }

    public KClient getNetClient() {
        return netClient;
    }

    public void setNetClient(KClient netClient) {
        this.netClient = netClient;
    }
}
