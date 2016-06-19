package networking.KListeners;

import com.esotericsoftware.kryonet.Connection;
import networking.KBaseApp;
import networking.packets.NewPlayerPacket;
import networking.packets.StatePacket;

/**
 * Etat de départ côté client, l'état s'occupe principalement d'envoyer un hello
 * et un ready puis d'attenre le changement d'état avec la configuration de la manche
 */
public class KListenerClientNewGame extends KAbstractListener {

    public KListenerClientNewGame(KBaseApp context) {
        super(context);
        client();
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);

        //Hello server, I'm a new client !
        StatePacket p = new StatePacket(clientContext.getView().getPlayerInfo(),StatePacket.states.HELLO);
        connection.sendTCP(p);
        System.out.println("HELLO PACKET SENT !");
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);

        //Changement d'état --> game on
        if (o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.GO_TO_ON_STS) {

            System.out.println("Passage en game on");

            StatePacket statePacket = (StatePacket) o;

            //On stocke la config de la manche
            context.getPlayedRounds().add(statePacket.roundInfo);

            //Et on change d'état
            context.getEndPoint().removeListener(this);
            context.getEndPoint().addListener(new KListenerClientGameOn(context));

        }

        //Nouveau joueur ou joueur ready
        else if(o instanceof NewPlayerPacket){

            NewPlayerPacket npp = (NewPlayerPacket) o;

            if(clientContext.getView() != null){

                //Nouveau joueur ?
                if(npp.playerAction == NewPlayerPacket.action.CONNECTED)
                    clientContext.getView().onNewPlayerConnected(npp.connectedPlayers);

                //Joueur passé en ready ?
                else if(npp.playerAction == NewPlayerPacket.action.READY)
                    clientContext.getView().onPlayerReady(npp.playerWhosReady);


            }
        }

        //Serveur est pleins :(
        else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.SRV_FULL){
            if(clientContext.getView() != null)
                clientContext.getView().onServerFull();
        }
        //Serveur est déjà en cours de jeu :(
        else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.SRV_ALREADY_IN_GAME){
            if(clientContext.getView() != null)
                clientContext.getView().onServerAlreadyInGame();
        }
        //Joueur deconnecté
        else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.DISCONNECT_ME){
            if(clientContext.getView() != null)
                clientContext.getView().onPlayerDisconnected(((StatePacket) o).player);
        }
    }
}
