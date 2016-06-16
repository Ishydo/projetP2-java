package networking.KListeners;

import networking.KBaseApp;
import networking.packets.EntityInfo;
import networking.packets.StatePacket;

import java.util.ArrayList;

/**
 * Created by Dom on 11.06.2016.
 */
public class KListenerServerGameEnd extends KAbstractListener {

    private static final int BASE_POINTS = 10;
    private ArrayList<EntityInfo> playersOnChairs;

    public KListenerServerGameEnd(KBaseApp context, ArrayList<EntityInfo> playersOnChairs) {
        super(context);
        this.playersOnChairs = playersOnChairs;
        server();
        calculateScore();
        serverContext.getServer().sendToAllTCP(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()]));
        serverContext.getServer().sendToAllTCP(new StatePacket(null, StatePacket.states.GO_TO_NEW_STS));
        serverContext.getPlayersInfo().values().stream().map(i -> i.ready = false);
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
