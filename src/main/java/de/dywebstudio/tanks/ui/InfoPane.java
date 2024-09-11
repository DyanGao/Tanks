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

/**
 * This class represents the layout of info panel, which of the right hand side
 */
public class InfoPane extends VBox {

    /**
     * Constructor
     */
    public InfoPane() {
        /**
         * set background color
         */
        setStyle("-fx-background-color: Gray");

        /**
         * Set spacing
         */
        setSpacing(30);

        /**
         * Set infoPane width and height
         */
        setPrefSize(6 * Config.GRID_SIZE, FXGL.getAppHeight());

        /**
         * Set infoPane to the right side of the screen
         */
        setTranslateX(28 * Config.GRID_SIZE);

        /**
         * Center
         */
        setAlignment(Pos.TOP_CENTER);

        /**
         * Showcase for the enemies' tank
         */
        TilePane tilePane = new TilePane();

        /**
         * Set margin
         */
        VBox.setMargin(tilePane, new Insets(30, 0, 0, 0));
        //
        /**
         * Center
         */
        tilePane.setTileAlignment(Pos.CENTER);

        /**
         * Set horizontal gap
         */
        tilePane.setHgap(15);

        /**
         * Set vertical gap
         */
        tilePane.setVgap(15);

        /**
         * Set gap between tanks
         */
        tilePane.setMaxWidth(25 * 2 + 15);

        /**
         * Set the box height for later more tanks
         */
        tilePane.setPrefHeight(25*15 + 6*15 +15);
        for (int i = 0; i < Config.MAX_ENEMY_AMOUNT; i++) {
            tilePane.getChildren().add(FXGL.texture("ui/enemy_info.png"));
        }
        getChildren().add(tilePane);

        /**
         * hide the tank in showcase if the tank attend battle in the game
         */
        FXGL.getip("spawnedEnemies").addListener((obj, ov, nv) -> {
            ObservableList<Node> nodes = tilePane.getChildren();
            for (int i = nodes.size() - 1; i >= Config.MAX_ENEMY_AMOUNT - nv.intValue(); i--) {
                nodes.get(i).setVisible(false);
            }
        });

        /**
         * Add the level image and level text
         */
        Texture texture = FXGL.texture("ui/levelFlag.png");
        Text text = new Text("" + FXGL.geti("gameLevel"));
        text.setFont(Font.font(25));

        /**
         * To show the current game level
         */
        HBox levelBox = new HBox(texture, text);
        levelBox.setMaxWidth(70);
        levelBox.setAlignment(Pos.BOTTOM_CENTER);

        /**
         * Add levelBox to the infoPane
         */
        getChildren().add(levelBox);
    }
}
