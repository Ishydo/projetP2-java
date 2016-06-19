package map;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import networking.KClient;
import networking.KView;
import networking.packets.EntityInfo;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Classe Board héritant de Pane et implémentant KView.
 * C'est l'une des classes centrale du projet côté client.
 * La classe Board implémente le plateau de jeu et gère
 * les différentes étapes du jeu côté client.
 */
public class Board extends Pane implements KView {
    private MapParsor m;
    private boolean freezePlayer = true;

    private Player player;

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private ArrayList<Chair> chairs = new ArrayList<>();

    private ArrayList<Point> spawns;

    private ArrayList<PlayerLine> allPlayers = new ArrayList<>();
    private Map<String, BaseCharacter> playersMap = new HashMap<>();

    private KClient netClient;
    private Main parent;

    private AudioClip music = new AudioClip(getClass().getClassLoader().getResource("music.mp3").toString());
    private AudioClip win = new AudioClip(getClass().getClassLoader().getResource("win.mp3").toString());

    private TitlePane startGameMsg = new TitlePane("Préparez-vous à jouer !", "En attente des joueurs pour la première partie...");
    private TitlePane newGameMsg = new TitlePane("Fin de la manche !", "En attente des joueurs pour la suivante...");
    private FadeTransition fadeNewGameMsgIn = new FadeTransition(Duration.millis(1000), newGameMsg);
    private FadeTransition fadeNewGameMsgOut = new FadeTransition(Duration.millis(1000), newGameMsg);
    private FadeTransition fadeStartGameMsgOut = new FadeTransition(Duration.millis(1000), startGameMsg);

    private int manche = 0;

    /**
     * Méthode permettant de remettre le joueur
     * à sa position de départ et enlever les chaises.
     * Appelée quand fin de manche.
     */
    public void reinit(){

        // Replace le joueur
        player.moveBack();

        // Efface les chaises
        Platform.runLater(() -> {
            getChildren().removeAll(chairs);
            chairs.clear();
        });
    }

    /**
     * Constructeur de la classe Board.
     * @param main : Instance de la classe Main d'où le board est créé.
     * @throws ParserConfigurationException
     */
    public Board(Main main) throws ParserConfigurationException {

        // Récupération du main pour mise à jour des éléments visuels
        this.parent = main;

        //Va lire le fichier et nous générer les tableaux pour nos éléments
        m = new MapParsor();

        // In and Out animations fade
        fadeNewGameMsgIn.setFromValue(0.0);
        fadeNewGameMsgIn.setToValue(1.0);
        fadeNewGameMsgOut.setFromValue(1.0);
        fadeNewGameMsgOut.setToValue(0.0);
        fadeStartGameMsgOut.setFromValue(1.0);
        fadeStartGameMsgOut.setToValue(0.0);

        //Donne un fond noir au board.
        this.setStyle("-fx-background-color: black;");

        //Affiche les murs.
        this.getChildren().add(new ImageView("/newmap2.png"));
        for (Point p : m.getTabSpawnWall()) {
            walls.add(new Wall(p.getX(),p.getY()));
        }
        spawns = m.getTabSpawnPlayers();

        player = new Player(JOptionPane.showInputDialog("votre nom ?"),KClient.uuid,100, 120, walls, m);

        this.getChildren().addAll(chairs);
        this.getChildren().addAll(enemies);
        this.getChildren().add(player);

        newGameMsg.setOpacity(0);
        this.getChildren().add(newGameMsg);
        this.getChildren().add(startGameMsg);

        this.getChildren().add(player.label);

        move();

        try {
            netClient = new KClient(5555,5559,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    /**
     * Méthode permettant de récupérer les entrées clavier
     * pour le déplacement des joueurs.
     * @param code : Touche entrée
     */
    public void moveCircleOnKeyPress(KeyCode code) {
        if (code == KeyCode.UP) {
            player.stop();
            player.UP = true;
        }
        if (code == KeyCode.DOWN) {
            player.stop();
            player.DOWN = true;
        }
        if (code == KeyCode.LEFT) {
            player.stop();
            player.LEFT = true;
        }
        if (code == KeyCode.RIGHT) {
            player.stop();
            player.RIGHT = true;
        }
    }

    /**
     * Méthode permettant de récupérer l'entrée clavier
     * du joueur pour savoir quand il est prêt.
     * @param code : touche relachée.
     */
    public void moveCircleOnKeyRelease(KeyCode code) {
        if(code == KeyCode.R){
            netClient.sendReady();
        }
    }

    /**
     * Méthode permettant de gérer le déplacement.
     * C'est-à-dire qu'on va crée un timer et on exécutera
     * toutes les x milisecondes, les tests pour savoir si
     * le joueur sur une chaise ainsi qu'appeller move() du
     * joueur pour qu'il se déplace. Mis-à-jour des joueurs.
     */
    private void move() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Chair c = player.isOnChair();
                    if(c != null && !c.isOccupied()){
                        netClient.sendChairTaken(chairs.indexOf(c));
                        freezePlayer = true;
                    }else{
                        if(!freezePlayer){
                            player.move();
                        }
                        for(Enemy e : enemies)
                            e.placeLabel();
                    }
                });

             }
        }, 0, 30);
    }

    /**
     * Méthode permettant de mettre à jour les ennemis.
     * @param players tableaux des joueurs en jeu
     */
    @Override
    public void onPlayersPosReceived(EntityInfo[] players) {
        Platform.runLater(() -> {
            for(EntityInfo ei : players){
                if(!player.getUuid().equals(ei.uuid)){
                    int index = enemies.indexOf(ei);
                    if(index != -1) {
                        enemies.get(index).moveTo(ei.x, ei.y);
                    }else {
                        Enemy e = new Enemy(ei.name,ei.uuid,ei.x,ei.y);
                        enemies.add(e);
                        this.getChildren().add(e);
                        this.getChildren().add(e.label);
                    }
                }
            }
        });
    }

    /**
     * Méthode permettant de récupérer un EntityInfo du joueur.
     * @return : EntityInfo.
     */
    @Override
    public EntityInfo getPlayerInfo() {
        EntityInfo e = new EntityInfo();
        e.name = player.getName();
        e.x = (float)player.getCenterX();
        e.y = (float)player.getCenterY();
        e.score = player.score;
        return e;
    }

    /**
     * Méthode appelée lorsque le joueur se connecte.
     * Initialisation des différents objets avec leurs spawn et score.
     * Initialisation des lignes dans classement.
     * @param player : Tableau d'EntityInfo des joueurs.
     */
    @Override
    public void onNewPlayerConnected(EntityInfo[] player) {
        allPlayers.clear();
        for(int i = 0; i < player.length; i++){
            if(player[i].uuid.equals(this.player.getUuid())){
                this.player.score = player[i].score;
                Point spawn = spawns.get(player[i].index);
                this.player.setCenterX(spawn.getX());
                this.player.setCenterY(spawn.getY());
                this.player.setStartX(spawn.getX());
                this.player.setStartY(spawn.getY());
                this.player.placeLabel();
            }
            PlayerLine np = new PlayerLine(player[i].name,player[i].uuid, "" + player[i].score, player[i].ready);
            allPlayers.add(np);
        }

        Platform.runLater(() -> updatePlayersList());

    }

    /**
     * Méthode appelée lorsqu'un joueur est prêt.
     * Mise à jour de la ligne d'affichage.
     * @param player joueur qui vient de passer prêt
     */
    @Override
    public void onPlayerReady(EntityInfo player) {
        for(int i = 0; i < allPlayers.size(); i++){
            if(allPlayers.get(i).uuid.equals(player.uuid)){
                PlayerLine np = new PlayerLine(player.name,player.uuid, "" + player.score, player.ready);
                allPlayers.remove(i);
                allPlayers.add(np);
            }
        }
        Platform.runLater(() -> updatePlayersList());
    }

    /**
     * Méthode appelée lorsqu'il faut afficher les chaises.
     * On arrête la musique puis on initialise les chaises.
     * @param chairsIndex indexes des chaises à afficher
     */
    @Override
    public void onTimeToShowChairs(int[] chairsIndex) {

        // On arrête la musique
        music.stop();

        ArrayList<Point> chairsList =  m.getTabSpawnChairs();
        for(int i = 0; i < chairsIndex.length; i++){
            chairs.add(new Chair(chairsList.get(chairsIndex[i]).getX(), chairsList.get(chairsIndex[i]).getY()));
        }
        Platform.runLater(() -> this.getChildren().addAll(chairs));
        player.setChairs(chairs);

    }

    /**
     * Méthode appelée lorsqu'une chaise est prise.
     * Petit effet musical et on spécifie que la chaise est occupée.
     * @param index index de la chaise prise
     */
    @Override
    public void onChairTaken(int index) {
        // Effet musical
        Platform.runLater(() -> win.play());
        chairs.get(index).setOccupied(true);
    }

    /**
     * Méthode appelée lorsque la partie commence.
     * On lance la musique et on affiche le bon message.
     * On "réactive" le joueur.
     */
    @Override
    public void onGameStart() {
        // Relance la musique
        manche++;
        music.play();
        System.out.println("Le jeu commence !");

       if(manche > 1){
            Platform.runLater(() -> fadeNewGameMsgOut.play());
        }else{
           Platform.runLater(() -> fadeStartGameMsgOut.play());
       }
        freezePlayer = false;
    }

    /**
     * Méthode appelée lorsqu'une manche se termine.
     * Freeze le joueur et réinitialise la partie.
     * @param players joueurs avec scores finale calculé
     */
    @Override
    public void onGameEnd(EntityInfo[] players) {
        freezePlayer = true;
        onNewPlayerConnected(players);
        Platform.runLater(() -> fadeNewGameMsgIn.play());
        reinit();
    }

    /**
     * Méthode appelée lorsqu'un joueur veut se connecter et que le serveur est plein.
     * Lui signifie que le serveur est plein et le déconnecte.
     */
    @Override
    public void onServerFull() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connexion refusé");
            alert.setContentText("Serveur pleins !");
            alert.show();
            alert.setOnCloseRequest(event -> {
                netClient.disconnectMe();
                System.exit(0);

            });
        });
    }

    /**
     * Si le joueur essaie de se connecter alors qu'une manche est en cours.
     * On lui signifie que la partie est en cours et il doit se reconncter plus tard.
     */
    @Override
    public void onServerAlreadyInGame() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connexion refusé");
            alert.setContentText("Serveur en cours de jeu !");
            alert.show();
            alert.setOnCloseRequest(event -> {
                netClient.disconnectMe();
                System.exit(0);

            });
        });
    }

    /**
     * Méthode appelée lorsqu'un joueur se déconnecte.
     * @param player joueur deconnecté
     */
    @Override
    public void onPlayerDisconnected(EntityInfo player) {
        Platform.runLater(() -> {
            allPlayers.removeIf(playerLine -> playerLine.uuid.equals(player.uuid));
            updatePlayersList();
        });

    }

    /**
     * Méthode permettant de quitter correctement le client.
     */
    @Override
    public void exit() {
        netClient.disconnectMe();
        netClient.getClient().stop();
        Platform.runLater(()->{
            music.stop();
            win.stop();
            System.exit(0);
        });
    }

    /**
     * Méthode qui va mettre à jour la listes des joueurs.
     */
    private void updatePlayersList(){
        parent.thePlayersList.getChildren().clear();
        for(int i = 0; i <= allPlayers.size()-1; i++){
            parent.thePlayersList.getChildren().add(allPlayers.get(i));
        }
    }

    /**
     * Getter du NetClient
     * @return : netClient
     */
    public KClient getNetClient() {
        return netClient;
    }

    /**
     * Setter du netclient
     * @param netClient
     */
    public void setNetClient(KClient netClient) {
        this.netClient = netClient;
    }
}
