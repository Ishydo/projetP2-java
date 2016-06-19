package networking.packets;

import networking.KBaseApp;
import networking.KClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Packet de base de tout packet,
 * celui-ci contient le temps du server du dernier passage par le serveur et ainsi que le uuid du client.
 */
public class BasePacket {

    /**
     * identifiant unique de l'instance qui instancie cette classe
     */
    public String uuid = KBaseApp.uuid;

    /**
     * Dernier temps serveur connu
     */
    protected String serverTime;

    public BasePacket() {
        updateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * Maj temps serveur
     * @param formatter formatteur pour format la date au bon format,
     *                  à passer en argument car la création de celui-ci est couteux
     */
    public void updateTime(SimpleDateFormat formatter){
        this.serverTime = formatter.format(Calendar.getInstance().getTime());
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
