package networking;

import networking.packets.EntityInfo;

/**
 * Interface permettant de communiquer entre le réseau
 * et le jeu. Le jeux implémente cette interface ou
 * les méthodes seront appeler par le thread réseau
 * avec des informations importantes suivant les events.
 */
public interface KView {

    /**
     * Appelé lorsque la positions des autres joueurs est
     * reçu.
     * @param players tableaux des joueurs en jeu
     */
    void onPlayersPosReceived(EntityInfo[] players);

    /**
     * Appeler par le thread réseau afin de connaitre
     * l'état actuel du joueur pour l'envoyer.
     * @return
     */
    EntityInfo getPlayerInfo();

    /**
     * Appelé lorsqu'un nouveau joueur est connecté, attention
     * tout les joueurs sont retourné pour mettre à jour l'affichage
     * @param players joueurs en salle de jeu
     */
    void onNewPlayerConnected(EntityInfo[] players);


    /**
     * Appelé lorsqu'un joueur est passé en mode prêt avant la partie
     * @param player joueur qui vient de passer prêt
     */
    void onPlayerReady(EntityInfo player);


    /**
     * Appelé lorsqu'il est temps d'afficher les chaises
     * @param chairsIndex indexes des chaises à afficher
     */
    void onTimeToShowChairs(int[] chairsIndex);

    /**
     * Appelé lorsqu'une chaise à été prise par un autre joueur
     * @param index index de la chaise prise
     */
    void onChairTaken(int index);

    /**
     * Début de jeu
     */
    void onGameStart();

    /**
     * Fin de jeu
     * @param players joueurs avec scores finale calculé
     */
    void onGameEnd(EntityInfo[] players);

    /**
     * Appelé lorsque le serveur ou on tente de se connecté est pleins
     */
    void onServerFull();


    /**
     * Appelé lorsque le serveur est déjà en jeu
     */
    void onServerAlreadyInGame();

    /**
     * Appelé lorsqu'un joueur est déconnecté
     * @param player joueur deconnecté
     */
    void onPlayerDisconnected(EntityInfo player);


    /**
     * Appeler en cas de fermeture de l'application
     */
    void exit();
}
