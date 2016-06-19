package map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Classe Main de la partie cliente.
 * Création de la fenêtre.
 */
public class Main extends Application {

    public VBox thePlayersList;
    private Board board;


    /**
     * Méthode permettante de créer la fenêtre et initialiser
     * ses différentes partes.
     * @param primaryStage : Stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        thePlayersList = new VBox();

        Parent root = FXMLLoader.load(getClass().getResource("../resources/sample.fxml"));

        // Main app layout
        BorderPane mainLayout = new BorderPane();
        board = new Board(this);

        // The Left Bar (Players)
        VBox leftBar = createLeftBar(board);

        // Pane central avec le jeu et titre
        StackPane centerPane = new StackPane();
        TitlePane mainTitle = new TitlePane("Welcome in the game !", "Press 'r' or click on the ready button when you are ready.");
        centerPane.getChildren().addAll(board);

        mainLayout.setLeft(leftBar);
        mainLayout.setCenter(centerPane);

        Scene scene = new Scene(mainLayout, 1320, 800);
        scene.getStylesheets().add("/style.css");

        scene.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> board.moveCircleOnKeyPress(event.getCode()));
        scene.addEventFilter(KeyEvent.KEY_RELEASED,
                event -> board.moveCircleOnKeyRelease(event.getCode()));

        primaryStage.setTitle("KYS FM - The Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Méthode permettant de créer la partie gauche
     * de la fenêtre, c'est-à-dire l'endroit où
     * on aura la liste des joueurs avec leur score.
     * @param board : Instance du plateau de jeu.
     * @return : VBox qui se positonné à gauche.
     */
    private VBox createLeftBar(Board board) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); // Set all sides to 10
        vbox.setSpacing(8);              // Gap between nodes

        vbox.setStyle("-fx-background-color: #7f8c8d;");

        // Titre du jeu
        Text gameName = new Text("Kys FM");
        gameName.setFont(Font.font("Open Sans Extrabold", FontWeight.NORMAL, 25));
        gameName.setFill(Color.WHITESMOKE);

        // Nom de la room
        Text roomName = new Text("The Great Room Of Players");
        roomName.setFont(Font.font("Open Sans Light", FontWeight.NORMAL, 15));
        roomName.setFill(Color.WHITESMOKE);

        Separator separator = new Separator();

        // Titre joueurs
        Text playersTxt = new Text("Players");
        playersTxt.setFont(Font.font("Open Sans", FontWeight.BOLD, 25));
        playersTxt.setFill(Color.WHITESMOKE);

        vbox.getChildren().addAll(gameName, roomName, separator, playersTxt);
        vbox.getChildren().addAll(thePlayersList);
        vbox.setPrefWidth(250);

        /**
         * The ready button
         */
        Button btnReady = new Button("Prêt ?");
        btnReady.setTextFill(Color.WHITE);
        btnReady.setStyle("-fx-font-size:20px;-fx-background-color: #000;");


        btnReady.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnReady.setText("Prêt !");
                btnReady.setStyle("-fx-font-size:20px;-fx-background-color: #40d47e;");
                board.getNetClient().sendReady();
            }
        });


        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.getChildren().add(btnReady);

        return vbox;
    }

    /**
     * Méthode lancant l'application entière.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Méthode pour fermer correctement l'application.
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if(board != null){
            board.exit();
        }
    }
}