package map;

import javafx.scene.paint.Color;
import networking.packets.EntityInfo;

/**
 * Classe Enemy héritant de BaseCharacter. C'est
 * simplement le joueur adverse.
 */
public class Enemy extends BaseCharacter {

    /**
     * Constructeur d'Enemy, on va simplement appeler
     * la classe mère et spécifier une couleur différente
     * au cercle.
     * @param name : Nom de l'ennemi.
     * @param uuid : UUID de l'ennemi.
     * @param x : Position de départ en x.
     * @param y : Position de départ en y.
     */
    public Enemy(String name,String uuid, double x, double y){
        super(name, uuid,x, y);
        setFill(Color.ORANGE);
    }

    /**
     * Méthode permettant de placer l'ennemi.
     * @param x : Position en x.
     * @param y : Position en y.
     */
    public void moveTo(double x,double y){
        setCenterX(x);
        setCenterY(y);
    }

    /**
     * Méthode permettant de savoir si c'est un ennemi.
     * @param obj : Objet à tester.
     * @return : True ou False.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EntityInfo){
            return this.getName().equals(((EntityInfo) obj).name);
        }else{
            return super.equals(obj);
        }
    }

}
