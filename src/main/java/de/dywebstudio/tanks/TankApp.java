package de.dywebstudio.tanks;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.collisions.BulletEnemyHandler;
import de.dywebstudio.tanks.collisions.BulletPlayerHandler;
import de.dywebstudio.tanks.components.TankComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;




public class TankApp extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(28 * Config.GRID_SIZE + 6 * Config.GRID_SIZE);
        settings.setHeight(28 * Config.GRID_SIZE );
        settings.setTitle("Tank");
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameWorld().addEntityFactory(new TankEntityFactory());


//        FXGL.spawn("brick", 50, 20);
//        FXGL.spawn("grass", 20, 70);
//        FXGL.spawn("ice", 260, 90);
//        FXGL.spawn("sea", 180, 150);
//        FXGL.spawn("steel", 220, 250);
        FXGL.setLevelFromMap("level.tmx");

        player = FXGL.spawn("player", 344, 424);

        FXGL.spawn("enemy", 120, 120);
        FXGL.spawn("enemy", 240, 240);
        FXGL.spawn("enemy", 300, 300);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletEnemyHandler());
        FXGL.getPhysicsWorld().addCollisionHandler(new BulletPlayerHandler());
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

    public static void main(String[] args) {
        launch(args);
    }
}
