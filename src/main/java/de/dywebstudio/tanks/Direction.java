package de.dywebstudio.tanks;


import javafx.geometry.Point2D;

/**
 * This enum defines collections of constants for direction
 */
public enum Direction {
    UP(new Point2D(0, -1)),
    DOWN(new Point2D(0, 1)),
    LEFT(new Point2D(-1, 0)),
    RIGHT(new Point2D(1, 0));


    /**
     * an instance attribute that represents the vector
     */
    private Point2D vector;

    /**
     * Constructor
     * @param vector
     */
    Direction(Point2D vector) {
        this.vector = vector;
    }


    /**
     * method to get the direction vector
     * @return vector
     */
    public Point2D getVector() {
        return vector;
    }
}
