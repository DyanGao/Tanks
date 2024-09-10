package de.dywebstudio.tanks;


import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 *  Interface to define configuration constants for a game
 */
public interface Config {

    /**
     * defines the size of grid cells in the game
     */
    int GRID_SIZE = 24;

    /**
     * defines the speed of tanks
     */
    int TANK_SPEED = 120;

    /**
     * defines the speed of bullets
     */
    int BULLET_SPEED = 500;


    /**
     * sets a delay between shots
     */
    Duration SHOOT_DELAY = Duration.seconds(0.1);


    /**
     * An array of Point2D objects defining three spawn positions for enemies.
     *
     */
    Point2D[] SPAWN_ENEMY_POSITION = new Point2D[] {
            new Point2D(30, 30),
            new Point2D(240, 30),
            new Point2D(500, 30)
    };


    /**
     * Maximum of created enemies
     */
    int MAX_ENEMY_AMOUNT = 6;

    /**
     * Maximum of game level
     */
    int MAX_GAME_LEVEL = 2;
}
