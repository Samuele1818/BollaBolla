package model.entity.monster.enemies;

import model.Level;

public class Invader extends Enemies {

    boolean fear;
    private int fearSpace = 16;

    public Invader(int x, int y) {
        super(x, y, Type.INVADER);
        fear = false;
    }

    public Invader(int x, int y, String path, Level.Direction direction) {
        super(x, y, Type.INVADER, 1, path, direction);
        fear = false;
    }

    public int getFearSpace() {
        return fearSpace;
    }


    public void setFearSpace(int fearSpace) {
        this.fearSpace = fearSpace;
    }

    public void decreseFearSpace() {
        this.fearSpace -= 1;
    }

    public void resetFearSpace() {
        this.fearSpace = 16;
    }

    public boolean isFear() {
        return fear;
    }

    public void setFear(boolean fear) {
        this.fear = fear;
    }
}
