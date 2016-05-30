package com.company.States.ServerStates;

import com.company.KBaseApp;
import com.company.KServer;
import com.company.States.IState;
import com.company.packets.BasePacket;
import com.company.packets.EntityInfo;
import com.company.packets.PlayersPosition;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

/**
 * Created by diogo on 09.05.16.
 */
public class ServerGameOnState implements IState {

    private boolean initied = false;
    private KServer srv;
    @Override
    public void handleState(KBaseApp context) {
        if(!initied){
            initied = true;
            context.getEndPoint().addListener(new Listener(){
                @Override
                public void received(Connection connection, Object o) {
                    if(o instanceof BasePacket){
                        srv = (KServer)context;
                        BasePacket e = (BasePacket) o;
                        srv.getPlayersInfo().put(e.uuid,e.player);
                    }
                }
            });
        }

        if(srv != null){
            ((Server)context.getEndPoint())
                    .sendToAllTCP(
                            new PlayersPosition(
                                    srv.getPlayersInfo()
                                            .values()
                                            .toArray(
                                                    new EntityInfo[srv.getPlayersInfo().size()]
                                            )
                            )
                    );
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
