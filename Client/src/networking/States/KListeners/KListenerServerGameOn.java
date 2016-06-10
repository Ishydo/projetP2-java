package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import networking.KBaseApp;
import networking.packets.EntityInfo;

/**
 * Created by diogo on 6/10/16.
 */
public class KListenerServerGameOn extends KAbstractListener {
    public KListenerServerGameOn(KBaseApp context) {
        super(context);
        server();
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
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
            serverContext.getPlayersInfo().put(e.uuid,e);
            connection.sendUDP(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()]));
        }
    }

    @Override
    public void idle(Connection connection) {
        super.idle(connection);
    }
}
