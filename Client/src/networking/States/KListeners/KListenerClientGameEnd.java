package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import networking.KBaseApp;
import networking.packets.EntityInfo;
import networking.packets.StatePacket;

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
        System.out.println("Pute" + o.toString());
        if(o instanceof EntityInfo[]){
            EntityInfo[] infos = (EntityInfo[]) o;
            if(clientContext.getView() != null){
                clientContext.getView().onGameEnd(infos);
            }
        }else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.GO_TO_NEW_STS){
            System.out.println("Etat replay");

            context.getEndPoint().removeListener(this);
            context.getEndPoint().addListener(new KListenerClientNewGame(context));
        }
    }
}
