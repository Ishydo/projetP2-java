package networking.packets;

import map.Enemy;

/**
 * Représente un joueur, contient sa position, son score, s’il est prêt et bien sûr tout ce que contient BasePacket
 */
public class EntityInfo extends BasePacket {

    /**
     * Position x
     */
    public float x=0;

    /**
     * Position y
     */
    public float y=0;

    /**
     * Nom du joueur
     */
    public String name="";

    /**
     * Prêt ?
     */
    public boolean ready = false;

    /**
     * Score
     */
    public int score = 0;

    /**
     * Index pour la position de départ
     */
    public int index;

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
                "startX=" + x +
                ", startY=" + y +
                ", name='" + name + '\'' +
                ", ready=" + ready +
                ", score=" + score +
                '}';
    }
}
