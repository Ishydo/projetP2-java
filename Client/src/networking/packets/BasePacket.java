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



    protected String serverTime;

    public BasePacket() {
        updateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public void updateTime(SimpleDateFormat formatter){
        this.serverTime = formatter.format(new Date());
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
