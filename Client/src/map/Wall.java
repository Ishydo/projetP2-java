package map;

import javafx.scene.layout.StackPane;

/**
 * Classe Wall héritant de StackPane.
 * Elle représente un mur.
 * Elle n'est pas affichée et sert uniquement
 * pour les collisions.
 */
public class Wall extends StackPane {

    double x; double y;

    /**
     * Constructeur de la classe Wall.
     * @param x : Position en X.
     * @param y : Position en Y.
     */
    public Wall(double x, double y){
        this.x = x;
        this.y = y;
        setLayoutX(this.x);
        setLayoutY(this.y);
        setWidth(16);
        setHeight(16);
    }
}
