package com.company.States.ClientStates;

import com.company.KBaseApp;
import com.company.States.IState;
import com.company.packets.EntityInfo;
import com.company.packets.RoundInfo;
import com.company.packets.StatePacket;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import javax.swing.*;

/**
 * Created by diogo on 5/23/16.
 */
public class ClientNewGameState implements IState {

    private boolean initied = false;

    @Override
    public void handleState(KBaseApp context) {
        if(!initied) {
            initied = true;
            context.getEndPoint().addListener(new Listener() {
                @Override
                public void received(Connection connection, Object o) {
                    System.out.println(o);
                    if (o instanceof RoundInfo) {
                        context.getPlayedRounds().add((RoundInfo) o);
                        JOptionPane.showMessageDialog(null, "Attention, on va commencer la partie !");
                        StatePacket p = new StatePacket();
                        p.state = StatePacket.states.READY;
                        connection.sendTCP(p);
                    } else if (o.equals("gameon")) {
                        System.out.println("POUF JEUX DEMARER !");
                    }
                }
            });
            StatePacket p = new StatePacket();
            p.state = StatePacket.states.HELLO;
            ((Client)context.getEndPoint()).sendTCP(p);
        }
    }
}
