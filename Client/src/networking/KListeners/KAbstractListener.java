package networking.KListeners;

import com.esotericsoftware.kryonet.Listener;
import networking.KBaseApp;
import networking.KClient;
import networking.KServer;

/**
 * Listener de base contenant le contexte,
 * et soit un client ou serveur,
 * ceci pour implémenté le pattern state
 */
public class KAbstractListener extends Listener {

    protected KBaseApp context;
    protected KClient  clientContext;
    protected KServer  serverContext;

    public KAbstractListener(KBaseApp context) {
        super();
        this.context = context;
    }

    /**
     * retourne le contexte client
     */
    protected void client(){
        clientContext = (KClient)context;
    }

    /**
     * Retourne le contexte serveur
     */
    protected void server(){
        serverContext = (KServer)context;
    }
}
