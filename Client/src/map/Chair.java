package map;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Created by Bryan on 30.05.2016.
 */
public class Chair extends javafx.scene.shape.Rectangle {
    double x; double y;
    boolean occupied = false;
    public Chair(double x, double y){
        this.x = x;
        this.y = y;
        setY(y);
        setX(x);
        setWidth(32);
        setHeight(32);
    }


    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;

        if(occupied){
            setFill(Color.RED);
        }else{
            setFill(Color.GRAY);
        }
    }
}
