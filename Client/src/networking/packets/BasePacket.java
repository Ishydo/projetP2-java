package networking.packets;

import networking.KBaseApp;
import networking.KClient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diogo on 09.05.16.
 */
public class BasePacket {

    public String uuid = KBaseApp.uuid;

    public EntityInfo player = KClient.playerInfo;

    protected String serverTime;

    public BasePacket() {
        this.serverTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
