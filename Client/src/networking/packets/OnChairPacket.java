package networking.packets;

/**
 * Informe le client qu’une chaise à été prise par un autre joueur
 */
public class OnChairPacket {
    public int chairIndex;
    public String clientTime;
    public EntityInfo playerOnChair;
}
