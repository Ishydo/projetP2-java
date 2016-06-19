package networking;

import networking.KListeners.KListenerClientNewGame;
import networking.packets.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.Calendar;

/**
 * Implémente la partie cliente du réseau,
 * cette classes et utilisé et instancié dans le code graphique du jeu par example.
 */
public class KClient extends KBaseApp {

    /**
     * Interface pour communiquer avec la vue
     */
    private KView view;

    /**
     * Client kroynet
     */
    private Client client;

    /**
     * Delta différence entre temps client et serveur
     */
    public static long timeDelta;



    public static void main(String[] args) throws IOException {
        new KClient(5555,5559,null);
    }

    public KClient(int tcpPort, int udpPort,KView view) throws IOException {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        this.view = view;
        init();
    }

    /**
     * Démarre le client
     * @throws IOException
     */
    private void init() throws IOException {
        client = new Client(100 * 8192, 3*2048);
        kryoSerializer = client.getKryo();
        serializeAll();
        client.addListener(new KListenerClientNewGame(this));
        client.start();
        client.connect(5000, JOptionPane.showInputDialog(null,"Entrez l'ip"), tcpPort, udpPort);
        endPoint = (EndPoint) client;
    }

    /**
     * Envoye l'information que le joueur est prêt
     */
    public void sendReady(){
        if(view != null){
            client.sendTCP(new StatePacket(view.getPlayerInfo(),StatePacket.states.READY));
        }
    }

    /**
     * Envoye l'information qu'une chaise est prise
     * @param index index de la chaise
     */
    public void sendChairTaken(int index){

        OnChairPacket chairPacket = new OnChairPacket();

        chairPacket.chairIndex = index;

        chairPacket.playerOnChair = view.getPlayerInfo();

        Calendar timeout = Calendar.getInstance();

        timeout.add(Calendar.MILLISECOND,(int)-KClient.timeDelta);

        chairPacket.clientTime = timeout.getTime().toString();

        client.sendTCP(chairPacket);
    }

    /**
     * Informe le serveur qu'on se déconnecte
     */
    public void disconnectMe(){
        StatePacket statePacket = new StatePacket(view.getPlayerInfo(), StatePacket.states.DISCONNECT_ME);
        client.sendTCP(statePacket);
    }

    public KView getView() {
        return view;
    }

    public void setView(KView view) {
        this.view = view;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
