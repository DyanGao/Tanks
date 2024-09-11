package de.dywebstudio.tanks;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;
import de.dywebstudio.tanks.collisions.*;
import de.dywebstudio.tanks.components.TankComponent;
import de.dywebstudio.tanks.ui.GameOverScene;
import de.dywebstudio.tanks.ui.InfoPane;
import de.dywebstudio.tanks.ui.WinnerScene;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

/**
 * In order to use FXGL for TankApp class needs to extend <code>GameApplication</code> and
 * override methods:
 * <ul>
 *   <li><code>initSettings()</code></li>
 *   <li><code>initGameVars()</code></li>
 *   <li><code>initGame()</code></li>
 *   <li><code>initPhysics()</code></li>
 *   <li><code>initInput()</code></li>
 * </ul>
 *
 * @author Dongyang Gao (DyanGao)
 */
public class TankApp extends GameApplication {

    /**
     * An instance attribute for the player
     */
    private Entity player;

    /**
     * <code>TimerAction</code> for refreshing after created the enemies
     */
    private TimerAction spawnedEnemiesTimerAction;


    /**
     * Basic settings for the game
     * @param settings
     */
    @Override
    protected void initSettings(GameSettings settings) {
        /**
         * Enable the main menu
         */
        settings.setMainMenuEnabled(true);

        /**
         * Set the width and height of the screen
         */
        settings.setWidth(28 * Config.GRID_SIZE + 6 * Config.GRID_SIZE);
        settings.setHeight(28 * Config.GRID_SIZE );

        /**
         * Set the title
         */
        settings.setTitle("Battle Tanks");

        /**
         * Set the version number
         */
        settings.setVersion("1.0");
    }


    /**
     * Stores variables
     * @param vars map containing CVars (global variables)
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        /**
         * initialize variables for creating new enemy
         * @param vars map containing
         */
        vars.put("spawnedEnemies",0);

        /**
         * initialize variables for killing enemies
         */
        vars.put("destroyedEnemies",0);

        /**
         * initialize variables for game level
         */
        vars.put("gameLevel", 1);
    }


    /**
     * Configurations at the beginning of the game, used to override the default method <code>initGame()</code>
     */
    public void startGameLevel() {
        //
        /**
         * reset TimerAction
         * @param action
         */
        expireTimerAction(spawnedEnemiesTimerAction);

        /**
         * reset the variables
         */
        FXGL.set("spawnedEnemies", 0);
        FXGL.set("destroyedEnemies", 0);

        /**
         * load the maps
         */
        FXGL.setLevelFromMap("level" + FXGL.geti("gameLevel") + ".tmx");

        /**
         * crate and load the player
         */
        player = FXGL.spawn("player", 344, 424);

        /**
         * add the Info Panel to Game
         */
        GameView gameView = new GameView(new InfoPane(), Integer.MAX_VALUE);
        FXGL.getGameScene().addGameView(gameView);


        /**
         * 3 spawnedEnemies at beginning
         */
        FXGL.spawn("enemy", 30, 30);
        FXGL.spawn("enemy", 240, 30);
        FXGL.spawn("enemy", 500, 30);
        FXGL.inc("spawnedEnemies", 3);

        /**
         * Create a random creature of the enemies
         */
        spawnedEnemiesTimerAction = FXGL.run(() -> {
            /**
             * The amount of spawnendEnemies to reach the maximum amount of spawnendEnenemies,
             * let the timeraction to expire
             */
            if (FXGL.geti("spawnedEnemies") == Config.MAX_ENEMY_AMOUNT) {
                expireTimerAction(spawnedEnemiesTimerAction);
                return;
            }
            Point2D pointPosition = FXGLMath.random(Config.SPAWN_ENEMY_POSITION).get();
            List<Entity> entityList = FXGL.getGameWorld().getEntitiesInRange(
                    new Rectangle2D(pointPosition.getX(), pointPosition.getY(), 39, 39));

            //
            /**
             * avoid to create enemy on position of steel, sea, player and another enemy
             */
            List<Entity> entities = entityList.stream().filter(entity ->
                    entity.isType(GameType.STEEL)
                            || entity.isType(GameType.SEA)
                            || entity.isType(GameType.ENEMY)
                            || entity.isType(GameType.PLAYER)).toList();
            if(entities.isEmpty()) {
                spawn("enemy",pointPosition);
                FXGL.inc("spawnedEnemies", 1);
            }
        }, Duration.seconds(2));


    }


    /**
     * Set up all the entities that needs to be ready before the game starts
     */
    @Override
    protected void initGame() {
        /**
         * Set the background color
         */
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        /**
         * Add the entities to the scene
         */
        FXGL.getGameWorld().addEntityFactory(new TankEntityFactory());

        /**
         * Observe the state of the enemies, before and after
         */
        FXGL.getip("destroyedEnemies").addListener((ob, ov, nv) -> {
            if(nv.intValue() == Config.MAX_ENEMY_AMOUNT) {
                FXGL.runOnce(() -> {
                    FXGL.getSceneService().pushSubScene(new WinnerScene());
                }, Duration.seconds(1));

            }
        });

        /**
         * <code>startGameLevel()</code> is called
         */
        startGameLevel();
    }

    /**
     * Set up the collision handlers
     */
    @Override
    protected void initPhysics() {
        /**
         * collision detection between bullets and enemies
         */
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());

        /**
         * collision detection between bullets and Player
         */
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletPlayerHandler());

        /**
         * collision detection between bullets and bricks
         */
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBrickHandler());

        /**
         * collision detection between enemies' bullets
         */
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBulletHandler());

        /**
         * collision detection between bullets and borders
         */
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBorderHandler());

        /**
         * collision detection between bullets and steel
         */
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletSteelHandler());

        /**
         * collision detection between bullets and flag
         */
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletFlagHandler());

    }

    /**
     * Set up input bindings for the user to be able to control the player entity
     */
    @Override
    protected void initInput() {
        /**
         * pressed w to move the player forward
         */
        FXGL.onKey(KeyCode.W, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveUp();
        });

        /**
         * pressed s to move the player down
         */
        FXGL.onKey(KeyCode.S, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveDown();
        });

        /**
         * pressed a to move the player left
         */
        FXGL.onKey(KeyCode.A, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveLeft();
        });

        /**
         * pressed d to move the player right
         */
        FXGL.onKey(KeyCode.D, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveRight();
        });

        /**
         * pressed space to shoot
         */
        FXGL.onKey(KeyCode.SPACE, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.shoot();
        });
    }


    /**
     *  Updates the game over screen
     */
    public void gameOver() {
        FXGL.getSceneService().pushSubScene(new GameOverScene());
    }


    /**
     * Refresh the timer action
     * @param spawnedEnemiesTimerAction <code>TimerAction</code> for spawning enemies
     */
    private void expireTimerAction(TimerAction spawnedEnemiesTimerAction) {
        if (spawnedEnemiesTimerAction != null && !spawnedEnemiesTimerAction.isExpired()) {
            spawnedEnemiesTimerAction.expire();
        }
    }

    /**
     * Starts the game
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
