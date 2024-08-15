package model.entity.monster.enemies;

import model.Level;
import model.behaviour.Jump;

public class Pulpul extends Enemy implements Jump {
    private static final int DEFAULT_JUMP = 50;
    public boolean isJumping;
    public int jumpHeight;
    public boolean canJump;

    /**
     * Invader constructor
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Pulpul(int x, int y) {
        this(x, y, Level.Animation.RIGHT.getAnimationFileName(), Level.Animation.RIGHT);
    }

    /**
     * Invader constructor
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param skinPath  name of current animation file
     * @param animation current monster animation
     */
    public Pulpul(int x, int y, String skinPath, Level.Animation animation) {
        super(x, y, Type.PULPUL, 1, skinPath, animation);
        isJumping = false;
        jumpHeight = DEFAULT_JUMP;
        canJump = true;
    }

    /**
     * Reset jump height to default
     */
    public void resetJumpHeight() {
        this.jumpHeight = DEFAULT_JUMP;
    }

    /**
     * Check if Pulpul is jumping
     *
     * @return is jumping
     */
    public boolean isJumping() {
        return isJumping;
    }

    /**
     * Set if Pulpul is jumping
     *
     * @param isJumping set if Pulpul is jumping
     */
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    /**
     * Get jump height
     *
     * @return jump height
     */
    public int getJumpHeight() {
        return jumpHeight;
    }

    /**
     * Decrease jump height of 1
     */
    public void decreaseJumpHeight() {
        this.jumpHeight -= 1;
    }

    /**
     * Jump behaviour of Pulpul
     */
    @Override
    public void jump() {
        setY(getY() - 2);
    }
}
