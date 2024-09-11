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


/**
 * This class describes the screen of the winner
 */
public class WinnerScene extends SubScene {

    /**
     * The duration of this Transition, if the value of duration is changed for a running PauseTransition,
     * the animation has to be stopped and started again to pick up the new value.
     */
    private final PauseTransition pauseTransition;

    /**
     * Constructor
     */
    public WinnerScene() {

        /**
         * UI settings for this scene
         */
        Text text = new Text("You win!");
        text.setFill(Color.RED);
        text.setFont(Font.font(30));
        StackPane winnerPane = new StackPane(text);
        winnerPane.setPrefSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        winnerPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        getContentRoot().getChildren().add(winnerPane);

        /**
         * Pause after the winning of a level, duration of 2 milliseconds.
         */
        pauseTransition = new PauseTransition(Duration.seconds(2));

        /**
         * After pause transition to the next level
         */
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

    /**
     * Called when the game is finished
     */
    @Override
    public void onCreate() {
        pauseTransition.play();
    }
}
