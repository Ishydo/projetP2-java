package networking.packets;

/**
 * Created by Dom on 10.06.2016.
 */
public class NewPlayerPacket extends BasePacket {

    public enum action {
        CONNECTED,
        READY
    }

    public EntityInfo connectedPlayer;

    public action playerAction;

    public NewPlayerPacket(EntityInfo connectedPlayer) {
        this.playerAction = action.CONNECTED;
        this.connectedPlayer = connectedPlayer;
    }

    public NewPlayerPacket(EntityInfo connectedPlayer, action playerAction) {
        this.connectedPlayer = connectedPlayer;
        this.playerAction = playerAction;
    }

    public NewPlayerPacket() { }
}
