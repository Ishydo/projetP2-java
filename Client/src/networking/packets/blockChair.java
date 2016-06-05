package networking.packets;

/**
 * Created by diogo on 09.05.16.
 */
public class blockChair extends BasePacket {
    private String chairName;

    public blockChair(String chairName) {
        this.chairName = chairName;
    }

    public String getChairName() {
        return chairName;
    }

    public void setChairName(String chairName) {
        this.chairName = chairName;
    }
}
