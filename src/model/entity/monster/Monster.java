package model.entity.monster;

import model.Level;
import model.behaviour.Fall;
import model.behaviour.Move;
import model.entity.Entity;

import java.io.Serial;
import java.io.Serializable;

public abstract class Monster extends Entity implements Move, Fall, Serializable {
    public static final int WIDTH = 32, HEIGHT = 32;
    @Serial
    private static final long serialVersionUID = 3977631827356837393L;
    private int speed;

    /**
     * Monster constructor
     *
     * @param x        monster x coordinate
     * @param y        monster y coordinate
     * @param speed    monster speed
     * @param type     monster type
     * @param skinPath name of current animation file
     */
    public Monster(int x, int y, int speed, Type type, String skinPath) {
        super(x, y, WIDTH, HEIGHT, type, skinPath);

        this.speed = speed;
    }

    /**
     * Monster constructor
     *
     * @param x         monster x coordinate
     * @param y         monster y coordinate
     * @param speed     monster speed
     * @param type      monster type
     * @param skinPath  name of current animation file
     * @param animation current monster animation
     */
    public Monster(int x, int y, int speed, Type type, String skinPath, Level.Animation animation) {
        super(x, y, WIDTH, HEIGHT, type, skinPath, animation);

        this.speed = speed;
    }

    /**
     * Get monster speed
     *
     * @return monster speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set monster speed
     *
     * @param speed new monster speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Monster fall behaviour
     */
    @Override
    public void fall() {
        this.setY(getY() + 1);
    }

    /**
     * Monster move behaviour using monster speed
     *
     * @param left has to move left (true) or right (false)
     */
    @Override
    public void move(boolean left) {
        setX(getX() + (left ? -this.speed : this.speed));
    }

    /**
     * Monster move behaviour
     *
     * @param currentSpeed current speed of movement
     */
    @Override
    public void move(int currentSpeed) {
        setX(getX() + currentSpeed);
    }
}
