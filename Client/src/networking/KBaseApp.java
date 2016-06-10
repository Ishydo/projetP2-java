package networking;

import com.esotericsoftware.kryonet.Listener;
import networking.States.IState;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import networking.packets.RoundInfo;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by diogo on 5/23/16.
 */
public abstract class KBaseApp  {
    protected int tcpPort;
    protected int udpPort;
    protected ArrayList<RoundInfo> playedRounds = new ArrayList<>();
    protected EndPoint endPoint;
    protected Kryo kryoSerializer;
    protected boolean stop = false;
    public static String uuid = UUID.randomUUID().toString();

    private final int MAX_PLAYERS = 12;

    public RoundInfo getCurrentRound(){
        return playedRounds.get(playedRounds.size()-1);
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

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int getMAX_PLAYERS() {
        return MAX_PLAYERS;
    }
}
