package controller.game;

import controller.GameController;
import model.Level;
import model.Model;
import model.entity.monster.Character;
import model.entity.monster.enemies.*;
import model.entity.objects.Brick;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.NormalBubble;
import view.View;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class EnemiesHelper {

    private static EnemiesHelper instance = null;
    private Model model;

    /**
     * EnemiesHelper constructor
     * Init Model
     */
    private EnemiesHelper() {
        model = Model.getInstance();
    }

    /**
     * Get EnemiesHelper instance
     *
     * @return EnemiesHelper instance
     */
    public static EnemiesHelper getInstance() {
        if (instance == null) instance = new EnemiesHelper();
        return instance;
    }

    /**
     * Check if enemies of the level contained in <code>enemies</code> array has to move
     * Every enemy has a custom move function
     * Check also if Clock powerUp counter is 0 (else enemies cannot move)
     */
    public void enemiesMove() {
        for (Enemy enemy : controller.GameController.getInstance().getAddEnemies())
            model.getLevel().getEnemies().add(enemy);
        GameController.getInstance().getAddEnemies().clear();

        for (Enemy enemy : GameController.getInstance().getRemoveEnemies())
            model.getLevel().getEnemies().remove(enemy);
        GameController.getInstance().getRemoveEnemies().clear();

        CopyOnWriteArrayList<Enemy> monsters = new CopyOnWriteArrayList<>(model.getLevel().getEnemies());
        for (Enemy monster : monsters) {
            switch (monster.getClass().getSimpleName()) {
                case "Zenchan":
                    enemyFall(monster);
                    if (enemyFall(monster)) break;
                    if (GameController.getInstance().getClockCounter() == 0)
                        zenchanMove((Zenchan) monster);
                    break;
                case "Invader":
                    if (enemyFall(monster)) break;
                    if (GameController.getInstance().getClockCounter() == 0)
                        invaderMove((Invader) monster);
                    break;
                case "Monsta":
                    if (enemyFall(monster)) break;
                    if (GameController.getInstance().getClockCounter() == 0)
                        monstaMove((Monsta) monster);
                    break;
                case "Mighta":
                    if (GameController.getInstance().getClockCounter() == 0)
                        mightaMove((Mighta) monster);
                    break;
                case "Pulpul":
                    if (GameController.getInstance().getClockCounter() == 0)
                        pulpulMove((Pulpul) monster);
                    break;
                case "Drunk":
                    if (enemyFall(monster)) break;
                    if (GameController.getInstance().getClockCounter() == 0)
                        drunkMove((Drunk) monster);
                    break;
            }
        }

        if (GameController.getInstance().getClockCounter() != 0)
            GameController.getInstance().setClockCounter(GameController.getInstance().getClockCounter() - 1);
    }

    /**
     * Make Zenchan move
     *
     * @param zenchan Zenchan enemy instance
     */
    private void zenchanMove(Zenchan zenchan) {
        int newX;
        switch (zenchan.getCurrentAnimation()) {
            case LEFT:
                newX = zenchan.getX() - zenchan.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == zenchan.getY() + zenchan.getHeight() && (newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH))
                        && GameController.getInstance().isValidPositionBrick(zenchan.getCurrentAnimation(), newX, zenchan.getY()))

                    zenchan.move(true);

                else {
                    zenchan.setCurrentAnimation(Level.Animation.RIGHT);
                    zenchan.changePath(Level.Animation.RIGHT);
                }

                break;

            case RIGHT:
                newX = zenchan.getX() + zenchan.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == zenchan.getY() + zenchan.getHeight() && (newX + zenchan.getWidth() >= brick.getX() && newX + zenchan.getWidth() < brick.getX() + Brick.WIDTH))
                        && GameController.getInstance().isValidPositionBrick(zenchan.getCurrentAnimation(), newX, zenchan.getY()))
                    zenchan.move(false);
                else {
                    zenchan.setCurrentAnimation(Level.Animation.LEFT);
                    zenchan.changePath(Level.Animation.LEFT);
                }
                break;
        }
    }

    /**
     * Make Monsta move
     *
     * @param monsta Monsta enemy instance
     */
    private void monstaMove(Monsta monsta) {
        switch (monsta.getCurrentAnimation()) {
            case LEFT -> {
                if (model.getLevel().getBricks().stream().noneMatch(brick -> (brick.getY() == monsta.getY() || brick.getY() == monsta.getY() + 16) && monsta.getX() == brick.getX() + brick.getWidth()))
                    monsta.move(true);

                else {
                    monsta.setCurrentAnimation(Level.Animation.RIGHT);
                    monsta.changePath(Level.Animation.RIGHT);
                }
            }
            case RIGHT -> {
                if (model.getLevel().getBricks().stream().filter(brick -> brick.getX() >= monsta.getX()).noneMatch(brick -> (brick.getY() == monsta.getY() || brick.getY() == monsta.getY() + 16) && monsta.getX() + monsta.getWidth() == brick.getX()))
                    monsta.move(false);

                else {
                    monsta.setCurrentAnimation(Level.Animation.LEFT);
                    monsta.changePath(Level.Animation.LEFT);
                }
            }
        }
    }

    /**
     * Make invader move
     *
     * @param invader Invader enemy instace
     */
    private void invaderMove(Invader invader) {
        int newX;

        if (!invader.hasFear()) {
            if (model.getLevel().getMainCharacter().getX() == invader.getX()) return;

            if (model.getLevel().getMainCharacter().getX() < invader.getX()) {
                invader.setCurrentAnimation(Level.Animation.LEFT);
                invader.changePath(Level.Animation.LEFT);

            } else {
                invader.setCurrentAnimation(Level.Animation.RIGHT);
                invader.changePath(Level.Animation.RIGHT);
            }
        }

        switch (invader.getCurrentAnimation()) {
            case RIGHT -> {
                newX = invader.getX() + invader.getSpeed();

                if (model.getLevel().getBricks().stream().
                        anyMatch(brick -> brick.getY() == invader.getY() + invader.getHeight() && ((newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH) || (newX + invader.getWidth() >= brick.getX() && newX + invader.getWidth() < brick.getX() + Brick.WIDTH)))
                        && GameController.getInstance().isValidPositionBrick(invader.getCurrentAnimation(), newX, invader.getY()))

                    invader.move(false);
                else {

                    invader.setCurrentAnimation(Level.Animation.LEFT);
                    invader.changePath(Level.Animation.LEFT);

                    invader.setSpeed(3);
                    invader.setHasFear(true);
                }
            }
            case LEFT -> {
                newX = invader.getX() - invader.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == invader.getY() + invader.getHeight() && ((newX + invader.getWidth() > brick.getX() && newX + invader.getWidth() <= brick.getX() + Brick.WIDTH) || newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH))
                        && GameController.getInstance().isValidPositionBrick(invader.getCurrentAnimation(), newX, invader.getY()))
                    invader.move(true);
                else {
                    invader.setCurrentAnimation(Level.Animation.RIGHT);
                    invader.changePath(Level.Animation.RIGHT);
                    invader.setSpeed(3);
                    invader.setHasFear(true);
                }
            }
        }
        if (invader.hasFear())
            invader.decreaseFearSpace();

        if (invader.getFearSpace() == 0) {
            invader.setHasFear(false);
            invader.resetFearSpace();
            invader.setSpeed(1);
        }

    }

    /**
     * Make Mighta move
     *
     * @param mighta Mighta enemy instance
     */
    private void mightaMove(Mighta mighta) {
        int x;
        int y;
        if (model.getLevel().getMainCharacter().getX() < mighta.getX()) {
            x = -1;
            mighta.changePath(Level.Animation.LEFT);
        } else if (model.getLevel().getMainCharacter().getX() == mighta.getX())
            x = 0;
        else {
            x = 1;
            mighta.changePath(Level.Animation.RIGHT);
        }

        if (model.getLevel().getMainCharacter().getY() < mighta.getY())
            y = -1;
        else
            y = 1;

        mighta.fly(x, y);
    }

    /**
     * Make Pulpul move
     * Use <code>controlPulpulMovement</code>
     *
     * @param pulpul Pulpul enemy instance
     */
    private void pulpulMove(Pulpul pulpul) {
        pulpul.setSpeed(1);
        if (model.getLevel().getMainCharacter().getY() == pulpul.getY()) {
            if (model.getLevel().getMainCharacter().getX() >= pulpul.getX())
                pulpul.setCurrentAnimation(Level.Animation.RIGHT);
            else
                pulpul.setCurrentAnimation(Level.Animation.LEFT);
            pulpul.setSpeed(2);
        }
        if (!pulpulFall(pulpul) && model.getLevel().getMainCharacter().getY() + 32 < pulpul.getY())
            pulpul.setJumping(true);
        if (!pulpul.isJumping) enemyFall(pulpul);
        if (pulpul.isJumping()) {
            if (model.getLevel().getMainCharacter().getX() >= pulpul.getX())
                pulpul.setCurrentAnimation(Level.Animation.RIGHT);
            else
                pulpul.setCurrentAnimation(Level.Animation.LEFT);
            pulpul.jump();
            pulpul.decreaseJumpHeight();
        }
        if (pulpul.getJumpHeight() == 0) {
            pulpul.setJumping(false);
            pulpul.resetJumpHeight();
        }

        controlPulpulMovement(pulpul);
    }

    /**
     * Check if Pulpul is falling
     *
     * @param pulpul Pulpul enemy instance
     * @return if Pulpul is falling
     */
    private boolean pulpulFall(Pulpul pulpul) {
        boolean isFall;
        int x = pulpul.getX();
        int y = pulpul.getY();


        if (y < 48)
            return true;


        isFall = model.getLevel().getBricks().stream().
                noneMatch(brick -> brick.getY() == y + Character.HEIGHT && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16)));


        if (isFall)
            return true;

        isFall = model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y + 16 && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );
        if (isFall)
            return true;

        return model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );
    }

    /**
     * Change Pulpul direction to reach the player position
     *
     * @param pulpul Pulpul enemy instance
     */
    private void changePulpulDirection(Pulpul pulpul) {
        switch (pulpul.getCurrentAnimation()) {
            case LEFT -> {
                if (model.getLevel().getBricks().stream().noneMatch(brick -> (brick.getY() == pulpul.getY() || brick.getY() == pulpul.getY() + 16) && pulpul.getX() == brick.getX() + brick.getWidth()))
                    pulpul.move(-pulpul.getSpeed());
                else {
                    pulpul.setCurrentAnimation(Level.Animation.RIGHT);
                    pulpul.changePath(Level.Animation.RIGHT);
                }
            }
            case RIGHT -> {

                if (model.getLevel().getBricks().stream().filter(brick -> brick.getX() >= pulpul.getX()).noneMatch(brick -> (brick.getY() == pulpul.getY() || brick.getY() == pulpul.getY() + 16) && pulpul.getX() + pulpul.getWidth() == brick.getX()))
                    pulpul.move(pulpul.getSpeed());
                else {
                    pulpul.setCurrentAnimation(Level.Animation.LEFT);
                    pulpul.changePath(Level.Animation.LEFT);
                }
            }
        }
    }

    /**
     * Handle Pulpul jumping and falling
     * Make Pulpul change direction if is needed, and it is not jumping / falling
     * Use <code>changePulpulDirection</code> and <code>enemiyFall</code> methods
     *
     * @param pulpul Pulpul enemy instance
     */
    private void controlPulpulMovement(Pulpul pulpul) {
        if (pulpul.isJumping()) {
            changePulpulDirection(pulpul);
            return;
        }

        if (!enemyFall(pulpul) || !enemyFall(pulpul)) changePulpulDirection(pulpul);
    }

    /**
     * Check if an enemy is falling
     *
     * @param enemy enemy on which make fall check instance
     * @return if an enemy is falling
     */
    private boolean enemyFall(Enemy enemy) {
        boolean isFall;
        int x = enemy.getX();
        int y = enemy.getY();

        if (y < 48) {
            enemy.fall();
            return true;
        }

        if (y > 384)
            return false;

        isFall = model.getLevel().getBricks().stream().
                noneMatch(brick -> brick.getY() == y + Character.HEIGHT && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16)));


        if (isFall) {
            enemy.fall();
            return true;
        }
        isFall = model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y + 16 && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );
        if (isFall) {
            enemy.fall();
            return true;
        }
        isFall = model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );
        if (isFall) {
            enemy.fall();
            return true;
        }

        return false;
    }

    /**
     * Make Drunk move simulating speed of 4 <code>drunkMoveHelper</code> method
     *
     * @param drunk Drunk enemy instance
     */
    private void drunkMove(Drunk drunk) {
        for (int i = 0; i < drunk.getSpeed(); i++)
            if (!enemyFall(drunk))
                drunkMoveHelper(drunk);
    }

    /**
     * Make Drunk move
     *
     * @param drunk Drunk enemy instance
     */
    private void drunkMoveHelper(Drunk drunk) {
        int newX;
        switch (drunk.getCurrentAnimation()) {
            case LEFT:
                newX = drunk.getX() - drunk.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == drunk.getY() + drunk.getHeight() && (newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH))
                        && GameController.getInstance().isValidPositionBrick(drunk.getCurrentAnimation(), newX, drunk.getY()))

                    drunk.move(true);

                else {
                    drunk.setCurrentAnimation(Level.Animation.RIGHT);
                    drunk.changePath(Level.Animation.RIGHT);
                }

                break;

            case RIGHT:
                newX = drunk.getX() + drunk.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == drunk.getY() + drunk.getHeight() && (newX + drunk.getWidth() >= brick.getX() && newX + drunk.getWidth() < brick.getX() + Brick.WIDTH))
                        && GameController.getInstance().isValidPositionBrick(drunk.getCurrentAnimation(), newX, drunk.getY()))

                    drunk.move(false);

                else {
                    drunk.setCurrentAnimation(Level.Animation.LEFT);
                    drunk.changePath(Level.Animation.LEFT);
                }
                break;

        }
    }

    /**
     * Get the enemy that has been hit from main character
     *
     * @param x main character x coordinate
     * @param y main character y coordinate
     * @return the enemy that has been hit from main character
     */
    public Enemy getHitFromEnemies(int x, int y) {
        return model.getLevel().getEnemies().parallelStream()
                .filter(enemies -> {
                            boolean hit = false;
                            for (int i = 0; i < enemies.getHeight(); i++) {
                                hit = x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y >= enemies.getY() && y < enemies.getY() + 32;
                                hit |= x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= enemies.getY() && y + model.getLevel().getMainCharacter().getHeight() < enemies.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }

    /**
     * Check if enemy has hit main character
     *
     * @param x main character x coordinate
     * @param y main character y coordinate
     * @return if enemy has hit main character
     */
    public boolean hitFromEnemies(int x, int y) {
        return model.getLevel().getEnemies().parallelStream()
                .anyMatch(enemies -> {
                            boolean hit = false;
                            for (int i = 0; i < enemies.getHeight(); i++) {
                                hit = x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y >= enemies.getY() && y < enemies.getY() + 32;
                                hit |= x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= enemies.getY() && y + model.getLevel().getMainCharacter().getHeight() < enemies.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }

    /**
     * Make enemy die when player hit the bubble with the trapped enemy and start enemy death animation
     * Remove the bubble with the enemy and increase score of player (caused by the bubble explosion)
     */
    public void enemiesKill() {
        int score;
        if (BubbleHelper.getInstance().hitFromBubble(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {

            NormalBubble bubble = BubbleHelper.getInstance().getBubbleHit(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY());
            if (bubble.getHorizontalDistanceSetting() <= 0) {
                Level.Animation direction = model.getLevel().getMainCharacter().getCurrentAnimation();
                Level.Animation directionMonster;
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.BLUE_CANDY, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.BLUE_CANDY) - 1);
                if (model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.RED_RING))
                    score = 100;
                else
                    score = 10;

                directionMonster = Level.Animation.DEAD_RIGHT;

                if (bubble.getCurrentAnimation() == Level.Animation.LEFT)
                    directionMonster = Level.Animation.DEAD_LEFT;

                switch (bubble.getCharacterPath()) {
                    case "bubble.png", "bubble_end.png":
                        GameController.getInstance().increaseScore(score);
                        GameController.getInstance().getRemoveBubbles().add(bubble);
                        break;
                    case "zenchan.gif":
                        GameController.getInstance().getAddKilledEnemies().add(new Zenchan(bubble.getX(), bubble.getY(), directionMonster.getAnimationFileName(), direction));
                        GameController.getInstance().getRemoveBubbles().add(bubble);
                        break;

                    case "monsta.gif":
                        GameController.getInstance().getAddKilledEnemies().add(new
                                Monsta(bubble.getX(), bubble.getY(), directionMonster.getAnimationFileName(), direction));
                        GameController.getInstance().getRemoveBubbles().add(bubble);
                        break;

                    case "pulpul.gif":
                        GameController.getInstance().getAddKilledEnemies().add(new Pulpul(bubble.getX(), bubble.getY(), directionMonster.getAnimationFileName(), direction));
                        GameController.getInstance().getRemoveBubbles().add(bubble);
                        break;

                    case "mighta.gif":
                        GameController.getInstance().getAddKilledEnemies().add(new Mighta(bubble.getX(), bubble.getY(), directionMonster.getAnimationFileName(), direction));
                        GameController.getInstance().getRemoveBubbles().add(bubble);
                        break;

                    case "invader.gif":
                        GameController.getInstance().getAddKilledEnemies().add(new Invader(bubble.getX(), bubble.getY(), directionMonster.getAnimationFileName(), direction));
                        GameController.getInstance().getRemoveBubbles().add(bubble);
                        break;

                    case "drunk.gif":
                        GameController.getInstance().getAddKilledEnemies().add(new Drunk(bubble.getX(), bubble.getY(), directionMonster.getAnimationFileName(), direction));
                        GameController.getInstance().getRemoveBubbles().add(bubble);
                        break;
                }
            }
        }
    }

    /**
     * Make the first part of the death animation by making enemy fly up
     *
     * @param enemy killed enemy instance
     */
    private void killedEnemiesFlyAnimation(Enemy enemy) {
        int newX = 0;
        int newY = 0;
        switch (enemy.getCurrentAnimation()) {
            case LEFT:
                if (enemy.getY() > 48)
                    newY = -1;
                if (enemy.getX() > 48)
                    newX = -1;
                else
                    enemy.setCurrentAnimation(Level.Animation.RIGHT);
                break;
            case RIGHT:
                if (enemy.getY() > 48)
                    newY = -1;
                if (enemy.getX() + enemy.getWidth() < View.WINDOWS_WIDTH - 48)
                    newX = 1;
                else
                    enemy.setCurrentAnimation(Level.Animation.LEFT);
                break;
        }
        enemy.fly(newX, newY);
    }

    /**
     * Handle death animation
     * Use <code>killedEnemiesFlyAnimation</code> to do first part of animation
     * Then make the enemy fall down using <code>killedEnemyFallAnimation</code> method
     * When enemy touch the ground add the loot generated to <code>addLoots</code> array
     */
    public void killedEnemiesMove() {
        for (Enemy enemy : GameController.getInstance().getAddKilledEnemies())
            model.getLevel().getKilledEnemies().add(enemy);
        GameController.getInstance().getAddKilledEnemies().clear();
        for (Enemy enemy : GameController.getInstance().getRemoveKilledEnemies())
            model.getLevel().getKilledEnemies().remove(enemy);
        GameController.getInstance().getRemoveKilledEnemies().clear();

        CopyOnWriteArrayList<Enemy> monsters = new CopyOnWriteArrayList<>(model.getLevel().getKilledEnemies());
        for (Enemy enemy : monsters) {

            if (enemy.getDeadAnimationLength() > 0) {
                enemy.decreaseDeadAnimationLength();
                killedEnemiesFlyAnimation(enemy);
                killedEnemiesFlyAnimation(enemy);
                killedEnemiesFlyAnimation(enemy);

            } else {

                if (killedEnemyFallAnimation(enemy) || killedEnemyFallAnimation(enemy) || killedEnemyFallAnimation(enemy)) {
                    GameController.getInstance().getRemoveKilledEnemies().add(enemy);
                    switch ((int) (Math.random() * 10)) {
                        case 2:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.BANANA));
                            break;
                        case 3:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.CHERRIES));
                            break;
                        case 4:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.GRAPE));
                            break;
                        case 5:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.ICE_CREAM_CUP));
                            break;
                        case 6:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.LEMON));
                            break;
                        case 7:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.ORANGE));
                            break;
                        case 8:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.PEACH));
                            break;
                        case 9:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.PEAR));
                            break;
                        default:
                            GameController.getInstance().getAddLoots().add(new Loot(enemy.getX(), enemy.getY(), Loot.Type.APPLE));
                            break;
                    }

                }
            }

        }
    }

    /**
     * Check for every enemy if his has been hit by a bubble
     * Imprison it in the bubble if needed and spawn the bubble with the enemy inside
     */
    public void collisionEnemies() {
        ArrayList<NormalBubble> bubbles = model.getLevel().getBubbles().stream().filter(bubble -> bubble.getHorizontalDistanceSetting() > 0).collect(Collectors.toCollection(ArrayList::new));
        for (NormalBubble bubble : bubbles) {
            if (!bubble.isContainsEnemy() && hitFromEnemies(bubble.getX(), bubble.getY())) {
                bubble.setContainsEnemy(true);
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.ORANGE_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.ORANGE_UMBRELLA) - 1);
                Enemy enemy = getHitFromEnemies(bubble.getX(), bubble.getY());
                GameController.getInstance().getRemoveEnemies().add(enemy);

                switch (enemy.getClass().getSimpleName()) {
                    case "Zenchan" -> bubble.changePath(Level.Animation.ZENCHAN_BUBBLE);
                    case "Invader" -> bubble.changePath(Level.Animation.INVADER_BUBBLE);
                    case "Monsta" -> bubble.changePath(Level.Animation.MONSTA_BUBBLE);
                    case "Pulpul" -> bubble.changePath(Level.Animation.PULPUL_BUBBLE);
                    case "Mighta" -> bubble.changePath(Level.Animation.MIGHTA_BUBBLE);
                    case "Drunk" -> bubble.changePath(Level.Animation.DRUNK_BUBBLE);
                }
            }
        }
    }

    /**
     * Check if bubble with enemy inside reached the top margin of the level
     * In this case respawn the enemy and eliminate the bubble
     * Handle <code>powerUpsCounter</code> of PINK_UMBRELLA and RED_UMBRELLA
     *
     * @param bubble bubble that contains enemy
     */
    public void respawnEnemy(NormalBubble bubble) {
        GameController.getInstance().getRemoveBubbles().add(bubble);
        switch (bubble.getCharacterPath()) {
            case "zenchan.gif":
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.PINK_UMBRELLA) - 1);
                GameController.getInstance().getAddEnemies().add(new Zenchan(15 * 16, bubble.getY()));
                break;

            case "invader.gif":
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.PINK_UMBRELLA) - 1);
                GameController.getInstance().getAddEnemies().add(new Invader(15 * 16, bubble.getY()));
                break;

            case "mighta.gif":
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.PINK_UMBRELLA) - 1);
                GameController.getInstance().getAddEnemies().add(new Mighta(15 * 16, bubble.getY()));
                break;

            case "pulpul.gif":
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.PINK_UMBRELLA) - 1);
                GameController.getInstance().getAddEnemies().add(new Pulpul(15 * 16, bubble.getY()));
                break;

            case "drunk.gif":
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.PINK_UMBRELLA) - 1);
                GameController.getInstance().getAddEnemies().add(new Drunk(15 * 16, bubble.getY()));
                break;

            case "monsta.gif":
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.PINK_UMBRELLA) - 1);
                GameController.getInstance().getAddEnemies().add(new Monsta(15 * 16, bubble.getY()));
                break;
            default:
                GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.RED_UMBRELLA, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.RED_UMBRELLA) - 1);
        }

    }

    /**
     * Make the fall part of the death animation by making enemy fall down
     *
     * @param enemy killed enemy instance
     * @return if enemy touched the ground (stop fall animation)
     */
    private boolean killedEnemyFallAnimation(Enemy enemy) {
        boolean isFall;
        int x = enemy.getX();
        int y = enemy.getY();

        if (y < 48) {
            enemy.fall();
            return false;
        }

        isFall = model.getLevel().getBricks().stream().
                noneMatch(brick -> brick.getY() == y + Character.HEIGHT && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16)));

        if (isFall) {
            enemy.fall();
            return false;
        }

        isFall = model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y + 16 && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );

        if (isFall) {
            enemy.fall();
            return false;
        }

        isFall = model.getLevel().getBricks().stream().anyMatch(brick ->
                brick.getY() == y && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))
        );

        if (isFall) {
            enemy.fall();
            return false;
        }

        return true;
    }

}