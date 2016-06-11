package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import networking.KBaseApp;
import networking.packets.EntityInfo;

/**
 * Created by Dom on 11.06.2016.
 */
public class KListenerClientGameEnd extends KAbstractListener {
    public KListenerClientGameEnd(KBaseApp context) {
        super(context);
        client();
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if(o instanceof EntityInfo[]){
            EntityInfo[] infos = (EntityInfo[]) o;
            if(clientContext.getView() != null){
                clientContext.getView().onGamEnd(infos);
            }
        }
    }
}
