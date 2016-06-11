package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import networking.KBaseApp;
import networking.packets.EntityInfo;

import java.util.ArrayList;

/**
 * Created by Dom on 11.06.2016.
 */
public class KListenerServerGameEnd extends KAbstractListener {

    private ArrayList<EntityInfo> playersOnChairs;

    public KListenerServerGameEnd(KBaseApp context, ArrayList<EntityInfo> playersOnChairs) {
        super(context);
        this.playersOnChairs = playersOnChairs;
        server();
        calculateScore();
        serverContext.getServer().sendToAllTCP(serverContext.getPlayersInfo());
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
    }

    private void calculateScore(){
        for(int i=1; i <= playersOnChairs.size(); i++){
            playersOnChairs.get(i).score += i * 10;
        }
    }
}
