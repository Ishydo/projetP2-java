package networking.packets;

/**
 * nforme le joueur que le client à prise la chaise à l’index envoyé.
 */
public class BlockChairPacket extends BasePacket {
    public int chairIndex;


    public BlockChairPacket(){}

    public BlockChairPacket(OnChairPacket onChairPacket){
        this.chairIndex = onChairPacket.chairIndex;
    }
}
