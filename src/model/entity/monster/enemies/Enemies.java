package model.entity.monster.enemies;

import model.Level;
import model.entity.Monster;

public abstract class Enemies extends Monster {
    private int deadSize;

    public Enemies(int x, int y, Type type) {
        super(x, y, 1, type, "walk_right.gif");
        deadSize = 100;
    }


    public Enemies(int x, int y, Type type, int speed) {
        this(x, y, type, speed, "walk_right.gif", Level.Direction.RIGHT);
    }

    public Enemies(int x, int y, Type type, int speed, String path, Level.Direction direction) {
        super(x, y, speed, type, path, direction);
        deadSize = 100;
    }

    public void decreaseDeadSize() {
        deadSize--;
    }

    public int getDeadSize() {
        return deadSize;
    }

    public void resetDeadSize() {
        deadSize = 100;
    }
}
