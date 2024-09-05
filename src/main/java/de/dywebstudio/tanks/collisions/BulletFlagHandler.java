package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;
import de.dywebstudio.tanks.TankApp;
import de.dywebstudio.tanks.components.FlagComponent;
import de.dywebstudio.tanks.ui.GameOverScene;

public class BulletFlagHandler extends CollisionHandler {
    public BulletFlagHandler() {
        super(GameType.BULLET, GameType.FLAG);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity flag) {
        flag.getComponent(FlagComponent.class).onShoot();
        bullet.removeFromWorld();
        FXGL.<TankApp>getAppCast().gameOver();
    }
}
