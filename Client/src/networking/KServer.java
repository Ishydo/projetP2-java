package networking;

import networking.KListeners.KListenerServerNewGame;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Server;
import networking.packets.*;
import java.io.IOException;
import java.util.*;

/**
 * Implémentation serveur du module réseau
 */
public class KServer extends KBaseApp {

    /**
     * Serveur kryonet
     */
    private Server server;

    /**
     * Liste des joueurs dans la partie, la clef est l'uuid
     */
    private HashMap<String,EntityInfo> playersInfo = new HashMap<>();

    public static void main(String[] args) throws IOException {
	    new KServer(5555,5559);
    }

    public KServer(int tcpPort, int udpPort) throws IOException {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        init();
    }

    /**
     * démarre le serveur
     * @throws IOException
     */
    private void init() throws IOException {

        //les paramètres sont les tailles des buffers d'écriture et lecture
        server = new Server(100 * 16384,3 * 2048);

        //On récupère le sérialiseur
        kryoSerializer = server.getKryo();

        //On enregistre tout pour la serialisation
        serializeAll();

        //Etat de départ
        server.addListener(new KListenerServerNewGame(this));

        //Démmarage
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
