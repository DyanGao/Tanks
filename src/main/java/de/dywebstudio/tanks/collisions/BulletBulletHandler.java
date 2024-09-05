package de.dywebstudio.tanks.collisions;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

public class BulletBulletHandler extends CollisionHandler {
    public BulletBulletHandler() {
        super(GameType.BULLET, GameType.BULLET);
    }

    @Override
    protected void onCollisionBegin(Entity b1, Entity b2) {
        GameType t1 = b1.getObject("allyType");
        GameType t2 = b2.getObject("allyType");
        if (t1 != t2) {
            b1.removeFromWorld();
            b2.removeFromWorld();
        }
    }
}
