package model.entity.objects.consumable;

import model.Entity;
import model.sam.Fall;

public abstract class Consumable extends Entity implements Fall {
    private int speed;

    private int score;
    public Consumable(int x, int y, int width, int height, Type type, String skinPath,int score,int speed) {
        super(x, y, width, height, type, skinPath);
        this.score=score;
        this.speed=speed;
    }
    @Override
    public void fall(){
        setY(getY()+1);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
