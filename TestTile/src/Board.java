
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;


import javax.xml.parsers.ParserConfigurationException;


public class Board extends Pane {
    private parseMap m;

    private boolean end = false;
    private int lives = 3;
    public int score = 0;
    private boolean startedMoving;
    private boolean inited;


    public Board() throws ParserConfigurationException
    {
        //Va lire le fichier et nous générer les tableaux pour nos éléments
        m = new parseMap();
        //Donne un fond noir au board.
        this.setStyle("-fx-background-color: black;");
        //Affiche les murs.
        this.getChildren().add(new ImageView("/img/background.png"));

        for(Point p: m.getTabSpawnWall()) {
            System.out.println("MUR");
        }
        startedMoving = inited = false;

    }

    /*
    * Méthode qui permet de "capturer" les touches entrées (touches directionnelles ici)
    * */
    public void handleKeyPressed(KeyCode keyCode)
    {
        startedMoving = true;

        switch (keyCode) {
            case UP:
                //pacman.setDirection(Sprite.Direction.UP);
                break;
            case DOWN:
                //pacman.setDirection(Sprite.Direction.DOWN);
                break;
            case LEFT:
                //pacman.setDirection(Sprite.Direction.LEFT);
                break;
            case RIGHT:
                //pacman.setDirection(Sprite.Direction.RIGHT);
                break;
            default:
                break;
        }
    }

}
