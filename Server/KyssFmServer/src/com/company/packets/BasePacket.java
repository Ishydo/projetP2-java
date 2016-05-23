package com.company.packets;

import com.company.KBaseApp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by diogo on 09.05.16.
 */
public abstract class BasePacket {

    public String uuid = KBaseApp.uuid;

    public EntityInfo player = new EntityInfo();

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
