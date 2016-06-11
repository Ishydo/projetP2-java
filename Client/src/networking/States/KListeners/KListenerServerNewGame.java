package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.sun.deploy.util.ArrayUtil;
import networking.KBaseApp;
import networking.KServer;
import networking.packets.EntityInfo;
import networking.packets.NewPlayerPacket;
import networking.packets.RoundInfo;
import networking.packets.StatePacket;

import java.text.SimpleDateFormat;
import java.util.*;

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
        if(serverContext.getPlayersInfo().size() > serverContext.getMAX_PLAYERS()){
            System.out.println("FULL");
            connection.sendTCP(new StatePacket(null,StatePacket.states.SRV_FULL));
        }
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
                serverContext.getServer().sendToAllTCP(new NewPlayerPacket(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()])));
            }
            else if(pr.state == StatePacket.states.READY){
                System.out.println("READY RECEIVED");
                pr.player.ready = true;
                serverContext.getPlayersInfo().put(pr.player.uuid,pr.player);
                serverContext.getServer().sendToAllTCP(new NewPlayerPacket(pr.player));
            }
        }


        if (areAllPlayersReady() && ((KServer) context).getPlayersInfo().size() >= 2) {
            StatePacket p = new StatePacket(null,StatePacket.states.GO_TO_ON_STS);
            RoundInfo currentRound = createGame();
            p.roundInfo = currentRound;
            serverContext.getServer().sendToAllTCP(p);
            context.getEndPoint().removeListener(this);
            context.getEndPoint().addListener(new KListenerServerGameOn(context));
        }

        System.out.println(serverContext.getPlayersInfo());
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
        calendar.add(Calendar.SECOND, 20);
        return new RoundInfo("First",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(calendar.getTime()),randomChairs()
        );
    }

    private int[] randomChairs(){
        ArrayList<Integer> chairIndexes = new ArrayList<>();
        for (int i = 0; i < 31 ; i++){
            chairIndexes.add(new Integer(i));
        }
        Collections.shuffle(chairIndexes);
        return chairIndexes.subList(0,serverContext.getPlayersInfo().size()-1).stream().mapToInt(i -> i).toArray();
    }
}
