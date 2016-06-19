package networking.KListeners;

import com.esotericsoftware.kryonet.Connection;
import networking.KBaseApp;
import networking.KServer;
import networking.packets.EntityInfo;
import networking.packets.NewPlayerPacket;
import networking.packets.RoundInfo;
import networking.packets.StatePacket;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by diogo on 6/10/16.
 */
public class KListenerServerNewGame extends KAbstractListener {

    /**
     * Nombre total de chaises (a voir avec la map)
     */
    private static final int N_TOTAL_CHAIRS = 31;

    /**
     * Nombre minimum de joueurs pour lancer une partie
     */
    private static final int MIN_PLAYERS = 2;

    public KListenerServerNewGame(KBaseApp context) {
        super(context);
        server();
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        if(serverContext.getPlayersInfo().size() > serverContext.getMAX_PLAYERS()){
            System.out.println("FULL");
            connection.sendTCP(new StatePacket(null,StatePacket.states.SRV_FULL));
        }
    }

    @Override
    public void received(Connection connection, Object o) {
        super.received(connection, o);

        //Etats
        if (o instanceof StatePacket) {
            StatePacket pr = (StatePacket)o;

            //Nouveau joueur
            if(pr.state == StatePacket.states.HELLO){
                System.out.println("HELLO PACKET RECEIVED");
                pr.player.index = serverContext.getPlayersInfo().size();

                //On ajoute le joueur à notre liste
                serverContext.getPlayersInfo().put(pr.player.uuid,pr.player);

                //Informe les clients qu'un nouveau joueurs s'est connecté
                serverContext.getServer().sendToAllTCP(new NewPlayerPacket(serverContext.getPlayersInfo().values().toArray(new EntityInfo[serverContext.getPlayersInfo().size()])));
            }

            //Joueur prêt
            else if(pr.state == StatePacket.states.READY){
                System.out.println("READY RECEIVED");
                pr.player.ready = true;
                serverContext.getPlayersInfo().put(pr.player.uuid,pr.player);

                //Informe les clients qu'un joueur est prêt
                serverContext.getServer().sendToAllTCP(new NewPlayerPacket(pr.player));
            }
            //Joueur déco
            else if(((StatePacket) o).state == StatePacket.states.DISCONNECT_ME){
                serverContext.getServer().sendToAllExceptTCP(connection.getID(),o);
                serverContext.getPlayersInfo().remove(((StatePacket) o).uuid);
            }
        }

        //Joueurs tous prêts ? si oui on commence la partie !
        if (areAllPlayersReady() && ((KServer) context).getPlayersInfo().size() >= MIN_PLAYERS) {

            //Changement d'état pour les clients
            StatePacket p = new StatePacket(null,StatePacket.states.GO_TO_ON_STS);

            //On crée une manche
            RoundInfo currentRound = createGame();
            p.roundInfo = currentRound;

            //On envoie l'info à tout le monde
            serverContext.getServer().sendToAllTCP(p);

            //Changement d'état
            context.getEndPoint().removeListener(this);
            context.getEndPoint().addListener(new KListenerServerGameOn(context));
        }

        //Affiche les joueurs, pour du debug
        System.out.println(serverContext.getPlayersInfo());
    }

    /**
     * Est-ce que tout les joueurs sont prêts ?
     * @return
     */
    private boolean areAllPlayersReady(){
        return 0 == serverContext.getPlayersInfo()
                .entrySet()
                .stream()
                .filter(stringEntityInfoEntry -> stringEntityInfoEntry.getValue().ready == false)
                .toArray()
                .length;
    }

    /**
     * Créer le round
     * @return RoundInfo contenant les détails du round
     */
    private RoundInfo createGame(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 20);
        return new RoundInfo("First",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(calendar.getTime()),randomChairs()
        );
    }

    /**
     * Choix random des chaises à affiché
     * @return tableau d'indexes des chaises à afficher, les indexes sont par rapport à la map.
     */
    private int[] randomChairs(){
        ArrayList<Integer> chairIndexes = new ArrayList<>();
        for (int i = 0; i < N_TOTAL_CHAIRS ; i++){
            chairIndexes.add(new Integer(i));
        }
        Collections.shuffle(chairIndexes);
        return chairIndexes.subList(0,serverContext.getPlayersInfo().size()-1).stream().mapToInt(i -> i).toArray();
    }
}
