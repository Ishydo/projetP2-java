package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import networking.packets.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Classe abstraite contenant le port UDP et TCP utilisé,
 * certaines variables importantes comme le max de joueurs,
 * les manches jouées, la manche actuelle et s’occupe de faire
 * les registers des classes envoyé à travers le réseau.
 */
public abstract class KBaseApp  {

    protected int tcpPort;

    protected int udpPort;

    /**
     * Manches joués et actuel (dernière)
     */
    protected ArrayList<RoundInfo> playedRounds = new ArrayList<>();

    /**
     * EndPoint kryonet permettant d'ajouter des listeners
     */
    protected EndPoint endPoint;

    /**
     * Serialiseurs kryo
     */
    protected Kryo kryoSerializer;


    /**
     * Identifiant unique, utile pour différencié les clients et serveur
     */
    public static String uuid = UUID.randomUUID().toString();


    private final int MAX_PLAYERS = 7;


    /**
     * Enregistre toutes les classes envoyé dans le réseau
     */
    protected void serializeAll(){
        if(kryoSerializer != null){
            kryoSerializer.register(String.class);
            kryoSerializer.register(BasePacket.class);
            kryoSerializer.register(BlockChairPacket.class);
            kryoSerializer.register(EntityInfo.class);
            kryoSerializer.register(OnChairPacket.class);
            kryoSerializer.register(RoundInfo.class);
            kryoSerializer.register(StatePacket.class);
            kryoSerializer.register(EntityInfo[].class);
            kryoSerializer.register(StatePacket.states.class);
            kryoSerializer.register(NewPlayerPacket.class);
            kryoSerializer.register(NewPlayerPacket.action.class);
            kryoSerializer.register(int[].class);
        }
    }


    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public ArrayList<RoundInfo> getPlayedRounds() {
        return playedRounds;
    }

    public void setPlayedRounds(ArrayList<RoundInfo> playedRounds) {
        this.playedRounds = playedRounds;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public Kryo getKryoSerializer() {
        return kryoSerializer;
    }

    public void setKryoSerializer(Kryo kryoSerializer) {
        this.kryoSerializer = kryoSerializer;
    }

    public int getMAX_PLAYERS() {
        return MAX_PLAYERS;
    }

    public RoundInfo getCurrentRound(){
        return playedRounds.get(playedRounds.size()-1);
    }
}
