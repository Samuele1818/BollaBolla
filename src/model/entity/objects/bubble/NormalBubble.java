package model.entity.objects.bubble;

import model.Level;
import model.sam.Fly;


public class NormalBubble extends Bubble implements Fly {

    public NormalBubble(int x, int y, Level.Direction direction) {
        super(x, y, BUBBLE_SIZE, BUBBLE_SIZE, 48);
        setCurrentDirection(direction);

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
