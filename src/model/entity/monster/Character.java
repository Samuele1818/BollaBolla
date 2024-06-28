package model.entity.monster;

import model.Level;
import model.entity.Monster;
import model.entity.objects.Brick;
import model.entity.objects.bubble.Bubble;
import model.entity.objects.bubble.NormalBubble;
import model.entity.objects.consumable.Consumable;
import model.sam.Fire;
import model.sam.Jump;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Character extends Monster implements Jump, Fire, Serializable {
    public static final int SPAWN_X = Brick.WIDTH * 4, SPAWN_Y = Brick.HEIGHT * (Level.ROWS - 5), HEALTH = 3;
    private static final int DEFAULT_JUMP = 50;
    private Set<Consumable> powerUp;
    private int Health;
    private int jumpHeight;
    private Type type;


    public Character(Type type) {
        this(SPAWN_X, SPAWN_Y, HEALTH, type);
        this.Health = HEALTH;
        this.jumpHeight = DEFAULT_JUMP;

    }

    private Character(int x, int y, int health, Type type) {
        super(x, y, 2, type, "walk_right.gif");
        this.type = type;
        powerUp = new HashSet<>();
        changeCharacterType(type);
    }

    public void resetHumpHeight() {
        jumpHeight = DEFAULT_JUMP;
    }


    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }

    public Set<Consumable> getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(Set<Consumable> powerUp) {
        this.powerUp = powerUp;
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
        bubbleX = switch (getCurrentDirection()) {
            case LEFT -> {
                bubbleY = getY() + 5;
                yield getX() - 24;
            }
            case RIGHT -> {
                bubbleY = getY() + 5;
                yield getX() + HEIGHT;
            }
            default -> bubbleX;
        };

        return new NormalBubble(bubbleX, bubbleY, getCurrentDirection());
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
