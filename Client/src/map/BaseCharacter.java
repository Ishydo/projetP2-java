package map;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.swing.*;

/**
 * Created by Bryan on 30.05.2016.
 */
public class BaseCharacter extends Circle{
    String name;
    double x; double y;
    int speed;
    VBox label = new VBox();
    Text text;
    double radius = 10;
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
        text = new Text(name);
        text.setFont(Font.font("Open Sans", FontWeight.BOLD, 15));
        text.setFill(Color.WHITE);
        label.setStyle("-fx-background-color: #000;-fx-padding: 1px;");
        label.getChildren().add(text);
        placeLabel();
    }

    public void move(){}

    public boolean isInWall(){
        return false;
    }

    public void moveBack(){
        setCenterX(x);
        setCenterY(y);
        placeLabel();
    }

    public void placeLabel(){
        label.setLayoutY(this.getCenterY()-40);
        label.setLayoutX(this.getCenterX()- (text.getLayoutBounds().getWidth()/2));
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
