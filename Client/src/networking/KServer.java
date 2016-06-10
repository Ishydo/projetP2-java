package networking;

import networking.States.KListeners.KListenerClientNewGame;
import networking.States.KListeners.KListenerServerNewGame;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import networking.packets.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class KServer extends KBaseApp {

    private Server server;
    private HashMap<String,EntityInfo> playersInfo = new HashMap<>();

    public static void main(String[] args) throws IOException {
	    new KServer(5555,5559);
    }

    public KServer(int tcpPort, int udpPort) throws IOException {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        init();
    }

    private void init() throws IOException {
        server = new Server();
        kryoSerializer = server.getKryo();
        kryoSerializer.register(String.class);
        kryoSerializer.register(BasePacket.class);
        kryoSerializer.register(blockChair.class);
        kryoSerializer.register(EntityInfo.class);
        kryoSerializer.register(onChairPacket.class);
        kryoSerializer.register(PlayersPosition.class);
        kryoSerializer.register(RoundInfo.class);
        kryoSerializer.register(StatePacket.class);
        kryoSerializer.register(EntityInfo[].class);
        kryoSerializer.register(StatePacket.states.class);
        kryoSerializer.register(NewPlayerPacket.class);
        kryoSerializer.register(NewPlayerPacket.action.class);
        kryoSerializer.register(int[].class);
        server.addListener(new KListenerServerNewGame(this));
        server.start();
        server.bind(tcpPort, udpPort);
        endPoint = (EndPoint)server;
    }


    public HashMap<String, EntityInfo> getPlayersInfo() {
        return playersInfo;
    }

    public void setPlayersInfo(HashMap<String, EntityInfo> playersInfo) {
        this.playersInfo = playersInfo;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
