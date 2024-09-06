package de.dywebstudio.tanks;


import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Point2D;
import javafx.util.Duration;


public interface Config {
    int GRID_SIZE = 24;
    int TANK_SPEED = 120;
    int BULLET_SPEED = 500;
    Duration SHOOT_DELAY = Duration.seconds(0.1);


    Point2D[] SPAWN_ENEMY_POSITION = new Point2D[] {
            new Point2D(30, 30),
            new Point2D(240, 30),
            new Point2D(500, 30)
    };

    // maximum of created enemies
    int MAX_ENEMY_AMOUNT = 6;
    // maximum of game level
    int MAX_GAME_LEVEL = 2;
}
