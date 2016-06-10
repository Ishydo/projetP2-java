package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import networking.KBaseApp;
import networking.KServer;
import networking.States.ServerStates.ServerGameOnState;
import networking.packets.EntityInfo;
import networking.packets.RoundInfo;
import networking.packets.StatePacket;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by diogo on 6/10/16.
 */
public class KListenerServerNewGame extends KAbstractListener {
    public KListenerServerNewGame(KBaseApp context) {
        super(context);
        server();
    }


    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        RoundInfo currentRound = createGame();
        connection.sendTCP(currentRound);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if (o instanceof StatePacket) {
            StatePacket pr = (StatePacket)o;

            if(pr.state == StatePacket.states.HELLO){
                System.out.println("HELLO PACKET RECEIVED");
                serverContext.getPlayersInfo().put(pr.player.uuid,pr.player);
            }
            else if(pr.state == StatePacket.states.READY){
                System.out.println("READY RECEIVED");
                pr.player.ready = true;
                serverContext.getPlayersInfo().put(pr.player.uuid,pr.player);
            }
        }
        if (areAllPlayersReady() && ((KServer) context).getPlayersInfo().size() >= 2) {
            StatePacket p = new StatePacket(null,StatePacket.states.GO_TO_ON_STS);
            serverContext.getServer().sendToAllTCP(p);
            context.getEndPoint().removeListener(this);
            context.getEndPoint().addListener(new KListenerServerGameOn(context));
        }
        System.out.println(serverContext.getPlayersInfo());
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }
    private boolean areAllPlayersReady(){
        if (0 == serverContext.getPlayersInfo()
                .entrySet()
                .stream()
                .filter(stringEntityInfoEntry -> stringEntityInfoEntry.getValue().ready == false)
                .toArray()
                .length) return true;
        else return false;
    }
    private RoundInfo createGame(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);
        return new RoundInfo("First",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(calendar.getTime()),new EntityInfo[]{
                new EntityInfo(),
                new EntityInfo(),
        });
    }
}
