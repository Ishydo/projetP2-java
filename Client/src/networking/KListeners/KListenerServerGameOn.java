package networking.KListeners;

import com.esotericsoftware.kryonet.Connection;
import networking.KBaseApp;
import networking.packets.EntityInfo;
import networking.packets.StatePacket;
import networking.packets.BlockChairPacket;
import networking.packets.OnChairPacket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
        if(serverContext.getPlayersInfo().size() < 2){
            System.out.println("Plus aucun joueur :( bye");
            System.exit(0);
        }
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if(o instanceof EntityInfo){
            EntityInfo e = (EntityInfo) o;
            e.updateTime(formatter);
            serverContext.getPlayersInfo().put(e.uuid,e);
            connection.sendTCP(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()]));
        }else if(o instanceof OnChairPacket){
            OnChairPacket onChairPacket = (OnChairPacket) o;
            BlockChairPacket blockChairPacket = new BlockChairPacket(onChairPacket);
            serverContext.getServer().sendToAllTCP(blockChairPacket);
            playersOnChair.add(onChairPacket.playerOnChair);
            if(playersOnChair.size() >= serverContext.getPlayersInfo().size() - 1){
                System.out.println("Game finished");
                serverContext.getServer().sendToAllTCP(new StatePacket(null, StatePacket.states.GO_TO_END_STS));
                context.getEndPoint().removeListener(this);
                context.getEndPoint().addListener(new KListenerServerGameEnd(context, playersOnChair));
            }
        }else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.DISCONNECT_ME){
            serverContext.getServer().sendToAllExceptTCP(connection.getID(),o);
            serverContext.getPlayersInfo().remove(((StatePacket) o).uuid);
        }
    }
}
