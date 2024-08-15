package model.entity.monster.enemies;

import model.Level;

public class Mighta extends Enemy {
    public static final int DEFAULT_SPEED = 1;

    /**
     * Invader constructor
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Mighta(int x, int y) {
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
    public Mighta(int x, int y, String skinPath, Level.Animation animation) {
        super(x, y, Type.MIGHTA, DEFAULT_SPEED, skinPath, animation);
    }
}
