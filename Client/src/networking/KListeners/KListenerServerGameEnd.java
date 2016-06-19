package networking.KListeners;

import networking.KBaseApp;
import networking.packets.EntityInfo;
import networking.packets.StatePacket;

import java.util.ArrayList;

/**
 * Etat fin côté serveur
 */
public class KListenerServerGameEnd extends KAbstractListener {

    /**
     * Point de base suivant sa position dans le ranking
     */
    private static final int BASE_POINTS = 10;

    /**
     * Les joueurs qui sont sur une chaise
     */
    private ArrayList<EntityInfo> playersOnChairs;

    /**
     * Constructeurs
     * @param context contexte
     * @param playersOnChairs joueurs qui sont sur une chaise
     */
    public KListenerServerGameEnd(KBaseApp context, ArrayList<EntityInfo> playersOnChairs) {
        super(context);
        this.playersOnChairs = playersOnChairs;
        server();

        //Calcule de score
        calculateScore();

        //Envoie des scores aux clients
        serverContext.getServer().sendToAllTCP(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()]));

        //Et on demande aux clients de passer à l'état de départ
        serverContext.getServer().sendToAllTCP(new StatePacket(null, StatePacket.states.GO_TO_NEW_STS));

        //On n'oublie pas de remettre tout le monde en non-ready
        serverContext.getPlayersInfo().values().stream().map(i -> i.ready = false);

        //Et on change nous-même aussi d'état
        context.getEndPoint().addListener(new KListenerServerNewGame(context));
    }

    /**
     * Calcul du score des joueurs suivant quand ils ont pris une chaise
     */
    private void calculateScore(){
        for(int i=0; i < playersOnChairs.size(); i++){
            serverContext.getPlayersInfo().get(playersOnChairs.get(i).uuid).score += (playersOnChairs.size() - i) * BASE_POINTS;
        }
    }
}
