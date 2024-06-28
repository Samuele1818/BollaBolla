package model.entity.monster.enemies;


import model.Level;

public class Monsta extends Enemies {
    public Monsta(int x, int y) {
        super(x, y, Type.MONSTA);

    }

    public Monsta(int x, int y, String path, Level.Direction direction) {
        super(x, y, Type.MONSTA, 1, path, direction);

    }
}


