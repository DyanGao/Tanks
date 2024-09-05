package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

public class BulletSteelHandler extends CollisionHandler {
    public BulletSteelHandler() {
        super(GameType.BULLET, GameType.STEEL);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity steel) {

        bullet.removeFromWorld();
    }
}
