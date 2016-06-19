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
 * Etat en cours de jeu côté serveur, on synchronise les positions
 */
public class KListenerServerGameOn extends KAbstractListener {

    /**
     * Formatter pour mettre le temps serveur des packets à jour
     */
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * Joueur qui sont sur une chaise
     */
    ArrayList<EntityInfo> playersOnChair = new ArrayList<>();

    public KListenerServerGameOn(KBaseApp context) {
        super(context);
        server();
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);

        //Le join in game n'est pas (encore) supporté !
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

            //Mise à jour temps serveur
            e.updateTime(formatter);

            //Mise à jour liste positions joueurs
            serverContext.getPlayersInfo().put(e.uuid,e);

            //Envoie des toutes les positions en retour
            connection.sendTCP(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()]));
        }else if(o instanceof OnChairPacket){

            //On as reçu l'information qu'une chaise est prise
            OnChairPacket onChairPacket = (OnChairPacket) o;

            //On créer un paquet pour informé les autres que la chaise est prise
            BlockChairPacket blockChairPacket = new BlockChairPacket(onChairPacket);
            serverContext.getServer().sendToAllTCP(blockChairPacket);

            //Stocke le joueur sur la chaise
            playersOnChair.add(onChairPacket.playerOnChair);

            //Toutes les chaises sont pris ?
            if(playersOnChair.size() >= serverContext.getPlayersInfo().size() - 1){
                System.out.println("Game finished");
                serverContext.getServer().sendToAllTCP(new StatePacket(null, StatePacket.states.GO_TO_END_STS));
                context.getEndPoint().removeListener(this);
                context.getEndPoint().addListener(new KListenerServerGameEnd(context, playersOnChair));
            }

        }else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.DISCONNECT_ME){
            //Joueur deconnecté ? On informe tout le monde, et on l'enlève
            serverContext.getServer().sendToAllExceptTCP(connection.getID(),o);
            serverContext.getPlayersInfo().remove(((StatePacket) o).uuid);
        }
    }
}
