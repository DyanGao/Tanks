package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;
import de.dywebstudio.tanks.TankApp;
import de.dywebstudio.tanks.components.FlagComponent;

/**
 * Handler for a collision that occurred between bullet and the flag
 */
public class BulletFlagHandler extends CollisionHandler {
    /**
     * Constructor
     */
    public BulletFlagHandler() {
        super(GameType.BULLET, GameType.FLAG);
    }

    /**
     * Called when the flag is got shooting
     * @param bullet first entity
     * @param flag second entity
     */
    @Override
    protected void onCollisionBegin(Entity bullet, Entity flag) {
        /**
         * If the flag is firing then the bullet should be removed and the game should be lost
         */
        flag.getComponent(FlagComponent.class).onShoot();
        bullet.removeFromWorld();
        FXGL.<TankApp>getAppCast().gameOver();
    }
}
