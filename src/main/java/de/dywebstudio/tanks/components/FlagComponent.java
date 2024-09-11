package de.dywebstudio.tanks.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

/**
 * This class describes the behavior of the flag
 */
public class FlagComponent extends Component {
    /**
     * Called when the flag is got shooting
     */
    public void onShoot() {
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(FXGL.texture("items/flag_failed.png"));
    }
}
