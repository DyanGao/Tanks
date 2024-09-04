package de.dywebstudio.tanks.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

public class BulletEnemyHandler extends CollisionHandler {
    public BulletEnemyHandler() {
        super(GameType.BULLET, GameType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity enemy) {
        bullet.removeFromWorld();
        enemy.removeFromWorld();
    }
}
