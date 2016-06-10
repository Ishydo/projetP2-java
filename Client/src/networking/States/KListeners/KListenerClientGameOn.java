package networking.States.KListeners;

import com.esotericsoftware.kryonet.Connection;
import networking.KBaseApp;
import networking.KClient;
import networking.packets.EntityInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by diogo on 6/10/16.
 */
public class KListenerClientGameOn extends KAbstractListener {
    public KListenerClientGameOn(KBaseApp context) {
        super(context);
        client();
        clientContext.setUpdateState(true);
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);
        if(o instanceof EntityInfo[]){
            EntityInfo[] players = (EntityInfo[])o;
            if(clientContext.getView() != null){
                clientContext.getView().onPlayersPosReceived(players);
            }
            //Sync
            /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date parsedDate = null;
            boolean timeToShowChairs = false;
            try {
                parsedDate = formatter.parse(players[0].getServerTime());
                KClient.timeDelta = getDateDiff(parsedDate,new Date(), TimeUnit.MILLISECONDS);
                timeToShowChairs = checkChairs(formatter.parse(client.getCurrentRound().getShowChairsAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
        }
        connection.sendUDP(clientContext.getView().getPlayerInfo());
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
