package networking.States.ServerStates;

import networking.KBaseApp;
import networking.KServer;
import networking.States.IState;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import networking.packets.EntityInfo;
import networking.packets.RoundInfo;
import networking.packets.StatePacket;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by diogo on 09.05.16.
 */
public class ServerNewGameState implements IState {

    private boolean initied = false;

    @Override
    public void handleState(KBaseApp context) {
        if(!initied) {
            initied = true;
            RoundInfo currentRound = createGame();
            context.getPlayedRounds().add(currentRound);
            context.getEndPoint().addListener(new Listener() {
                @Override
                public void received(Connection connection, Object o) {
                    synchronized (context){
                        if (o instanceof StatePacket) {
                            StatePacket pr = (StatePacket)o;

                            if(pr.state == StatePacket.states.HELLO){
                                ((KServer) context).getPlayersInfo().put(pr.uuid, pr.player);
                                connection.sendTCP(currentRound);
                            }
                            else if(pr.state == StatePacket.states.READY){
                                ((KServer) context).getPlayersInfo().get(pr.uuid).ready = true;
                            }
                        }
                        if (areAllPlayersReady(context) && ((KServer) context).getPlayersInfo().size() >= 2) {
                            StatePacket p = new StatePacket(StatePacket.states.GO_TO_ON_STS);
                            ((Server) context.getEndPoint()).sendToAllTCP(p);
                            context.setCurrentState(new ServerGameOnState());
                            context.getEndPoint().removeListener(this);
                        }
                    }
                }
            });
        }
    }

    private boolean areAllPlayersReady(KBaseApp context){
        if (0 == ((KServer) context)
                .getPlayersInfo()
                .entrySet()
                .stream()
                .filter(stringEntityInfoEntry -> stringEntityInfoEntry.getValue().ready == false)
                .toArray()
                .length) return true;
        else return false;
    }

    private RoundInfo createGame(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);
        return new RoundInfo("First",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(calendar.getTime()),new EntityInfo[]{
                new EntityInfo(),
                new EntityInfo(),
        });
    }
}
