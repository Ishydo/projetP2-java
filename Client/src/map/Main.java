package map;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// ADD BUTTON TO BOTTOM

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Bordure pour zone de jeu

        Parent root = FXMLLoader.load(getClass().getResource("../resources/sample.fxml"));

        // Main app layout
        BorderPane mainLayout = new BorderPane();

        // The Top Bar (Main Actions)
        HBox menuTopBar = createMenuTopBar();

        // The Left Bar (Players)
        VBox leftBar = createLeftBar();

        Board board = new Board();

        // Set MainLayout elements
        ///mainLayout.setTop(menuTopBar);
        mainLayout.setLeft(leftBar);
        mainLayout.setCenter(board);

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


    private VBox createLeftBar() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); // Set all sides to 10
        vbox.setSpacing(8);              // Gap between nodes

        // Use style classes to set properties previously set above
        //vbox.getStyleClass().addAll("pane", "vbox");

        vbox.setStyle("-fx-background-color: #f39c12;");

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

        // Noms des joueurs dans un tableau de labels
        Text players[] = new Text[]{
                new Text("Domdompow"),
                new Text("Diogoinou"),
                new Text("Shadow78"),
                new Text("TheGreat"),
                new Text("Domdompow"),
                new Text("Diogoinou"),
                new Text("Shadow78"),
                new Text("TheGreat"),
                new Text("Domdompow"),
                new Text("Diogoinou"),
                new Text("Shadow78"),
                new Text("TheGreat"),
                new Text("Domdompow"),
                new Text("Diogoinou"),
                new Text("Shadow78"),
                new Text("TheGreat")
        };

        Text scores[] = new Text[]{
                new Text("1236"),
                new Text("896"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0"),
                new Text("0")
        };


        // Boucle d'affichage des joueurs dans la barre de gauche
        for (int i = 0; i < players.length; i++) {

            // PlayerLine
            HBox playerline = new HBox(8);
            playerline.setMinHeight(20);

            // Username box
            HBox usernameBox = new HBox(8);
            usernameBox.setMinHeight(20);
            usernameBox.setPrefWidth(200);

            // Score Box
            HBox scoreBox = new HBox();
            scoreBox.setMinHeight(20);
            scoreBox.setAlignment(Pos.TOP_RIGHT);

            FxIconicsLabel icon =
                    (FxIconicsLabel) new FxIconicsLabel.Builder(FxFontAwesome.Icons.faw_user)
                            .size(20)
                            .color(MaterialColor.WHITE)
                            .build();

            // Set the text properties
            players[i].setFill(Color.WHITESMOKE);
            players[i].setFont(Font.font("Open Sans Extrabold", FontWeight.NORMAL, 18));

            // Score line
            scores[i].setFill(Color.WHITESMOKE);
            scores[i].setFont(Font.font("Open Sans Light", FontWeight.NORMAL, 18));
            scores[i].setTextAlignment(TextAlignment.RIGHT);

            usernameBox.getChildren().addAll(icon, players[i]);
            scoreBox.getChildren().add(scores[i]);

            playerline.getChildren().addAll(usernameBox, scoreBox);

            vbox.getChildren().addAll(playerline);

        }

        vbox.setPrefWidth(250);
        return vbox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}