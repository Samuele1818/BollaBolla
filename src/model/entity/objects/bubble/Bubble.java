package model.entity.objects.bubble;

import model.Level;
import model.entity.objects.consumable.Consumable;
import model.sam.Move;

public abstract class Bubble extends Consumable implements Move {
    public final static int BUBBLE_SIZE = 24;
    private int fireDelay;
    private boolean isEnemies;

    public Bubble(int x, int y, int width, int height, int fireDelay) {
        super(x, y, width, height, Type.NORMAL_BUBBLE, Level.Direction.BUBBLE.getImagesMovements());
        this.fireDelay = fireDelay;
        isEnemies = false;
    }

    public void decreaseFireDelay() {
        fireDelay--;
    }

    public int getFireDelay() {
        return fireDelay;
    }

    public void resetFireDelay() {
        fireDelay = 48;
    }

    public void clearFireDelay() {
        this.fireDelay = -1;
    }

    public boolean isEnemies() {
        return isEnemies;
    }

    public void setEnemies(boolean enemies) {
        isEnemies = enemies;
    }
}
