package model.entity.monster.enemies;


import model.Level;

public class ZenChan extends Enemie {
    public ZenChan(int x, int y) {
        super(x, y, Type.ZENCHAN);

    }
    public ZenChan(int x, int y,String path, Level.Direction direction) {
        super(x, y, Type.ZENCHAN,1,path,direction);

    }


}
