package map;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Created by Bryan on 23.05.2016.
 */
public class Player extends BaseCharacter {
    boolean UP, DOWN, RIGHT, LEFT;
    ImageView image;
    double x; double y;
    int speed;
    parseMap m;
    ArrayList<Wall> walls;

    public Player(String name, double x, double y, ArrayList<Wall> walls, parseMap m) {
        super(name, x, y);
        this.m = m;
        this.walls = walls;
        setFill(Color.DARKOLIVEGREEN);
    }

    @Override
    public void move(){
        if (UP)
            setCenterY(getCenterY() - 5);
        if (DOWN)
            setCenterY(getCenterY() + 5);
        if (LEFT)
            setCenterX(getCenterX() - 5);
        if (RIGHT)
            setCenterX(getCenterX() + 5);


        Bounds bp = getBoundsInParent();
        for (StackPane s : walls) {
            Bounds bs = s.getBoundsInParent();
            if (bp.intersects(bs)) {
                moveBack();
            }
        }

    }

}
