package controller.game;

import controller.GameController;
import model.Level;
import model.Model;
import model.entity.monster.Character;
import model.entity.objects.Brick;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.NormalBubble;
import view.View;

public class MainCharacterHelper {
    private static MainCharacterHelper instance;
    private Model model;
    private View view;

    /**
     * MainCharacterHelper constructor
     * Init View and Model
     */
    private MainCharacterHelper() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    /**
     * Get MainCharacterHelper instance
     *
     * @return MainCharacterHelper instance
     */
    public static MainCharacterHelper getInstance() {
        if (instance == null) instance = new MainCharacterHelper();
        return instance;
    }

    /**
     * Make main character dead animation, <code>setDead</code> true to pause game cycle thread
     * Reset level (delete bubbles, loots powerUps present in the level, respawn all enemies) using
     * Clear active powerUps on main character
     */
    public void dead() {
        GameController.getInstance().setDead(true);

        model.getLevel().getMainCharacter().resetHumpHeight();

        switch (model.getLevel().getMainCharacter().getCurrentAnimation()) {
            case LEFT:
                model.getLevel().getMainCharacter().changePath(Level.Animation.DEAD_LEFT);
                break;
            case RIGHT:
                model.getLevel().getMainCharacter().changePath(Level.Animation.DEAD_RIGHT);
        }
        try {
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        view.getMapPanel().getMainCharacterImage().flush();
        model.getLevel().getEnemies().clear();
        model.getLevel().getBubbles().clear();
        model.getLevel().getEffectBubbles().clear();
        model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth() - 1, model.getLevel().getLevel(), null);
        NormalBubble.setHorizontalDistanceSetting(32);
        GameController.getInstance().setCurrentSpeed(0);
        GameController.getInstance().setDead(false);
    }

    /**
     * Make main character move
     */
    public void move() {
        //hit character control
        if (EnemiesHelper.getInstance().hitFromEnemies(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {
            Thread a = new Thread(this::dead);
            a.start();
            return;
        }

        //movement either to the right or to the left
        if (GameController.getInstance().getCurrentSpeed() != 0 && isValidPosition(model.getLevel().getMainCharacter().getCurrentAnimation(), model.getLevel().getMainCharacter().getX() + GameController.getInstance().getCurrentSpeed(), model.getLevel().getMainCharacter().getY())) {
            model.getLevel().getMainCharacter().move(GameController.getInstance().getCurrentSpeed());
            GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.RED_SHOE, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.RED_SHOE) - 1);
            if (model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.BLUE_RING))
                GameController.getInstance().increaseScore(10);
        }
        if (model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.RED_SHOE))
            if (GameController.getInstance().getCurrentSpeed() != 0 && isValidPosition(model.getLevel().getMainCharacter().getCurrentAnimation(), model.getLevel().getMainCharacter().getX() + GameController.getInstance().getCurrentSpeed(), model.getLevel().getMainCharacter().getY()))
                model.getLevel().getMainCharacter().move(GameController.getInstance().getCurrentSpeed());

        //possibility of jumping
        if (model.getLevel().getMainCharacter().isJumping() && model.getLevel().getMainCharacter().getJumpHeight() > 0) {

            model.getLevel().getMainCharacter().jump();
            model.getLevel().getMainCharacter().setJumpHeight(model.getLevel().getMainCharacter().getJumpHeight() - 1);

        }
        if (model.getLevel().getMainCharacter().isJumping() && model.getLevel().getMainCharacter().getJumpHeight() > 0) {
            model.getLevel().getMainCharacter().jump();
            model.getLevel().getMainCharacter().setJumpHeight(model.getLevel().getMainCharacter().getJumpHeight() - 1);

        }

        //end-of-jump control
        if (model.getLevel().getMainCharacter().getJumpHeight() == 0) {
            if (fall(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {
                model.getLevel().getMainCharacter().setJumping(false);
                model.getLevel().getMainCharacter().resetHumpHeight();

            }
        }

        //fall control
        if (!model.getLevel().getMainCharacter().isJumping() || model.getLevel().getMainCharacter().canJump()) {
            model.getLevel().getMainCharacter().setCanJump(!fall(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY()));
        }

        if (!model.getLevel().getMainCharacter().isJumping() || model.getLevel().getMainCharacter().canJump()) {
            model.getLevel().getMainCharacter().setCanJump(!fall(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY()));
        }
    }

    /**
     * Change character animation in case of direction change and fire
     * Check if character is in a valid position (not going against a brick) using <code>isValidPositionHelper</code>
     *
     * @param direction character current direction
     * @param x         character x coordinate
     * @param y         character y coordinate
     * @return <code>isValidPositionHelper</code>
     */
    private boolean isValidPosition(Level.Animation direction, int x, int y) {
        model.getLevel().getMainCharacter().changePath(model.getLevel().getMainCharacter().getCurrentAnimation());
        fireAnimation();

        return isValidPositionHelper(direction, x, y);
    }

    /**
     * Check if character is in a valid position (not going against a brick)
     *
     * @param direction character current direction
     * @param x         character x coordinate
     * @param y         character y coordinate
     * @return if character is in a valid position
     */
    private boolean isValidPositionHelper(Level.Animation direction, int x, int y) {
        return switch (direction) {
            case LEFT -> model.getLevel().getBricks().
                    stream().filter(brick -> brick.getX() <= x && ((brick.getY() + 16 > y && brick.getY() <= y) || (brick.getY() + 16 > y + 16 && brick.getY() <= y + 16))).
                    allMatch(brick -> brick.getX() + Brick.WIDTH <= x) && model.getLevel().getMainCharacter().getX() > Brick.HEIGHT * 3;
            case RIGHT -> model.getLevel().getBricks().
                    stream().filter(brick -> brick.getX() >= x && ((brick.getY() + 16 > y && brick.getY() <= y) || (brick.getY() + 16 > y + 16 && brick.getY() <= y + 16))).
                    allMatch(brick -> brick.getX() >= x + Character.WIDTH) && model.getLevel().getMainCharacter().getX() + Character.WIDTH < View.WINDOWS_WIDTH - (3 * Brick.HEIGHT);
            default -> false;
        };
    }

    /**
     * Change image if character is firing to render the fire animation
     */
    public void fireAnimation() {
        if (GameController.getInstance().isFiring()) {
            switch (model.getLevel().getMainCharacter().getCurrentAnimation()) {
                case LEFT:
                    model.getLevel().getMainCharacter().changePath(Level.Animation.FIRE_LEFT);
                    break;
                case RIGHT:
                    model.getLevel().getMainCharacter().changePath(Level.Animation.FIRE_RIGHT);
                    break;
            }
        }
    }

    /**
     * Check if character is falling
     *
     * @param x character x coordinate
     * @param y character y coordinate
     * @return true if character is falling else false
     */
    private boolean fall(int x, int y) {
        boolean isFall;

        if (y < 48) {
            model.getLevel().getMainCharacter().fall();
            return true;
        }

        if (y > 384)
            return false;

        isFall = model.getLevel().getBricks().stream().
                noneMatch(brick -> brick.getY() == y + Character.HEIGHT && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16)));

        if (isFall) {
            model.getLevel().getMainCharacter().fall();
            return true;
        }

        isFall = model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y + 16 && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );

        if (isFall) {
            model.getLevel().getMainCharacter().fall();
            return true;
        }

        isFall = model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );

        if (isFall) {
            model.getLevel().getMainCharacter().fall();
            return true;
        }

        return false;
    }
}
