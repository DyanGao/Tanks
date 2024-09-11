package de.dywebstudio.tanks.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;
import de.dywebstudio.tanks.TankApp;


/**
 * Handler for a collision that occurred between bullet and the player
 */
public class BulletPlayerHandler extends CollisionHandler {
    /**
     * Constructor
     */
    public BulletPlayerHandler() {
        super(GameType.BULLET, GameType.PLAYER);
    }

    /**
     * Called when a collision occurs between the bullet and the player
     * @param bullet first entity
     * @param player second entity
     */
    @Override
    protected void onCollisionBegin(Entity bullet, Entity player) {
        /**
         * If the player is got shot by the bullet,
         * the Health Points should be updated and the bullet should be removed
         * If the Health Points is zero, the player should be removed and game over
         */
        HealthIntComponent hpValues = player.getComponent(HealthIntComponent.class);
        hpValues.damage(1);
        if (hpValues.isZero()) {
            player.removeFromWorld();
            FXGL.<TankApp>getAppCast().gameOver();
        }
        bullet.removeFromWorld();
    }
}
