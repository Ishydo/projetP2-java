package com.company.States.ServerStates;

import com.company.KBaseApp;
import com.company.KServer;
import com.company.States.IState;
import com.company.packets.*;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.util.Date;

/**
 * Created by diogo on 09.05.16.
 */
public class ServerNewGameState implements IState {

    private boolean initied = false;

    @Override
    public void handleState(KBaseApp context) {
        if(!initied) {
            initied = true;
            context.getPlayedRounds().add(createGame());
            context.getEndPoint().addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    super.connected(connection);
                    System.out.println("HEY");
                    connection.sendTCP(context.getPlayedRounds().get(context.getPlayedRounds().size()-1));
                }

                @Override
                public void disconnected(Connection connection) {
                    super.disconnected(connection);
                }

                @Override
                public void received(Connection connection, Object o) {
                    if (o instanceof PlayerReady) {
                        BasePacket pr = (BasePacket) o;
                        ((KServer) context).getPlayersInfo().get(pr.uuid).ready = true;
                    } else if (o instanceof PlayerHello) {
                        BasePacket pr = (BasePacket) o;
                        ((KServer) context).getPlayersInfo().put(pr.uuid, pr.player);
                    }
                    if (areAllPlayersReady(context)) {
                        ((Server) context.getEndPoint()).sendToAllTCP("gameon");
                    }
                }
                @Override
                public void idle(Connection connection) {
                    super.idle(connection);
                }
            });
        }
    }

    private boolean areAllPlayersReady(KBaseApp context){
        return 0 == ((KServer)context)
                .getPlayersInfo()
                .entrySet()
                .stream()
                .filter(stringEntityInfoEntry -> stringEntityInfoEntry.getValue().ready == false)
                .toArray()
                .length;
    }

    private RoundInfo createGame(){
        return new RoundInfo("First",new Date().toString(),new EntityInfo[]{
                new EntityInfo(),
                new EntityInfo(),
        });
    }
}
