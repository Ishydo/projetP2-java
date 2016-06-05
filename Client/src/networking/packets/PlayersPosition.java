package networking.packets;

/**
 * Created by diogo on 09.05.16.
 */


public class PlayersPosition extends BasePacket {
    private EntityInfo[] players;

    public PlayersPosition(EntityInfo[] players) {
        this.players = players;
    }

    public EntityInfo[] getPlayers() {
        return players;
    }
    public PlayersPosition(){}

    public void setPlayers(EntityInfo[] players) {
        this.players = players;
    }
}
