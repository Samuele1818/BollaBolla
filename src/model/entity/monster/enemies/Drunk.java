package model.entity.monster.enemies;

import model.Level;

public class Drunk extends Enemies {


    public Drunk(int x, int y) {

        super(x, y, Type.DRUNK, 4);
    }
    public Drunk(int x, int y, String path, Level.Direction direction) {

        super(x, y, Type.DRUNK, 4,path,direction);
    }

    @Override
    public void move(int x) {

    }
}
