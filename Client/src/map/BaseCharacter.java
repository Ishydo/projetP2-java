package map;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.swing.*;

/**
 * Created by Bryan on 30.05.2016.
 */
public class BaseCharacter extends Circle{
    String name;
    double x; double y;
    int speed;
    double radius = 12;
    boolean ready = false;      // Joueur prêt ou non
    protected String uuid;

    public BaseCharacter(String name, double x, double y){
        this.name = name;
        this.x = x;
        this.y = y;
        initialize();
    }

    private void initialize(){
        setCenterX(this.x);
        setCenterY(this.y);
        setRadius(radius);
    }

    public void move(){}

    public boolean isInWall(){
        return false;
    }

    public void moveBack(){
        setCenterX(x);
        setCenterY(y);
    }


    public boolean getReady(){ return ready; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
