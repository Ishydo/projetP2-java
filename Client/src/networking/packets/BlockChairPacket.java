package networking.packets;

/**
 * Created by diogo on 09.05.16.
 */
public class BlockChairPacket extends BasePacket {
    public int chairIndex;


    public BlockChairPacket(){}

    public BlockChairPacket(OnChairPacket onChairPacket){
        this.chairIndex = onChairPacket.chairIndex;
    }
}
