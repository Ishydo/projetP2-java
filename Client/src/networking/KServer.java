package networking;

import networking.KListeners.KListenerServerNewGame;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import networking.packets.*;
import java.io.IOException;
import java.util.*;

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
        server = new Server(100 * 16384,3 * 2048);
        kryoSerializer = server.getKryo();
        serializeAll();
        server.addListener(new KListenerServerNewGame(this));
        server.start();
        server.bind(tcpPort, udpPort);
        endPoint = server;
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
