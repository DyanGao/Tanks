package de.dywebstudio.tanks.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

public class FlagComponent extends Component {
    public void onShoot() {
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(FXGL.texture("items/flag_failed.png"));
    }
}
