package model.entity.monster.enemies;

import model.Level;

public class Drunk extends Enemy {
    public static final int DEFAULT_SPEED = 4;

    /**
     * Drunk constructor
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Drunk(int x, int y) {
        this(x, y, Level.Animation.RIGHT.getAnimationFileName(), Level.Animation.RIGHT);
    }

    /**
     * Drunk constructor
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param skinPath  name of current animation file
     * @param animation current monster animation
     */
    public Drunk(int x, int y, String skinPath, Level.Animation animation) {
        super(x, y, Type.DRUNK, DEFAULT_SPEED, skinPath, animation);
    }
}
