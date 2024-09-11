package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

/**
 * Handler for a collision that occurred between bullets
 */
public class BulletBulletHandler extends CollisionHandler {
    /**
     * Constructor
     */
    public BulletBulletHandler() {
        super(GameType.BULLET, GameType.BULLET);
    }

    /**
     * Called when the collision occurs between bullets
     * @param b1 first entity
     * @param b2 second entity
     */
    @Override
    protected void onCollisionBegin(Entity b1, Entity b2) {
        /**
         * Collision occurs then both bullets should be removed
         */
        GameType t1 = b1.getObject("allyType");
        GameType t2 = b2.getObject("allyType");
        if (t1 != t2) {
            b1.removeFromWorld();
            b2.removeFromWorld();
        }
    }
}
