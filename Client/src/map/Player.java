package map;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
    ArrayList<Chair> chairs;
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

    public Chair isOnChair(){
        if(chairs != null){
            Bounds bp = getBoundsInParent();
            for(Chair r : chairs){
                Bounds br = r.getBoundsInParent();
                if(bp.intersects(br)){
                    return r;
                }
            }
        }
        return null;
    }


    public ArrayList<Chair> getChairs() {
        return chairs;
    }

    public void setChairs(ArrayList<Chair> chairs) {
        this.chairs = chairs;
    }
}
