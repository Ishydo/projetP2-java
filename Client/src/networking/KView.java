package networking;

import networking.packets.EntityInfo;

/**
 * Created by diogo on 6/10/16.
 */
public interface KView {
    void onPlayersPosReceived(EntityInfo[] players);
    EntityInfo getPlayerInfo();
    void onNewPlayerConnected(EntityInfo[] players);
    void onPlayerReady(EntityInfo player);
    void onTimeToShowChairs(int[] chairsIndex);
    void onChairTaken(int index);
    void onGameStart();
    void onGameEnd(EntityInfo[] players);
    void onServerFull();
    void onServerAlreadyInGame();
    void onPlayerDisconnected(EntityInfo player);

    void exit();
}
