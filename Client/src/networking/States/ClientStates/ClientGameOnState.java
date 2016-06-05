package networking.States.ClientStates;

import networking.KBaseApp;
import networking.KClient;
import networking.States.IState;
import networking.packets.BasePacket;
import networking.packets.EntityInfo;
import networking.packets.PlayersPosition;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by diogo on 5/23/16.
 */
public class ClientGameOnState implements IState {

    private boolean initied = false;

    @Override
    public void handleState(KBaseApp context) {
        if(!initied) {
            initied = true;
            System.out.println("game on");
            context.getEndPoint().addListener(new Listener() {
                @Override
                public void received(Connection connection, Object o) {
                    if(o instanceof PlayersPosition){
                        KClient client = ((KClient)context);
                        synchronized (client){
                            PlayersPosition p = (PlayersPosition)o;
                            if(client.getOnEnnemiesPosReceived() != null){
                                client.getOnEnnemiesPosReceived().onReceived(p.getPlayers());
                            }

                            //Sync
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                            Date parsedDate = null;
                            boolean timeToShowChairs = false;
                            try {
                                parsedDate = formatter.parse(p.getServerTime());
                                KClient.timeDelta = getDateDiff(parsedDate,new Date(),TimeUnit.MILLISECONDS);
                                timeToShowChairs = checkChairs(formatter.parse(client.getCurrentRound().getShowChairsAt()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(timeToShowChairs){
                                client.getOnShowChairs().onShow(client.getCurrentRound().getChairs());
                                timeToShowChairs = false;
                            }
                        }
                    }
                }
            });
        }
        BasePacket p = new BasePacket();
        ((Client)context.getEndPoint()).sendTCP(p);
    }

    private boolean checkChairs(Date popDate){
        Calendar timeout = Calendar.getInstance();
        Calendar realTime = Calendar.getInstance();
        timeout.add(Calendar.MILLISECOND,(int)-KClient.timeDelta);
        if(timeout.getTime().getTime() - popDate.getTime() > 0){
            return true;
        }else{
            return false;
        }
    }

    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
