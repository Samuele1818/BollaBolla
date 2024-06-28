package model.entity.objects.bubble;

import model.Level;
import model.sam.Fly;


public class NormalBubble extends Bubble implements Fly {


    public static int fireDelaySetting = 32;
    public NormalBubble(int x, int y, Level.Direction direction) {
        super(x, y, BUBBLE_SIZE, BUBBLE_SIZE, getFireDelaySetting());
        setCurrentDirection(direction);

    }

    public static int getFireDelaySetting() {
        return fireDelaySetting;
    }

    public static void setFireDelaySetting(int fireDelaySetting) {
        NormalBubble.fireDelaySetting = fireDelaySetting;
    }

    @Override
    public void fly(int x, int y) {
        setY(getY() + y);
    }


    @Override
    public void move(int x) {
        setX(getX() + x);
    }
}
