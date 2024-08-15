package model.entity.monster.enemies;

import model.Level;
import model.behaviour.Fly;
import model.entity.monster.Monster;

public abstract class Enemy extends Monster implements Fly {
    // Number of movements before touching the ground
    private int deadAnimationLength;

    /**
     * Enemy constructor
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param type entity type
     */
    public Enemy(int x, int y, Type type) {
        this(x, y, type, 1, "walk_right.gif", Level.Animation.RIGHT);
    }

    /**
     * Enemy constructor
     *
     * @param x     x coordinate
     * @param y     y coordinate
     * @param type  entity type
     * @param speed enemy speed
     */
    public Enemy(int x, int y, Type type, int speed) {
        this(x, y, type, speed, "walk_right.gif", Level.Animation.RIGHT);
    }

    /**
     * Enemy constructor
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param type      entity type
     * @param speed     enemy speed
     * @param skinPath  name of current animation file
     * @param animation current enemy animation
     */
    public Enemy(int x, int y, Type type, int speed, String skinPath, Level.Animation animation) {
        super(x, y, speed, type, skinPath, animation);
        deadAnimationLength = 100;
    }

    /**
     * Decrease number of movements before touching the ground after death
     */
    public void decreaseDeadAnimationLength() {
        deadAnimationLength--;
    }

    /**
     * Return current number of movements before touching the ground after death
     *
     * @return number of movements before touching the ground after death
     */
    public int getDeadAnimationLength() {
        return deadAnimationLength;
    }

    /**
     * Monster fly behaviour
     *
     * @param x number of pixels to shift by on the x-axis
     * @param y number of pixels to shift by on the y-axis
     */
    @Override
    public void fly(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }

}
