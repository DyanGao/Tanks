package de.dywebstudio.tanks;

import javafx.util.Duration;

public interface Config {
    int GRID_SIZE = 24;
    int TANK_SPEED = 120;
    int BULLET_SPEED = 500;
    Duration SHOOT_DELAY = Duration.seconds(0.1);
}
