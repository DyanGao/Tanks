package de.dywebstudio.tanks.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import de.dywebstudio.tanks.Direction;

/**
 * This class represents the behavior of the enemy tank
 */
public class EnemyComponent extends Component {

    /**
     * Component Injection, this component is injected automatically
     */
    private TankComponent tankComponent;

    /**
     * Called when the enemy tank is shown, moved and randomly shooting
     * @param tpf time per frame
     */
    @Override
    public void onUpdate(double tpf) {
        Direction moveDirection = tankComponent.getMoveDirection();
        if(FXGLMath.randomBoolean(0.02)) {
            moveDirection = FXGLMath.random(Direction.values()).get();
        }
        switch (moveDirection) {
            case UP -> tankComponent.moveUp();
            case DOWN -> tankComponent.moveDown();
            case LEFT -> tankComponent.moveLeft();
            case RIGHT -> tankComponent.moveRight();
            default -> {}
        }
        if (FXGLMath.randomBoolean(0.04)) {
            tankComponent.shoot();
        }
    }
}
