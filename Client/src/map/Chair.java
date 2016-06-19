package map;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Classe Chair héritant de StackPane. Représente une chaise.
 */
public class Chair extends StackPane {
    protected double x;
    protected double y;
    protected boolean occupied = false;
    protected Pane p;

    /**
     * Constructeur de la classe Chair.
     * Place la chaise et l'initalise.
     * @param x : Position en x.
     * @param y : Position en y.
     */
    public Chair(double x, double y){
        Rectangle r = new Rectangle(32, 32);
        p = new Pane();
        p.getStyleClass().add("chair");
        this.getChildren().addAll(r, p);
        r.widthProperty().bind(this.prefWidthProperty());
        r.heightProperty().bind(this.prefHeightProperty());
        this.setPrefSize(32, 32);
        this.setLayoutX(x-8);
        this.setLayoutY(y-8);
    }

    /**
     * Méthode permettant de savoir si la chaise est occupée.
     * @return : True si la chaise est occupée, sinon False.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Permet de spécifier qu'une chaise est occupée.
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
