package map;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Created by Bryan on 30.05.2016.
 */
public class Chair extends StackPane {
    double x; double y;
    boolean occupied = false;
    Pane p;
    public Chair(double x, double y){
        Rectangle r = new Rectangle(32, 32);
        r.setFill(Color.RED);
        p = new Pane();
        p.getStyleClass().add("chair");
        this.getChildren().addAll(r, p);
        r.widthProperty().bind(this.prefWidthProperty());
        r.heightProperty().bind(this.prefHeightProperty());
        this.setPrefSize(32, 32);
        this.setLayoutX(x-8);
        this.setLayoutY(y-8);
    }


    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;

        if(occupied){
            p.getStyleClass().add("occupied");
        }
    }
}
