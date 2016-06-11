package map;

import javafx.scene.layout.StackPane;

/**
 * Created by Bryan on 23.05.2016.
 */
public class Wall extends StackPane {

    double x; double y;
    public Wall(double x, double y){
        this.x = x;
        this.y = y;
        setLayoutX(this.x);
        setLayoutY(this.y);
        setWidth(16);
        setHeight(16);
    }
}
