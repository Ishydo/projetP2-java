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
 * Created by Dom on 10.06.2016.
 */
public class PlayerLine extends HBox {

    Text playername;
    Text score;
    boolean ready;

    public PlayerLine(String _username, String _score, boolean ready){

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
