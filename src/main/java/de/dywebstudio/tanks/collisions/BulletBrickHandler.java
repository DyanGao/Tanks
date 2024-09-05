package de.dywebstudio.tanks.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

public class BulletBrickHandler extends CollisionHandler {

    public BulletBrickHandler() {
        super(GameType.BULLET, GameType.BRICK);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity brick) {
        bullet.removeFromWorld();
        brick.removeFromWorld();
    }
}
