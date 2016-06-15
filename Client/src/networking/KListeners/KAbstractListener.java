package networking.KListeners;

import com.esotericsoftware.kryonet.Listener;
import networking.KBaseApp;
import networking.KClient;
import networking.KServer;

/**
 * Created by diogo on 6/10/16.
 */
public class KAbstractListener extends Listener {

    protected KBaseApp context;
    protected KClient  clientContext;
    protected KServer  serverContext;

    public KAbstractListener(KBaseApp context) {
        super();
        this.context = context;
    }
    protected void client(){
        clientContext = (KClient)context;
    }

    protected void server(){
        serverContext = (KServer)context;
    }
}
