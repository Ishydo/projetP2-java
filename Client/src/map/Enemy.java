package map;

import javafx.scene.paint.Color;
import networking.packets.EntityInfo;

/**
 * Created by Bryan on 30.05.2016.
 */
public class Enemy extends BaseCharacter {
    public Enemy(String name, double x, double y){
        super(name, x, y);
        setFill(Color.RED);
    }


    public void moveTo(double x,double y){
        setCenterX(x);
        setCenterY(y);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EntityInfo){
            return this.getName().equals(((EntityInfo) obj).name);
        }else{
            return super.equals(obj);
        }
    }
}
