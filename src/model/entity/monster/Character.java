package model.entity.monster;

import model.Level;
import model.entity.Monster;
import model.entity.objects.Brick;
import model.entity.objects.bubble.Bubble;
import model.entity.objects.bubble.NormalBubble;
import model.sam.Fire;
import model.sam.Jump;

import java.io.Serializable;

public class Character extends Monster implements Jump,Fire,Serializable {
    public static final int SPAWN_X = Brick.WIDTH * 4, SPAWN_Y = Brick.HEIGHT * (Level.ROWS - 5), HEALTH = 3;

    private static int default_Jump = 50;
    private int Health;
    private int jumpHeight;
    private Type type;


    private boolean redShoe;
    private boolean bluCandy;
    private boolean pinkCandy;
    private boolean yellowCandy;
    public Character(Type type) {
        this(SPAWN_X, SPAWN_Y, HEALTH, type);
        this.Health = HEALTH;
        this.jumpHeight = default_Jump;
        redShoe=false;
        bluCandy =false;
        pinkCandy=false;
        yellowCandy=false;
    }

    public boolean isYellowCandy() {
        return yellowCandy;
    }

    public void setYellowCandy(boolean yellowCandy) {
        this.yellowCandy = yellowCandy;
    }

    public boolean isPinkCandy() {
        return pinkCandy;
    }

    public void setPinkCandy(boolean pinkCandy) {
        this.pinkCandy = pinkCandy;
    }

    public boolean isBluCandy() {
        return bluCandy;
    }

    public void setBluCandy(boolean bluCandy) {
        this.bluCandy = bluCandy;
    }

    public boolean isRedShoe() {
        return redShoe;
    }

    public void setRedShoe(boolean redShoe) {
        this.redShoe = redShoe;
    }

    private Character(int x, int y, int health, Type type) {
        super(x, y, 2, type, "walk_right.gif");
        this.type = type;
        changeCharacterType(type);
    }



    public void resetHumpHeight() {
        jumpHeight = default_Jump;
    }


    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }


    public int getJumpHeight() {
        return jumpHeight;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    @Override
    public void jump() {
        setY(getY() - 2);
    }

    @Override
    public Bubble fire() {
        int bubbleX = 0;
        int bubbleY = 0;
        switch (getCurrentDirection()) {
            case LEFT:
                bubbleY = getY() + 5;
                bubbleX = getX() - 24;
                break;
            case RIGHT:
                bubbleY = getY() + 5;
                bubbleX = getX() + HEIGHT;
                break;
        }

        return new NormalBubble(bubbleX, bubbleY, getCurrentDirection());
    }

    @Override
    public void fall() {
        this.setY(getY() + 2);
    }



    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
