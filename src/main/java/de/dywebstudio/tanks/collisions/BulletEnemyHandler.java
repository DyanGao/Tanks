package de.dywebstudio.tanks.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

/**
 * Handler for a collision that occurred between bullet and the enemy tank
 */
public class BulletEnemyHandler extends CollisionHandler {
    /**
     * Constructor
     */
    public BulletEnemyHandler() {
        super(GameType.BULLET, GameType.ENEMY);
    }

    /**
     * If the enemy tank is being destroyed, both the bullet and the enemy tank should be removed
     * @param bullet first entity
     * @param enemy second entity
     */
    @Override
    protected void onCollisionBegin(Entity bullet, Entity enemy) {
        bullet.removeFromWorld();
        enemy.removeFromWorld();
        FXGL.inc("destroyedEnemies", 1);
    }
}
