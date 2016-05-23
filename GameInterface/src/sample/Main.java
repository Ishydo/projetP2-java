package sample;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import com.pepperonas.fxiconics.gmd.FxFontGoogleMaterial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Bordure pour zone de jeu

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // Main app layout
        BorderPane mainLayout = new BorderPane();

        // The Top Bar (Main Actions)
        HBox menuTopBar = createMenuTopBar();

        // The Left Bar (Players)
        VBox leftBar = createLeftBar();


        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #f39c12;");
        grid.setPadding(new Insets(50, 50, 50, 50));
        Text category = new Text("Gamezone.");
        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(category, 1, 0);

        // Set MainLayout elements
        mainLayout.setTop(menuTopBar);
        mainLayout.setLeft(leftBar);
        mainLayout.setCenter(grid);

        // The scene taking the MainLayout
        Scene scene = new Scene(mainLayout, 1280, 800);

        primaryStage.setTitle("KYS FM - The Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Création du menu top du jeu
     * @return
     */
    private HBox createMenuTopBar(){

        HBox menuTopBar = new HBox();

        menuTopBar.setPadding(new Insets(15, 12, 15, 12));
        menuTopBar.setSpacing(10); // Ecart entre les nodes
        menuTopBar.setStyle("-fx-background-color: #f39c12;");

        FxIconicsLabel iconGamepad =
                (FxIconicsLabel) new FxIconicsLabel.Builder(FxFontAwesome.Icons.faw_gamepad)
                        .size(30)
                        .color(MaterialColor.WHITE)
                        .build();

        Text roomName = new Text("Game Room : eSport Special KYSFM Room");
        roomName.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        roomName.setFill(Color.WHITESMOKE);

        menuTopBar.getChildren().addAll(iconGamepad, roomName);

        return menuTopBar;
    }


    private VBox createLeftBar(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); // Set all sides to 10
        vbox.setSpacing(8);              // Gap between nodes
// Use style classes to set properties previously set above
        //vbox.getStyleClass().addAll("pane", "vbox");

        vbox.setStyle("-fx-background-color: #f39c12;");
        vbox.setPrefWidth(200);

        Text title = new Text("Players");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        FxIconicsLabel labelDefault =
                (FxIconicsLabel) new FxIconicsLabel.Builder(FxFontAwesome.Icons.faw_user)
                        .size(24)
                        .color(MaterialColor.WHITE)
                        .build();

        vbox.getChildren().add(labelDefault);

        Hyperlink options[] = new Hyperlink[] {
                new Hyperlink("Player 1"),
                new Hyperlink("Player 2"),
                new Hyperlink("Player 3"),
                new Hyperlink("Player 4")};

        for (int i=0; i<4; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
