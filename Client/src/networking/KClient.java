package networking;

import networking.States.ClientStates.ClientNewGameState;
import networking.callbacks.IOnPlayerPosReceived;
import networking.callbacks.IOnShowChairs;
import networking.packets.*;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import javax.swing.JOptionPane;
import java.io.IOException;

/**
 * Created by diogo on 5/23/16.
 */
public class KClient extends KBaseApp {


    public static EntityInfo playerInfo = new EntityInfo();

    private Client client;

    public static long timeDelta;

    private IOnPlayerPosReceived onEnnemiesPosReceived;

    private IOnShowChairs onShowChairs;

    public static void main(String[] args) throws IOException {
        new KClient(5555,5559);
    }

    public KClient(int tcpPort, int udpPort) throws IOException {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        onEnnemiesPosReceived = pos -> System.out.println("DATA FROM SERVER " + pos);
        onShowChairs = chairs -> System.out.println("CHAIRS AT : " + chairs);
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
        client.start();
        client.connect(5000, JOptionPane.showInputDialog(null,"Entrez l'ip"), tcpPort, udpPort);
        endPoint = (EndPoint) client;
    }

    public void run(){
        currentState = new ClientNewGameState();
        while(!stop){
            currentState.handleState(this);
            sleep();
        }
    }

    public IOnPlayerPosReceived getOnEnnemiesPosReceived() {
        return onEnnemiesPosReceived;
    }

    public void setOnEnnemiesPosReceived(IOnPlayerPosReceived onEnnemiesPosReceived) {
        this.onEnnemiesPosReceived = onEnnemiesPosReceived;
    }

    public IOnShowChairs getOnShowChairs() {
        return onShowChairs;
    }

    public void setOnShowChairs(IOnShowChairs onShowChairs) {
        this.onShowChairs = onShowChairs;
    }
}
