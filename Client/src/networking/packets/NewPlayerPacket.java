package networking.packets;

import java.util.Arrays;

/**
 * Informe de l’arrivé d’un nouveau joueur ou le passage à prêt de celui-ci.
 */
public class NewPlayerPacket extends BasePacket {

    /**
     * Le joueur vient t'il de se connecté ou de se mettre ready ?
     */
    public enum action {
        CONNECTED,
        READY
    }

    /**
     * Joueurs en question
     */
    public EntityInfo playerWhosReady;

    /**
     * Liste des autres joueurs pour mettre à jour
     */
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
