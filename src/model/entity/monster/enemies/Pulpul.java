package model.entity.monster.enemies;

import model.Level;
import model.entity.Monster;
import model.sam.Jump;

public class Pulpul extends Enemies implements Jump {


    public boolean isJump;
    public int sizeJump;
    public boolean possibilityOfJumping;

    public Pulpul(int x, int y) {
        super(x, y, Monster.Type.PULPUL);
        isJump = false;
        sizeJump = 50;
        possibilityOfJumping = true;
    }

    public Pulpul(int x, int y, String path, Level.Direction direction) {
        super(x, y, Type.PULPUL, 1, path, direction);
        isJump = false;
        sizeJump = 50;
        possibilityOfJumping = true;

    }

    public void resetJump() {
        this.sizeJump = 50;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    public int getSizeJump() {
        return sizeJump;
    }

    public void decreaseSizeJump() {
        this.sizeJump -= 1;
    }

    public boolean isPossibilityOfJumping() {
        return possibilityOfJumping;
    }

    public void setPossibilityOfJumping(boolean possibilityOfJumping) {
        this.possibilityOfJumping = possibilityOfJumping;
    }

    @Override
    public void jump() {
        setY(getY() - 2);
    }
}
