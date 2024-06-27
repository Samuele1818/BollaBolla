package model.entity.objects;

import model.Entity;

import java.io.Serial;

public class Brick extends Entity {
    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    @Serial
    private final static long serialVersionUID = 5081307589196485458L;

    public Brick(int x, int y) {
        this(x, y, WIDTH, HEIGHT);
    }

    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height, Type.BRICK, "");
    }


    @Override
    public String toString() {
        return getX() + "," + getY();
    }
}
