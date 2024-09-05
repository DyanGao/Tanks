package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

public class BulletBorderHandler extends CollisionHandler {
    public BulletBorderHandler() {
        super(GameType.BULLET, GameType.BORDER);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity border) {
            bullet.removeFromWorld();
    }
}
