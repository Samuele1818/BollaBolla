package model.entity.objects.consumable;

import model.Entity;
import model.sam.Fall;

public abstract class Consumable extends Entity implements Fall {

    public static final int WIDTH=32;
    public static final int HEIGHT=32;

    public Consumable(int x, int y, int width, int height, Type type, String skinPath) {
        super(x, y, width, height, type, skinPath);
    }
    @Override
    public void fall(){
        setY(getY()+1);
    }

}
