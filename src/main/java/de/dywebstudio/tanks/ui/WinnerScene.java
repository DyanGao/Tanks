package de.dywebstudio.tanks.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import de.dywebstudio.tanks.Config;
import de.dywebstudio.tanks.TankApp;
import javafx.animation.PauseTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class WinnerScene extends SubScene {

    private final PauseTransition pauseTransition;

    public WinnerScene() {

        Text text = new Text("You win!");
        text.setFill(Color.RED);
        text.setFont(Font.font(30));
        StackPane winnerPane = new StackPane(text);
        winnerPane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        winnerPane.setStyle("-fx-background-color: transparent");
        getContentRoot().getChildren().add(winnerPane);

        // pause after the winning of a level
        pauseTransition = new PauseTransition(Duration.seconds(2));

        // after pause transition to the next level
        pauseTransition.setOnFinished((event) -> {
            if(FXGL.geti("gameLevel") < Config.MAX_GAME_LEVEL) {
                FXGL.getSceneService().popSubScene();
                FXGL.inc("gameLevel", 1);
                FXGL.<TankApp>getAppCast().startGameLevel();
            } else {
                FXGL.getNotificationService().pushNotification("Congratulation! You finished the game!!!");
                FXGL.getGameController().gotoMainMenu();
            }
        });
    }

    @Override
    public void onCreate() {
        pauseTransition.play();
    }
}
