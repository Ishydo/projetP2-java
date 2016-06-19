package map;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Classe BaseCharacter utilisé pour la création du
 * et des ennemies. Elle contient toutes les
 * caractéristiques que le joueur et les ennemies
 * partagent. Elle hérite de Circle car tous les joueurs
 * sont des ronds.
 */
public class BaseCharacter extends Circle{
    protected String name;
    protected int speed;
    protected Text text;
    protected double radius = 10;
    protected boolean ready = false;
    protected VBox label = new VBox();
    protected double startX;
    protected double startY;
    protected String uuid;

    /**
     * Constructeur de la classe BaseCharacter.
     * @param name : Nom du personnage.
     * @param uuid : UUID du personnage.
     * @param x : Sa position de départ en X.
     * @param y : Sa position de départ en Y.
     */
    public BaseCharacter(String name,String uuid, double x, double y){
        this.name = name;
        this.startX = x;
        this.startY = y;
        this.uuid = uuid;
        initialize();
    }

    /**
     * Méthode permettant d'initialiser le personnage.
     * C'est-à-dire le placer et créer le label du personnage.
     */
    private void initialize(){
        setCenterX(this.startX);
        setCenterY(this.startY);
        setRadius(radius);
        text = new Text(name);
        text.setFont(Font.font("Open Sans", FontWeight.BOLD, 15));
        text.setFill(Color.WHITE);
        label.setStyle("-fx-background-color: #000;-fx-padding: 1px;");
        label.getChildren().add(text);
        placeLabel();
    }

    /**
     * Méthode qui sera implémenté dans la classe Player,
     * c'est ici qu'on changera la position du joueur et qu'on
     * vérifiera la collision avec les murs.
     */
    public void move(){}

    /**
     * Méthode permettant de re-placer au départ le personnage
     * quand il touche un mur.
     */
    public void moveBack(){
        setCenterX(startX);
        setCenterY(startY);
        placeLabel();
    }

    /**
     * Méthode permettant de positionner le label du personnage
     * à la bonne place.
     */
    public void placeLabel(){
        label.setLayoutY(this.getCenterY()-40);
        label.setLayoutX(this.getCenterX()- (text.getLayoutBounds().getWidth()/2));
    }

    /**
     * Méthode permettant de savoir si le joueur est prêt ou non.
     * @return : True ou False.
     */
    public boolean getReady(){ return ready; }

    /**
     * Permet de récupérer le nom du personnage.
     * @return : Nom du personnage.
     */
    public String getName() {
        return name;
    }

    /**
     * Permet de changer le nom du joueur.
     * @param name : Nom du personnage.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Permet de récupérer la vitesse. (Pour plus tard si pouvoir)
     * @return : vitesse.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Permet de spécifier une vitesse.
     * @param speed : Vitesse du personnage.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Permet de récupérer le UUID du personnage.
     * @return : UUID du personnage.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Permet de changer le UUID du personnage.
     * @param uuid : UUID du personnage.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Permet de récupérer la position de départ du joueur en x.
     * @return : La position de départ en x.
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Permet de sépcifier une position en x de départ au personnage.
     * @param startX : Position en x.
     */
    public void setStartX(double startX) {
        this.startX = startX;
    }

    /**
     * Permet de récupérer la position de départ du joueur en y.
     * @return : La position de départ en y.
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Permet de sépcifier une position en y de départ au personnage.
     * @param startY: Position en y.
     */
    public void setStartY(double startY) {
        this.startY = startY;
    }
}
