import javafx.scene.paint.Color;

/**
 * Created by Bryan on 30.05.2016.
 */
public class Enemy extends BaseCharacter {

    public Enemy(String name, double x, double y){
        super(name, x, y);
        setFill(Color.RED);
    }
}
