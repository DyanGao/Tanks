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

public class TankComponent extends Component {

    private boolean isMoving = false;
    private double distance;
    private Direction moveDirection = Direction.UP;
    private LocalTimer shootTimer;

    private LazyValue<EntityGroup> entityGroupLazyValue
            = new LazyValue<>(() -> FXGL.getGameWorld().getGroup(GameType.BRICK, GameType.BORDER, GameType.SEA, GameType.STEEL));

    /**
     * called by add new component to entity
     */
    @Override
    public void onAdded() {
        shootTimer = FXGL.newLocalTimer();
    }

    /**
     * Called every frame _only_ in Play state
     * @param tpf
     */
    @Override
    public void onUpdate(double tpf) {
        isMoving = false;
        distance = tpf * Config.TANK_SPEED;
    }

    public void moveUp(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(0);
        moveDirection = Direction.UP;
        move();
    }
    public void moveDown(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(180);
        moveDirection = Direction.DOWN;
        move();
    }
    public void moveLeft(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(270);
        moveDirection = Direction.LEFT;
        move();
    }
    public void moveRight(){
        if (isMoving) {
            return;
        }
        isMoving = true;
        entity.setRotation(90);
        moveDirection = Direction.RIGHT;
        move();
    }

    public void move() {
        int len = (int) distance;
        List<Entity> blockerList = entityGroupLazyValue.get().getEntitiesCopy();
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
    public void shoot(){
        //System.out.println("Shooting");
        // next bullet
        if (shootTimer.elapsed(Config.SHOOT_DELAY)) {
            FXGL.spawn("bullet", new SpawnData(entity.getCenter().subtract(8.0/2.0, 10/2.0))
                    .put("direction", moveDirection.getVector()));
        }
        shootTimer.capture();
    }
}
