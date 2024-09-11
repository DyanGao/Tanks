package de.dywebstudio.tanks.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

/**
 * Handler for a collision that occurred between bullet and the brick
 */
public class BulletBrickHandler extends CollisionHandler {

    /**
     * Constructor
     */
    public BulletBrickHandler() {
        super(GameType.BULLET, GameType.BRICK);
    }

    /**
     * Called when a collision occurs between the bullet and the brick
     * @param bullet first entity
     * @param brick second entity
     */
    @Override
    protected void onCollisionBegin(Entity bullet, Entity brick) {
        /**
         * If the collision occurs between the bullet and the brick
         * then the bullet and the brick should be removed
         */
        bullet.removeFromWorld();
        brick.removeFromWorld();
    }
}
