package networking;

import networking.packets.EntityInfo;

/**
 * Created by diogo on 6/10/16.
 */
public interface KView {

    /**
     *
     * @param players
     */
    void onPlayersPosReceived(EntityInfo[] players);

    /**
     *
     * @return
     */
    EntityInfo getPlayerInfo();

    /**
     *
     * @param players
     */
    void onNewPlayerConnected(EntityInfo[] players);


    /**
     *
     * @param player
     */
    void onPlayerReady(EntityInfo player);


    /**
     *
     * @param chairsIndex
     */
    void onTimeToShowChairs(int[] chairsIndex);

    /**
     *
     * @param index
     */
    void onChairTaken(int index);

    /**
     *
     */
    void onGameStart();

    /**
     *
     * @param players
     */
    void onGameEnd(EntityInfo[] players);

    /**
     *
     */
    void onServerFull();


    /**
     *
     */
    void onServerAlreadyInGame();

    /**
     *
     * @param player
     */
    void onPlayerDisconnected(EntityInfo player);


    /**
     *
     */
    void exit();
}
