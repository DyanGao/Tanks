package de.dywebstudio.tanks.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;

import javafx.animation.TranslateTransition;

import javafx.util.Duration;


/**
 * This class describes the scene if the player lost the game
 */
public class GameOverScene extends SubScene {

    /**
     * This Transition creates a move/ translate animation that spans its duration.
     */
    private final TranslateTransition transition;
    /**
     * Represents a 2D image view
     */
    private final Texture texture;

    /**
     * Constructor
     */
    public GameOverScene() {

        /**
         * UI settings for the transition animation of this scene
         */
        texture = FXGL.texture("ui/GameOver.png");
        texture.setLayoutX(28*24/2.0- texture.getWidth()/2.0);
        texture.setLayoutY(FXGL.getAppHeight());
        transition = new TranslateTransition(Duration.seconds(1.0), texture);
        transition.setInterpolator(Interpolators.SINE.EASE_OUT());
        transition.setFromY(0);
        transition.setToY(-(FXGL.getAppHeight() - 280));
        transition.setOnFinished(event -> {
            FXGL.getSceneService().popSubScene();
            texture.setTranslateY(0);
            FXGL.getGameController().gotoMainMenu();
        });
        getContentRoot().getChildren().add(texture);;
    }


    /**
     * This method is called when the player lost
     */
    public void onCreate() {
        transition.play();
    }


}
