package networking.States.ClientStates;

import networking.KBaseApp;
import networking.States.IState;
import networking.packets.RoundInfo;
import networking.packets.StatePacket;
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
                    if (o instanceof RoundInfo) {
                        context.getPlayedRounds().add((RoundInfo) o);
                        JOptionPane.showMessageDialog(null, "Attention, on va commencer la partie !");
                        StatePacket p = new StatePacket(StatePacket.states.READY);
                        connection.sendTCP(p);
                    } else if (o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.GO_TO_ON_STS) {
                        System.out.println("Passage en game on");
                        context.setCurrentState(new ClientGameOnState());
                        context.getEndPoint().removeListener(this);
                    }
                }
            });
            StatePacket p = new StatePacket(StatePacket.states.HELLO);
            ((Client)context.getEndPoint()).sendTCP(p);
        }
    }
}
