package de.dywebstudio.tanks.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.Texture;
import de.dywebstudio.tanks.Config;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class InfoPane extends VBox {

    // constructor
    public InfoPane() {
        // set background color
        setStyle("-fx-background-color: Gray");
        // set spacing
        setSpacing(30);
        // set infoPane width and height
        setPrefSize(6 * Config.GRID_SIZE, FXGL.getAppHeight());
        // set infoPane to the right side of the window
        setTranslateX(28 * Config.GRID_SIZE);
        // center
        setAlignment(Pos.TOP_CENTER);

        // show the enemies' tank
        TilePane tilePane = new TilePane();
        // set margin
        VBox.setMargin(tilePane, new Insets(30, 0, 0, 0));
        // center
        tilePane.setTileAlignment(Pos.CENTER);
        // set horizontal gap
        tilePane.setHgap(15);
        // set vertical gap
        tilePane.setVgap(15);
        // set gap between tanks
        tilePane.setMaxWidth(25 * 2 + 15);
        // set the box height for later more tanks
        tilePane.setPrefHeight(25*15 + 6*15 +15);
        for (int i = 0; i < Config.MAX_ENEMY_AMOUNT; i++) {
            tilePane.getChildren().add(FXGL.texture("ui/enemy_info.png"));
        }
        getChildren().add(tilePane);

        // hide the tank if the tank in the game
        FXGL.getip("spawnedEnemies").addListener((obj, ov, nv) -> {
            ObservableList<Node> nodes = tilePane.getChildren();
            for (int i = nodes.size() - 1; i >= Config.MAX_ENEMY_AMOUNT - nv.intValue(); i--) {
                nodes.get(i).setVisible(false);
            }
        });

        // add the level image and level text
        Texture texture = FXGL.texture("ui/levelFlag.png");
        Text text = new Text("" + FXGL.geti("gameLevel"));
        text.setFont(Font.font(25));


        // to show the game level
        HBox levelBox = new HBox(texture, text);
        levelBox.setMaxWidth(70);
        levelBox.setAlignment(Pos.BOTTOM_CENTER);

        // add levelBox to the infoPane
        getChildren().add(levelBox);
    }
}
