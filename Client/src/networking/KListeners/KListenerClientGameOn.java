package networking.KListeners;

import com.esotericsoftware.kryonet.Connection;
import networking.KBaseApp;
import networking.KClient;
import networking.packets.EntityInfo;
import networking.packets.StatePacket;
import networking.packets.BlockChairPacket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Etat jeu en cours côté client,
 * c'est ici que seront sync les positions et ainsi que les chaisis prises, quand les affichées etc..
 */
public class KListenerClientGameOn extends KAbstractListener {

    boolean chairShowed = false;

    /**
     * Formmatter pour le deltatime
     */
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public KListenerClientGameOn(KBaseApp context) {
        super(context);
        client();

        //Envoie du premier packet pour la sync!
        clientContext.getClient().sendTCP(clientContext.getView().getPlayerInfo());

        //On informe la vue que la partie à commencé
        clientContext.getView().onGameStart();
    }
    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);

        //Arrivé des positions des autres joueurs
        if(o instanceof EntityInfo[]){
            EntityInfo[] players = (EntityInfo[])o;
            if(clientContext.getView() != null){
                clientContext.getView().onPlayersPosReceived(players);
            }
            /**
             * Calcul deltaTime
             */
            Date parsedDate = null;
            try {

                parsedDate = formatter.parse(players[0].getServerTime());

                KClient.timeDelta = getDateDiff(parsedDate,new Date(), TimeUnit.MILLISECONDS);

                //Est-il l'heure d'afficher les chaises ??
                if(checkChairs(formatter.parse(clientContext.getCurrentRound().getShowChairsAt()))){
                    if(clientContext.getView() != null && !chairShowed){

                        //Si oui on informe la vue !
                        clientContext.getView().onTimeToShowChairs(clientContext.getCurrentRound().getChairs());
                        chairShowed = true;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //On doit bloquer une chaise qui est prise
        else if(o instanceof BlockChairPacket){
            if(clientContext.getView() != null){
                clientContext.getView().onChairTaken(((BlockChairPacket) o).chairIndex);
            }
        }
        //Partie terminé on doit aller à l'état fin
        else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.GO_TO_END_STS){
            System.out.println("Etat fin");
            context.getEndPoint().removeListener(this);
            context.getEndPoint().addListener(new KListenerClientGameEnd(context));
        }
        //Un joueur se déconnecte
        else if(o instanceof StatePacket && ((StatePacket) o).state == StatePacket.states.DISCONNECT_ME){
            if(clientContext.getView() != null)
                clientContext.getView().onPlayerDisconnected(((StatePacket) o).player);
        }

        //Et bien sûr, on envoie sa position en retour !
        connection.sendTCP(clientContext.getView().getPlayerInfo());
    }

    /**
     * Est-il temps d'affiché les chaises ?
     * @param popDate temps en question
     * @return true si on doit les affichées
     */
    private boolean checkChairs(Date popDate){
        Calendar timeout = Calendar.getInstance();
        timeout.add(Calendar.MILLISECOND,(int)-KClient.timeDelta);
        if(timeout.getTime().getTime() - popDate.getTime() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Différence en ms entre 2 dates
     * @param date1
     * @param date2
     * @param timeUnit
     * @return temps en millisecondes
     */
    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
