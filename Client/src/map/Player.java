package map;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by Bryan on 23.05.2016.
 */
public class Player extends BaseCharacter {
    boolean UP, DOWN, RIGHT, LEFT;
    ImageView image;
    int speed;
    MapParsor m;
    ArrayList<Wall> walls;
    ArrayList<Chair> chairs;
    public int score=0;

    public Player(String name, double x, double y, ArrayList<Wall> walls, MapParsor m) {
        super(name, x, y);
        this.m = m;
        this.walls = walls;
        setFill(Color.DARKOLIVEGREEN);


    }

    @Override
    public void move(){
        if (UP)
            setCenterY(getCenterY() - 3);
        if (DOWN)
            setCenterY(getCenterY() + 3);
        if (LEFT)
            setCenterX(getCenterX() - 3);
        if (RIGHT)
            setCenterX(getCenterX() + 3);


        Bounds bp = getBoundsInParent();
        for (StackPane s : walls) {
            Bounds bs = s.getBoundsInParent();
            if (bp.intersects(bs)) {
                moveBack();
            }
        }
        placeLabel();
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

    public void stop(){
        this.DOWN = false;
        this.UP = false;
        this.RIGHT = false;
        this.LEFT = false;
    }


    public ArrayList<Chair> getChairs() {
        return chairs;
    }

    public void setChairs(ArrayList<Chair> chairs) {
        this.chairs = chairs;
    }
}
