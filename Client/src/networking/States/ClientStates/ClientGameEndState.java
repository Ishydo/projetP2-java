package networking.States.ClientStates;

import networking.KBaseApp;
import networking.States.IState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by diogo on 5/23/16.
 */
public class ClientGameEndState implements IState {
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
