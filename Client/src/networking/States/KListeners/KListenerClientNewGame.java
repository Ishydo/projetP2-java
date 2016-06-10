package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import networking.KBaseApp;
import networking.packets.NewPlayerPacket;
import networking.packets.RoundInfo;
import networking.packets.StatePacket;

/**
 * Created by diogo on 6/10/16.
 */
public class KListenerClientNewGame extends KAbstractListener {

    public KListenerClientNewGame(KBaseApp context) {
        super(context);
        client();
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        StatePacket p = new StatePacket(clientContext.getView().getPlayerInfo(),StatePacket.states.HELLO);
        connection.sendTCP(p);
        System.out.println("HELLO PACKET SENDED !");
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if (o instanceof RoundInfo) {
            context.getPlayedRounds().add((RoundInfo) o);
        } else if (o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.GO_TO_ON_STS) {
            System.out.println("Passage en game on");
            context.getEndPoint().removeListener(this);
            context.getEndPoint().addListener(new KListenerClientGameOn(context));
        }else if(o instanceof NewPlayerPacket){
            NewPlayerPacket npp = (NewPlayerPacket) o;
            if(clientContext.getView() != null){
                if(npp.playerAction == NewPlayerPacket.action.CONNECTED)
                    clientContext.getView().onNewPlayerConnected(npp.connectedPlayers);
                else if(npp.playerAction == NewPlayerPacket.action.READY)
                    clientContext.getView().onPlayerReady(npp.playerWhosReady);
            }
        }

    }
}
