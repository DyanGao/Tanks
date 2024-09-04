package de.dywebstudio.tanks.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import de.dywebstudio.tanks.Direction;

public class EnemyComponent extends Component {

    private TankComponent tankComponent;

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
