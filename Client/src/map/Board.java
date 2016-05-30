package map;


import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Board extends Pane {
    private parseMap m;

    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Chair> chairs = new ArrayList<>();


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

        for(Point p : m.getTabSpawnPlayers()){
            enemies.add(new Enemy("Enemy", p.getX(), p.getY()));
        }

        for(Point p : m.getTabSpawnChairs()){
            chairs.add(new Chair(p.getX(), p.getY()));
            System.out.println("AHHAH");
        }


        player = new Player("Dom",100, 100, walls, m);
        enemies.add(new Enemy("Bryan", 200, 200));

        this.getChildren().addAll(chairs);
        this.getChildren().addAll(enemies);
        this.getChildren().add(player);
        move(player);

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
    }

    private void move(Circle circle) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.move();
                    }
        }, 0, 50);
    }

}
