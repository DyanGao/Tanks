package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

/**
 * Handler for a collision that occurred between bullet and the border
 *
 */
public class BulletBorderHandler extends CollisionHandler {
    /**
     * Constructor
     */
    public BulletBorderHandler() {
        super(GameType.BULLET, GameType.BORDER);
    }

    /**
     * Called when a collision occurs between bullet and the border
     * @param bullet first entity
     * @param border second entity
     */
    @Override
    protected void onCollisionBegin(Entity bullet, Entity border) {
        /**
         * if collision occurs, bullet should be removed from the game world
         */
        bullet.removeFromWorld();
    }
}
