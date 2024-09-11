package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

/**
 * Handler for a collision that occurred between bullet and the steel
 */
public class BulletSteelHandler extends CollisionHandler {
    /**
     * Constructor
     */
    public BulletSteelHandler() {
        super(GameType.BULLET, GameType.STEEL);
    }

    /**
     * Called when a collision occurs between the bullet and the steel
     * @param bullet first entity
     * @param steel second entity
     */
    @Override
    protected void onCollisionBegin(Entity bullet, Entity steel) {
        /**
         * If the bullet is colliding with the steel then the bullet should be removed
         * the steel is resistant
         */
        bullet.removeFromWorld();
    }
}
