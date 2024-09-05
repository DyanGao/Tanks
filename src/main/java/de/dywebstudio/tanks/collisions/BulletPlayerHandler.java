package de.dywebstudio.tanks.collisions;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import de.dywebstudio.tanks.GameType;

import java.util.Collection;

public class BulletPlayerHandler extends CollisionHandler {
    public BulletPlayerHandler() {
        super(GameType.BULLET, GameType.PLAYER);
    }

    @Override
    protected void onCollisionBegin(Entity bullet, Entity player) {
        HealthIntComponent hpValues = player.getComponent(HealthIntComponent.class);
        hpValues.damage(1);
        if (hpValues.isZero()) {
            player.removeFromWorld();
        }
        bullet.removeFromWorld();
    }
}
