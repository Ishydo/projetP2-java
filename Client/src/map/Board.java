package map;


import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import networking.KClient;
import networking.packets.EntityInfo;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class Board extends Pane {
    private parseMap m;

    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Chair> chairs = new ArrayList<>();
    private KClient netClient;


    public Board() throws ParserConfigurationException {


        try {
            netClient = new KClient(5555,5559);
            netClient.start();
        } catch (Exception e) {
            System.out.println(e);
        }

        //Va lire le fichier et nous générer les tableaux pour nos éléments
        m = new parseMap();
        //Donne un fond noir au board.
        this.setStyle("-fx-background-color: black;");
        //Affiche les murs.
        this.getChildren().add(new ImageView("/background.png"));
        for (Point p : m.getTabSpawnWall()) {
            walls.add(new Wall(p.getX(),p.getY()));
        }

        for(Point p : m.getTabSpawnPlayers()){
            enemies.add(new Enemy("Enemy", p.getX(), p.getY()));
        }

        for(Point p : m.getTabSpawnChairs()){
            chairs.add(new Chair(p.getX(), p.getY()));
            System.out.println("AHHAH");
        }


        player = new Player(UUID.randomUUID().toString(),100, 100, walls, m);

        KClient.playerInfo.name = player.getName();
        KClient.playerInfo.x = (float)player.x;
        KClient.playerInfo.y = (float)player.y;

        this.getChildren().addAll(chairs);
        this.getChildren().addAll(enemies);
        this.getChildren().add(player);
        move(player);


        /**
         * Network callback
         */

        netClient.setOnEnnemiesPosReceived(pos -> Platform.runLater(() -> {
            for(EntityInfo ei : pos){
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
                System.out.println(ei);
            }
        }));

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
        if(code == KeyCode.R){
            player.ready = true;
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
    }

    private void move(Circle circle) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.move();

                synchronized (KClient.playerInfo){
                    KClient.playerInfo.name = player.getName();
                    KClient.playerInfo.x = (float)player.getCenterX();
                    KClient.playerInfo.y = (float)player.getCenterY();
                }

                    }
        }, 0, 50);
    }

}
