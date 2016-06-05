package networking.packets;

/**
 * Created by diogo on 09.05.16.
 */
public class RoundInfo extends BasePacket {
    private String roundName;
    private String showChairsAt;
    private EntityInfo[] chairs;

    public RoundInfo(){}

    public RoundInfo(String roundName, String showChairsAt, EntityInfo[] chairs) {
        this.roundName = roundName;
        this.showChairsAt = showChairsAt;
        this.chairs = chairs;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getShowChairsAt() {
        return showChairsAt;
    }

    public void setShowChairsAt(String showChairsAt) {
        this.showChairsAt = showChairsAt;
    }

    public EntityInfo[] getChairs() {
        return chairs;
    }
}
