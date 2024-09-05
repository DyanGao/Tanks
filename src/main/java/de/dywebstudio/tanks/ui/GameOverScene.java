package de.dywebstudio.tanks.ui;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;


import javafx.util.Duration;



public class GameOverScene extends SubScene {

    private final TranslateTransition transition;
    private final Texture texture;

    public GameOverScene() {

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


    public void onCreate() {

        transition.play();
    }


}
