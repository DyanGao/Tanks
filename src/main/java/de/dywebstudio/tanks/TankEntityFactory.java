package de.dywebstudio.tanks;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.ProgressBar;
import de.dywebstudio.tanks.components.EnemyComponent;
import de.dywebstudio.tanks.components.FlagComponent;
import de.dywebstudio.tanks.components.TankComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.geometry.Point2D;

/**
 * This class represents for creating entities per collective types in the game
 * such a class for creating entities is called an entity factory
 *
 * Add a factory to the game world as follows:
 * <code>getGameWorld().addEntityFactory(new TankEntityFactory());</code>
 * Spawn an entity, using <code>getGameWorld().spawn("player");</code>,
 * which in turn calls the factory methods annotated with <code>@Spawns("player!)</code>
 */
public class TankEntityFactory implements EntityFactory {

    /**
     * method, annotated with <code>@Spawns</code> can be used to spawn player.
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of player
     */
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        // set Health points for the player
        HealthIntComponent hpComponent = new HealthIntComponent(5);
        // current health points at the beginning
        hpComponent.setValue(5);
        // set progress bar for the player
        ProgressBar hpProgressBar = new ProgressBar(false);
        hpProgressBar.setLabelVisible(false);
        hpProgressBar.setWidth(39);
        hpProgressBar.setHeight(8);
        hpProgressBar.setTranslateY(42);
        hpProgressBar.setFill(Color.RED);

        // max hp bind on hpProgressBar
        hpProgressBar.maxValueProperty().bind(hpComponent.maxValueProperty());
        // current hp bind on hpProgressBar
        hpProgressBar.currentValueProperty().bind(hpComponent.valueProperty());
        return FXGL.entityBuilder(data)
                .type(GameType.PLAYER)
                .viewWithBBox("tanks/player.png")
                .view(hpProgressBar)
                .with(new TankComponent())
                .with(hpComponent)
                .with(new CollidableComponent(true))
                .build();
    }

    /**
     * method, annotated with <code>@Spawns</code> to create new brick.
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of brick
     */
    @Spawns("brick")
    public Entity newBrick(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.BRICK)
                .bbox(BoundingShape.box(Config.GRID_SIZE, Config.GRID_SIZE))
                .collidable()
                .neverUpdated()
                .build();
    }

    /**
     * Creates new grass
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of grass
     */
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

    /**
     * Creates new ice ground
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of ice
     */
    @Spawns("ice")
    public Entity newIce(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.ICE)
                .bbox(BoundingShape.box(Config.GRID_SIZE, Config.GRID_SIZE))
                .collidable()
                .neverUpdated()
                .build();
    }

    /**
     * Creates a new sea
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of sea
     */
    @Spawns("sea")
    public Entity newSea(SpawnData data) {
        // create a animated sea entity
        AnimationChannel animationChannel = new AnimationChannel(
                FXGL.image("items/sea_animation.png"), Duration.seconds(1), 2);
        AnimatedTexture texture = new AnimatedTexture(animationChannel);
        return FXGL.entityBuilder(data)
                .type(GameType.SEA)
                .viewWithBBox(texture.loop())
                .collidable()
                .build();
    }

    /**
     * Creates new steel
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of steel
     */
    @Spawns("steel")
    public Entity newSteel(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.STEEL)
                .bbox(BoundingShape.box(Config.GRID_SIZE, Config.GRID_SIZE))
                .collidable()
                .neverUpdated()
                .build();
    }

    /**
     * Creates flag
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of flag
     */
    @Spawns("flag")
    public Entity newFlag(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(GameType.FLAG)
                .bbox(BoundingShape.box(2 * Config.GRID_SIZE, 2 * Config.GRID_SIZE))
                .with(new FlagComponent())
                .collidable()
                .build();
    }

    /**
     * Construct the game board
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of border
     */
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

    /**
     * Creates bullets for the game
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of bullet
     */
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

    /**
     * Creates enemies
     * @param data
     * @return <code>FXGL.entityBuilder()</code> to quickly set up a bunch of properties of enemy
     */
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
