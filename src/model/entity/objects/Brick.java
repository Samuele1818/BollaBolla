package model.entity.objects;

import model.entity.Entity;

import java.io.Serial;

public class Brick extends Entity {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    @Serial
    private final static long serialVersionUID = 5081307589196485458L;

    /**
     * Brick constructor
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Brick(int x, int y) {
        super(x, y, WIDTH, HEIGHT, Type.BRICK, "");
    }
}


