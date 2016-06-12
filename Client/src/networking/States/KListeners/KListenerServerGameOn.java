package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import networking.KBaseApp;
import networking.packets.EntityInfo;
import networking.packets.StatePacket;
import networking.packets.blockChair;
import networking.packets.onChairPacket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by diogo on 6/10/16.
 */
public class KListenerServerGameOn extends KAbstractListener {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    int chairsTaken = 0;
    ArrayList<EntityInfo> playersOnChair = new ArrayList<>();

    public KListenerServerGameOn(KBaseApp context) {
        super(context);
        server();
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        connection.sendTCP(new StatePacket(null, StatePacket.states.SRV_ALREADY_IN_GAME));
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if(o instanceof EntityInfo){
            EntityInfo e = (EntityInfo) o;
            e.updateTime(formatter);
            serverContext.getPlayersInfo().put(e.uuid,e);
            connection.sendTCP(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()]));
        }else if(o instanceof onChairPacket){
            onChairPacket chairPacket = (onChairPacket) o;
            blockChair blockChair = new blockChair();
            blockChair.chairIndex = chairPacket.chairIndex;
            serverContext.getServer().sendToAllTCP(blockChair);
            playersOnChair.add(chairPacket.playerOnChair);
            if(playersOnChair.size() >= serverContext.getPlayersInfo().size() - 1){
                System.out.println("Game finished");
                serverContext.getServer().sendToAllTCP(new StatePacket(null, StatePacket.states.GO_TO_END_STS));
                context.getEndPoint().removeListener(this);
                context.getEndPoint().addListener(new KListenerServerGameEnd(context, playersOnChair));
            }
        }
    }
}
