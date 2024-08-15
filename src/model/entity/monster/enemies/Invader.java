package model.entity.monster.enemies;

import model.Level;

public class Invader extends Enemy {
    public static final int DEFAULT_SPEED = 1, DEFAULT_FEAR_SPACE = 16;
    private boolean hasFear;
    private int fearSpace = DEFAULT_FEAR_SPACE;

    /**
     * Invader constructor
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Invader(int x, int y) {
        this(x, y, Level.Animation.RIGHT.getAnimationFileName(), Level.Animation.RIGHT);
    }

    /**
     * Invader constructor
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param skinPath  name of current animation file
     * @param animation current monster animation
     */
    public Invader(int x, int y, String skinPath, Level.Animation animation) {
        super(x, y, Type.INVADER, DEFAULT_SPEED, skinPath, animation);
        hasFear = false;
    }

    /**
     * Get fear space
     *
     * @return fear space
     */
    public int getFearSpace() {
        return fearSpace;
    }

    /**
     * Decrease fear space
     */
    public void decreaseFearSpace() {
        this.fearSpace -= 1;
    }

    /**
     * Reset fear space to default
     */
    public void resetFearSpace() {
        this.fearSpace = DEFAULT_FEAR_SPACE;
    }

    /**
     * Check if invader has fear
     *
     * @return has fear
     */
    public boolean hasFear() {
        return hasFear;
    }

    /**
     * Set if invader has fear
     *
     * @param hasFear value of has fear
     */
    public void setHasFear(boolean hasFear) {
        this.hasFear = hasFear;
    }
}
