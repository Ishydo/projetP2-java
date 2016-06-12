package networking.packets;

import map.Enemy;

/**
 * Created by diogo on 09.05.16.
 */
public class EntityInfo extends BasePacket {
    public float x=0;
    public float y=0;
    public String name="";
    public boolean ready = false;
    public int score = 0;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Enemy){
            return this.uuid.equals(((Enemy) obj).getUuid());
        }else{
            return super.equals(obj);
        }
    }


    @Override
    public String toString() {
        return "EntityInfo{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ", ready=" + ready +
                ", score=" + score +
                '}';
    }
}
