package map;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.MaterialColor;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Classe PlayerLine héritant de HBox.
 * Représente une ligne dans la partie gauche
 * de la fenêtre.
 */
public class PlayerLine extends HBox {

    protected Text playername;
    protected Text score;
    protected boolean ready;
    protected String uuid;

    /**
     * Constructeur de PlayerLine.
     * Va créer toute la ligne.
     * @param _username : Nom du joueur.
     * @param _uuid : UUID du joueur
     * @param _score : Score du joueur.
     * @param ready : Boolean si le joueur est prêt.
     */
    public PlayerLine(String _username,String _uuid, String _score, boolean ready){

        uuid = _uuid;
        playername = new Text(_username);
        score = new Text(_score);

        // Style playername
        playername.setFill(Color.WHITESMOKE);
        playername.setFont(Font.font("Open Sans Extrabold", FontWeight.NORMAL, 18));

        // Style score
        score.setFill(Color.WHITESMOKE);
        score.setFont(Font.font("Open Sans Light", FontWeight.NORMAL, 18));
        score.setTextAlignment(TextAlignment.RIGHT);

        // Icon
        FxIconicsLabel icon;

        // Little icon
        if(ready) {
            icon =
                    (FxIconicsLabel) new FxIconicsLabel.Builder(FxFontAwesome.Icons.faw_user)
                            .size(20)
                            .color(MaterialColor.WHITE)
                            .build();
        }else{
            icon =
                    (FxIconicsLabel) new FxIconicsLabel.Builder(FxFontAwesome.Icons.faw_times)
                            .size(20)
                            .color(MaterialColor.WHITE)
                            .build();
        }

        // Username solo box
        HBox usernameBox = new HBox(8);
        usernameBox.setMinHeight(20);
        usernameBox.setPrefWidth(200);

        // Score solo box
        HBox scoreBox = new HBox();
        scoreBox.setMinHeight(20);
        scoreBox.setAlignment(Pos.TOP_RIGHT);

        usernameBox.getChildren().addAll(icon, playername);
        scoreBox.getChildren().add(score);

        // Final
        getChildren().addAll(usernameBox, scoreBox);
    }
}
