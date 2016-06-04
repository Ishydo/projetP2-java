package com.company.callbacks;

import com.company.packets.EntityInfo;

/**
 * Created by diogo on 5/30/16.
 */
public interface IOnPlayerPosReceived {
    void onReceived(EntityInfo[] pos);
}
