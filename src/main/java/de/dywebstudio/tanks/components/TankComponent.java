package de.dywebstudio.tanks.components;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityGroup;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import de.dywebstudio.tanks.Config;
import de.dywebstudio.tanks.Direction;
import de.dywebstudio.tanks.GameType;

import java.util.List;

/**
 * This component is used to add data and behavior to Tank.
 */
public class TankComponent extends Component {

    /**
     * Boolean that indicates whether the tank is currently moving or not
     */
    private boolean isMoving = false;

    /**
     * Defines the moving distance
     */
    private double distance;

    /**
     * The direction of the tank moving, default is UP
     */
    private Direction moveDirection = Direction.UP;

    /**
     * A local timer to capture current shoot time
     */
    private LocalTimer shootTimer;

    /**
     * A lazy value is only initialized when get is invoked.
     * Subsequent calls to get returns the same value (instance).
     */
    private LazyValue<EntityGroup> entityGroupLazyValue
            = new LazyValue<>(() -> FXGL.getGameWorld().getGroup(
                    GameType.BRICK,
            GameType.BORDER,
            GameType.SEA,
            GameType.STEEL,
            GameType.PLAYER,
            GameType.ENEMY
    ));

    /**
     * getter for the movement
     * @return moveDirection
     */
    public Direction getMoveDirection() {
        return moveDirection;
    }

    /**
     * Called by add new component to entity
     */
    @Override
    public void onAdded() {
        shootTimer = FXGL.newLocalTimer();
    }

    /**
     * Called every frame _only_ in Play state
     * @param tpf - Time per frame
     */
    @Override
    public void onUpdate(double tpf) {
        isMoving = false;
        /**
         * Distance is calculated from the tpf and the tank speed
         */
        distance = tpf * Config.TANK_SPEED;
    }

    /**
     * Called when the player is moving to up
     */
    public void moveUp(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(0);
        moveDirection = Direction.UP;
        move();
    }

    /**
     * Called when the entity is moved to down direction
     */
    public void moveDown(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(180);
        moveDirection = Direction.DOWN;
        move();
    }

    /**
     * Moves the left
     */
    public void moveLeft(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(270);
        moveDirection = Direction.LEFT;
        move();
    }

    /**
     * Moves the right direction
     */
    public void moveRight(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(90);
        moveDirection = Direction.RIGHT;
        move();
    }

    /**
     * Called when the tank is moved and avoids collision
     */
    public void move() {
        int len = (int) distance;
        /**
         * List of the entities that avoid collisions with
         */
        List<Entity> blockerList = entityGroupLazyValue.get().getEntitiesCopy();
        blockerList.remove(entity);
        int size = blockerList.size();

        boolean isCollision = false;

        for (int i = 0; i < len; i++) {
            entity.translate(moveDirection.getVector().getX(), moveDirection.getVector().getY());

            for (int j = 0; j < size; j++) {
                if (entity.isColliding(blockerList.get(j))) {
                    isCollision = true;
                    break;
                }
            }

            if (isCollision) {
               entity.translate(-moveDirection.getVector().getX(), -moveDirection.getVector().getY());
               break;
           }
        }

    }

    /**
     * Called when shooting
     */
    public void shoot(){
        //System.out.println("Shooting");

        /**
         * Bullet shot one bullet after another bullet, delay between bullets
         */
        if (shootTimer.elapsed(Config.SHOOT_DELAY)) {
            FXGL.spawn("bullet", new SpawnData(entity.getCenter().subtract(8.0/2.0, 10/2.0))
                    .put("direction", moveDirection.getVector())
                    .put("allyType", entity.getType()));
        }
        shootTimer.capture();
    }
}
