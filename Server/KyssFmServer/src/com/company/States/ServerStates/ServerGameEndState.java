package com.company.States.ServerStates;

import com.company.KBaseApp;
import com.company.KServer;
import com.company.States.IState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by diogo on 09.05.16.
 */
public class ServerGameEndState implements IState {
    @Override
    public void handleState(KBaseApp context) {
        context.getEndPoint().addListener(new Listener(){
            @Override
            public void received(Connection connection, Object o) {
                super.received(connection, o);
            }
        });
    }
}
