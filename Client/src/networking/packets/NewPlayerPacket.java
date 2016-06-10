package networking.packets;

import java.util.Arrays;

/**
 * Created by Dom on 10.06.2016.
 */
public class NewPlayerPacket extends BasePacket {

    public enum action {
        CONNECTED,
        READY
    }

    public EntityInfo playerWhosReady;
    public EntityInfo[] connectedPlayers;

    public action playerAction;

    public NewPlayerPacket(EntityInfo playerWhosReady) {
        this.playerAction = action.READY;
        this.playerWhosReady = playerWhosReady;
    }

    public NewPlayerPacket(EntityInfo[] connectedPlayers) {
        this.playerAction = action.CONNECTED;
        this.connectedPlayers = connectedPlayers;
    }

    public NewPlayerPacket() { }


    @Override
    public String toString() {
        return "NewPlayerPacket{" +
                "playerWhosReady=" + playerWhosReady +
                ", connectedPlayers=" + Arrays.toString(connectedPlayers) +
                ", playerAction=" + playerAction +
                '}';
    }
}
