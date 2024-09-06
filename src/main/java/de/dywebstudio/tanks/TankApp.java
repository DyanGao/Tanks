package de.dywebstudio.tanks;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;
import de.dywebstudio.tanks.collisions.*;
import de.dywebstudio.tanks.components.TankComponent;
import de.dywebstudio.tanks.ui.GameOverScene;
import de.dywebstudio.tanks.ui.WinnerScene;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


public class TankApp extends GameApplication {

    private Entity player;

    private TimerAction spawnedEnemiesTimerAction;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setMainMenuEnabled(true);
        settings.setWidth(28 * Config.GRID_SIZE + 6 * Config.GRID_SIZE);
        settings.setHeight(28 * Config.GRID_SIZE );
        settings.setTitle("Tank");
        settings.setVersion("1.0");
    }

    // store variables
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        // initialize variables for creating new enemy
        vars.put("spawnedEnemies",0);
        // initialize variables for killed enemies
        vars.put("destroyedEnemies",0);
        // initialize variables for game level
        vars.put("gameLevel", 1);
    }

    public void startGameLevel() {
        // reset TimerAction
        expireTimerAction(spawnedEnemiesTimerAction);
        // reset the variables
        FXGL.set("spawnedEnemies", 0);
        FXGL.set("destroyedEnemies", 0);

        // load the maps
        FXGL.setLevelFromMap("level" + FXGL.geti("gameLevel") + ".tmx");

        player = FXGL.spawn("player", 344, 424);

        // spawnedEnemies at beginning: 3
        FXGL.spawn("enemy", 30, 30);
        FXGL.spawn("enemy", 240, 30);
        FXGL.spawn("enemy", 500, 30);
        FXGL.inc("spawnedEnemies", 3);

        // random creature of the enemies
        spawnedEnemiesTimerAction = FXGL.run(() -> {
            // the amount of spawnendEnemies to reach the maximum amount of spawnendEnemies, let the timeraction to expire
            if (FXGL.geti("spawnedEnemies") == Config.MAX_ENEMY_AMOUNT) {
                expireTimerAction(spawnedEnemiesTimerAction);
                return;
            }
            Point2D pointPosition = FXGLMath.random(Config.SPAWN_ENEMY_POSITION).get();
            List<Entity> entityList = FXGL.getGameWorld().getEntitiesInRange(
                    new Rectangle2D(pointPosition.getX(), pointPosition.getY(), 39, 39));

            // do not create enemy on position of steel, sea, player and another enemy
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

    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameWorld().addEntityFactory(new TankEntityFactory());

        FXGL.getip("destroyedEnemies").addListener((ob, ov, nv) -> {
            if(nv.intValue() == Config.MAX_ENEMY_AMOUNT) {
                FXGL.runOnce(() -> {
                    FXGL.getSceneService().pushSubScene(new WinnerScene());
                }, Duration.seconds(1));

            }
        });

        startGameLevel();
    }

    @Override
    protected void initPhysics() {
        // collision detection between bullets and enemies
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());
        // collision detection between bullets and Player
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletPlayerHandler());
        // collision detection between bullets and bricks
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBrickHandler());
        // collision detection between enemies' bullets
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBulletHandler());
        // collision detection between bullets and borders
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletBorderHandler());
        // collision detection between bullets and steel
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletSteelHandler());
        // collision detection between bullets and flag
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletFlagHandler());

    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.W, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveUp();
        });
        FXGL.onKey(KeyCode.S, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveDown();
        });
        FXGL.onKey(KeyCode.A, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveLeft();
        });
        FXGL.onKey(KeyCode.D, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.moveRight();
        });
        FXGL.onKey(KeyCode.SPACE, () -> {
            TankComponent playerComponent = player.getComponent(TankComponent.class);
            playerComponent.shoot();
        });
    }

    public void gameOver() {
        FXGL.getSceneService().pushSubScene(new GameOverScene());
    }

    private void expireTimerAction(TimerAction spawnedEnemiesTimerAction) {
        if (spawnedEnemiesTimerAction != null && !spawnedEnemiesTimerAction.isExpired()) {
            spawnedEnemiesTimerAction.expire();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
