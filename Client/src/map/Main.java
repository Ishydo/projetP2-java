package map;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.animation.FadeTransition;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

// ADD BUTTON TO BOTTOM
public class Main extends Application {


    public VBox thePlayersList;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /**
         * Music
         */
        AudioClip ac = new AudioClip(getClass().getClassLoader().getResource("music.mp3").toString());
        ac.play();


        // The playerlist instanciation
        thePlayersList = new VBox();

        Parent root = FXMLLoader.load(getClass().getResource("../resources/sample.fxml"));

        // Main app layout
        BorderPane mainLayout = new BorderPane();

        // The Top Bar (Main Actions)
        HBox menuTopBar = createMenuTopBar();

        Board board = new Board(this);
        // The Left Bar (Players)
        VBox leftBar = createLeftBar(board);


        // Pane central avec le jeu
        StackPane centerPane = new StackPane();
        Text intro = new Text("Welcome in the game !");
        centerPane.getChildren().addAll(board, intro);

        // Opacity du Board
        //board.setOpacity(0.5);

        // Transition pour le text
        /*FadeTransition ft = new FadeTransition(Duration.millis(3000), board);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();*/

        // Set MainLayout elements
        ///mainLayout.setTop(menuTopBar);
        mainLayout.setLeft(leftBar);
        mainLayout.setCenter(centerPane);

        // The scene taking the MainLayout
        Scene scene = new Scene(mainLayout, 1320, 800);

        scene.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> board.moveCircleOnKeyPress(event.getCode()));
        scene.addEventFilter(KeyEvent.KEY_RELEASED,
                event -> board.moveCircleOnKeyRelease(event.getCode()));

        primaryStage.setTitle("KYS FM - The Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Création du menu top du jeu
     *
     * @return
     */
    private HBox createMenuTopBar() {

        HBox menuTopBar = new HBox();

        menuTopBar.setPadding(new Insets(15, 12, 15, 12));
        menuTopBar.setSpacing(10); // Ecart entre les nodes
        menuTopBar.setStyle("-fx-background-color: #f39c12;");

        FxIconicsLabel iconGamepad =
                (FxIconicsLabel) new FxIconicsLabel.Builder(FxFontAwesome.Icons.faw_gamepad)
                        .size(30)
                        .color(MaterialColor.WHITE)
                        .build();

        Text roomName = new Text("eSport Special KYSFM Room");
        roomName.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        roomName.setFill(Color.WHITESMOKE);

        menuTopBar.getChildren().addAll(iconGamepad, roomName);

        return menuTopBar;
    }

    private VBox createLeftBar(Board board) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); // Set all sides to 10
        vbox.setSpacing(8);              // Gap between nodes

        // Use style classes to set properties previously set above
        //vbox.getStyleClass().addAll("pane", "vbox");

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
        // Afficher uniquement au tout début.
        Button btnReady = new Button("Prêt ?");
        btnReady.setTextFill(Color.WHITE);
        btnReady.setStyle("-fx-font-size:20px;-fx-background-color: #7f8c8d;");
        btnReady.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnReady.setText("Prêt !");
                btnReady.setStyle("-fx-font-size:20px;-fx-background-color: #40d47e;");
                // TODO : Gestion du passage en ready
                board.getNetClient().sendReady();
            }
        });
        vbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.getChildren().add(btnReady);

        return vbox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}