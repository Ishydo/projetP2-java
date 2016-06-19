package map;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Classe Player héritant de BaseCharacter. C'est la classe
 * qui représente le joueur que l'on joue, donc nous-même.
 */
public class Player extends BaseCharacter {
    protected boolean UP, DOWN, RIGHT, LEFT;
    protected ImageView image;
    protected MapParsor m;
    protected ArrayList<Wall> walls;
    protected  ArrayList<Chair> chairs;
    public int score=0;

    /**
     * Constructeur du Player. Il aura une couleur différente
     * des autres joueurs, vert.
     * @param name : Nom du joueur.
     * @param uuid : UUID du joueur.
     * @param x : Position de départ du joueur en x.
     * @param y : Position de départ du joueur en y.
     * @param walls : Tableau contenant les Murs.
     * @param m : Instance du Parser de map.
     */
    public Player(String name,String uuid, double x, double y, ArrayList<Wall> walls, MapParsor m) {
        super(name,uuid, x, y);
        this.m = m;
        this.walls = walls;
        setFill(Color.DARKOLIVEGREEN);


    }

    /**
     * Implémentation de méthode défini dans la classe mère.
     * Gestion du déplacement et des collisions.
     */
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

        // Collision avec les murs.
        Bounds bp = getBoundsInParent();
        for (StackPane s : walls) {
            Bounds bs = s.getBoundsInParent();
            if (bp.intersects(bs)) {
                moveBack();
            }
        }
        // On replace le label correctement.
        placeLabel();
    }

    /**
     * Méthode permettant de savoir si le joueur
     * se trouve sur une chaise.
     * @return : True si le joueur est sur une chaise, sinon False.
     */
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

    /**
     * Méthode permettant d'arrêter le joueur.
     */
    public void stop(){
        this.DOWN = false;
        this.UP = false;
        this.RIGHT = false;
        this.LEFT = false;
    }

    /**
     * Méthode permettant de spécifier les chaises aux joueurs.
     * @param chairs : Tableau de Chair
     */
    public void setChairs(ArrayList<Chair> chairs) {
        this.chairs = chairs;
    }

    /**
     * Getter permettant de récupérer le tableau de chaises du joueur.
     * Non utilisé mais implémenté.
     * @return : Tableau de Chair
     */
    public ArrayList<Chair> getChairs() {
        return chairs;
    }
}
