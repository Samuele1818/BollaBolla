package model.behaviour;

/**
 * Define move behaviour
 */
public interface Move {
    /**
     * Use speed of the entity and set the direction
     *
     * @param left is left direction or right
     */
    void move(boolean left);

    /**
     * Move entity of custom speed parameter
     *
     * @param currentSpeed custom speed
     */
    void move(int currentSpeed);
}