package networking.packets;

/**
 * Permet d’informer le client qu’il doit changer d’état (de listener)
 */
public class StatePacket extends BasePacket {

    public EntityInfo player;

    /**
     * Etat à envoyer
     */
    public enum states {
        HELLO,
        READY,
        GO_TO_ON_STS,
        GO_TO_END_STS,
        GO_TO_NEW_STS,
        SRV_FULL,
        SRV_ALREADY_IN_GAME,
        DISCONNECT_ME
    }

    public states state;

    /**
     * Si l'état est GO_TO_ON_STS,
     * on envoie la configuration de la partie avec
     */
    public RoundInfo roundInfo;

    public StatePacket(){}

    public StatePacket(EntityInfo player, states state) {
        super();
        this.player = player;
        this.state = state;
    }

    @Override
    public String toString() {
        return "StatePacket{" +
                "state=" + state +
                '}';
    }
}
