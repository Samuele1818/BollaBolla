package model.entity.monster.enemies;

import model.Level;
import model.sam.Fly;

public class Mighta extends Enemies implements Fly {


    public Mighta(int x, int y) {
        super(x, y, Type.MIGHTA);
    }

    public Mighta(int x, int y, String path, Level.Direction direction) {

        super(x, y, Type.MIGHTA, 1, path, direction);
    }

    @Override
    public void fly(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }


}
