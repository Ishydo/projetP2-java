package networking;

import map.Player;
import networking.States.KListeners.KListenerClientNewGame;
import networking.packets.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import javax.swing.JOptionPane;
import java.io.IOException;

/**
 * Created by diogo on 5/23/16.
 */
public class KClient extends KBaseApp {


    private KView view;

    private EntityInfo player = new EntityInfo();

    private Client client;

    public static long timeDelta;

    private boolean updateState = false;


    public static void main(String[] args) throws IOException {
        new KClient(5555,5559,null);
    }

    public KClient(int tcpPort, int udpPort,KView view) throws IOException {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        this.view = view;
        init();
    }

    private void init() throws IOException {
        client = new Client();
        kryoSerializer = client.getKryo();
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
        client.addListener(new KListenerClientNewGame(this));
        client.start();
        client.connect(5000, JOptionPane.showInputDialog(null,"Entrez l'ip"), tcpPort, udpPort);
        endPoint = (EndPoint) client;
    }

    public void run(){
        /*while (!stop){
            if(view != null && updateState){
                player = view.getPlayerInfo();
                client.sendUDP(player);
            }
            sleep();
        }*/
    }

    public void sendReady(){
        client.sendTCP(new StatePacket(player,StatePacket.states.READY));
    }

    public KView getView() {
        return view;
    }

    public void setView(KView view) {
        this.view = view;
    }

    public EntityInfo getPlayer() {
        return player;
    }

    public void setPlayer(EntityInfo player) {
        this.player = player;
    }

    public boolean isUpdateState() {
        return updateState;
    }

    public void setUpdateState(boolean updateState) {
        this.updateState = updateState;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
