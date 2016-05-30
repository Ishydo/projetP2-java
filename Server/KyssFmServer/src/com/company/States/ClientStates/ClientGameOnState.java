package com.company.States.ClientStates;

import com.company.KBaseApp;
import com.company.KServer;
import com.company.States.IState;
import com.company.packets.BasePacket;
import com.company.packets.EntityInfo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by diogo on 5/23/16.
 */
public class ClientGameOnState implements IState {


    private boolean initied = false;


    @Override
    public void handleState(KBaseApp context) {

        if(!initied) {
            context.getEndPoint().addListener(new Listener() {
                @Override
                public void received(Connection connection, Object o) {
                    System.out.println(o);
                }
            });
        }

        BasePacket p = new BasePacket();
        p.player = new EntityInfo();
        p.player.x = 69;
        p.player.y = 69;
        ((Client)context.getEndPoint()).sendTCP(p);
    }

    private boolean isItTimeToShowChairs(){
        return false;
    }
}
