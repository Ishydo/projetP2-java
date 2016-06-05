package map;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Bryan on 30.05.2016.
 */
public class BaseCharacter extends Circle{
    String name;
    double x; double y;
    int speed;
    double radius = 15f;
    Color color = Color.BLACK;

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


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
