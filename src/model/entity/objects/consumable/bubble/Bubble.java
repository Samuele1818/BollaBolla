package model.entity.objects.consumable.bubble;

import model.behaviour.Fly;
import model.behaviour.Move;
import model.entity.objects.consumable.Consumable;

public abstract class Bubble extends Consumable implements Move, Fly {
    public static final int WIDTH = 24;
    public static final int HEIGHT = 24;
    private static final int SCORE = 10;

    /**
     * Bubble constructor
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param type bubble type
     */
    public Bubble(int x, int y, Type type, String animation) {
        super(x, y, WIDTH, HEIGHT, type, animation, SCORE);
    }

    /**
     * Bubble fly behaviour
     *
     * @param x number of pixels to shift by on the x-axis
     * @param y number of pixels to shift by on the y-axis
     */
    @Override
    public void fly(int x, int y) {
        setY(getY() + y);
    }

    /**
     * Bubble move behaviour
     *
     * @param currentSpeed current speed of movement
     */
    @Override
    public void move(int currentSpeed) {
        setX(getX() + currentSpeed);
    }

    /**
     * Do nothing
     *
     * @param left direction
     */
    @Override
    public void move(boolean left) {
    }
}
