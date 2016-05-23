package com.company.States.ClientStates;

import com.company.KBaseApp;
import com.company.States.IState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by diogo on 5/23/16.
 */
public class ClientGameEndState implements IState {
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
