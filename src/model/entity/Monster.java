package model.entity;

import model.Entity;
import model.Level;
import model.sam.Fall;
import model.sam.Fly;
import model.sam.Move;

import java.io.Serializable;

public abstract class Monster extends Entity implements Move, Fall, Serializable, Fly {
    private static final long serialVersionUID = 3977631827356837393L;
    public static final int WIDTH = 32, HEIGHT = 32;


    private int speed;


    public Monster(int x, int y, int speed, Type type, String skinPath) {
        super(x, y, WIDTH, HEIGHT, type, skinPath);

        this.speed = speed;

    }
    public Monster(int x, int y, int speed, Type type, String skinPath, Level.Direction direction) {
        super(x, y, WIDTH, HEIGHT, type, skinPath,direction);

        this.speed = speed;

    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    public void fall() {
        this.setY(getY() + 1);
    }


    @Override
    public void move(int speed) {
        setX(getX() + speed);
    }

    @Override
    public void fly(int x,int y){
        setX(getX()+x);
        setY(getY()+y);
    }



}
