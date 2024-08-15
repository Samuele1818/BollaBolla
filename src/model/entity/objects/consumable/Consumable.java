package model.entity.objects.consumable;

import model.entity.Entity;

public abstract class Consumable extends Entity {
    private int score;

    public Consumable(int x, int y, int width, int height, Type type, String skinPath, int score) {
        super(x, y, width, height, type, skinPath);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
