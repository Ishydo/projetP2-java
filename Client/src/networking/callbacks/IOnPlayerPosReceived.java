package networking.callbacks;

import networking.packets.EntityInfo;

/**
 * Created by diogo on 5/30/16.
 */
public interface IOnPlayerPosReceived {
    void onReceived(EntityInfo[] pos);
}
