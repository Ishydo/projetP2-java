package networking.States.ServerStates;

import networking.KBaseApp;
import networking.States.IState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by diogo on 09.05.16.
 */
public class ServerGameEndState implements IState {
    @Override
    public void handleState(KBaseApp context) {
        context.getEndPoint().addListener(new Listener(){
            @Override
            public void received(Connection connection, Object o) {
                super.received(connection, o);
            }
        });
    }
}
