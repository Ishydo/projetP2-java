package networking;

import networking.packets.EntityInfo;

/**
 * Created by diogo on 6/10/16.
 */
public interface KView {
    void onPlayersPosReceived(EntityInfo[] players);
    EntityInfo getPlayerInfo();
}
