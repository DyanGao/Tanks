package de.dywebstudio.tanks;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import de.dywebstudio.tanks.components.EnemyComponent;
import de.dywebstudio.tanks.components.TankComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.geometry.Point2D;


public class TankEntityFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.PLAYER)
                .viewWithBBox("tanks/player.png")
                .with(new TankComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.BRICK)
                .bbox(BoundingShape.box(Config.GRID_SIZE, Config.GRID_SIZE))
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("grass")
    public Entity newGrass(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.GRASS)
                .bbox(BoundingShape.box(Config.GRID_SIZE, Config.GRID_SIZE))
                .collidable()
                .zIndex(99)
                .neverUpdated()
                .build();
    }

    @Spawns("ice")
    public Entity newIce(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.ICE)
                .bbox(BoundingShape.box(Config.GRID_SIZE, Config.GRID_SIZE))
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("sea")
    public Entity newSea(SpawnData data) {
        AnimationChannel animationChannel = new AnimationChannel(
                FXGL.image("items/sea_animation.png"), Duration.seconds(1), 2);
        AnimatedTexture texture = new AnimatedTexture(animationChannel);
        return FXGL.entityBuilder(data)
                .type(GameType.SEA)
                .viewWithBBox(texture.loop())
                .collidable()
                .build();
    }

    @Spawns("steel")
    public Entity newSteel(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.STEEL)
                .bbox(BoundingShape.box(Config.GRID_SIZE, Config.GRID_SIZE))
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("flag")
    public Entity newFlag(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.FLAG)
                .bbox(BoundingShape.box(2 * Config.GRID_SIZE, 2 * Config.GRID_SIZE))
                .collidable()
                .neverUpdated()
                .build();
    }

    @Spawns("border")
    public Entity newBorder(SpawnData data) {
        int width = data.<Integer>get("width");
        int height = data.<Integer>get("height");
        return FXGL.entityBuilder(data)
                .type(GameType.BORDER)
                .viewWithBBox(new Rectangle(width, height, Color.LIGHTGRAY))
                .collidable()
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Point2D bulletDirection = data.get("direction");

        CollidableComponent collidableComponent = new CollidableComponent(true);
        collidableComponent.addIgnoredType(data.<GameType>get("allyType"));

        return FXGL.entityBuilder(data)
                .type(GameType.BULLET)
                .viewWithBBox("items/bullet/norm.png")
                .with(new ProjectileComponent(bulletDirection, Config.BULLET_SPEED))
                .with(collidableComponent)
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.ENEMY)
                .viewWithBBox("tanks/T" + FXGL.random(1,6) + ".png")
                .with(new TankComponent())
                .with(new EnemyComponent())
                .with(new CollidableComponent(true))
                .build();
    }
}
