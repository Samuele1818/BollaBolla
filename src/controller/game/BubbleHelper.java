package controller.game;

import controller.GameController;
import model.Level;
import model.Model;
import model.entity.monster.Monster;
import model.entity.objects.Brick;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.Bubble;
import model.entity.objects.consumable.bubble.EffectBubble;
import model.entity.objects.consumable.bubble.NormalBubble;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class BubbleHelper {
    private static BubbleHelper instance = null;
    private Model model;

    /**
     * BubbleHelper constructor
     * Init Model
     */
    private BubbleHelper() {
        model = Model.getInstance();
    }

    /**
     * Get BubbleHelper instance
     *
     * @return BubbleHelper instance
     */
    public static BubbleHelper getInstance() {
        if (instance == null) instance = new BubbleHelper();
        return instance;
    }

    /**
     * Check if character can fire a bubble and use <code>setBubbleDelay</code> to set a flag
     * This function use a new Thread to avoid block current thread execution (player has to be able to move while this function execute)
     */
    public void bubbleFireDelay() {
        GameController.getInstance().setBubbleDelay(true);
        int app;
        if (model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.YELLOW_CANDY))
            app = 300;
        else
            app = 400;

        Thread a = new Thread(() -> {

            try {
                Thread.sleep(app);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!GameController.getInstance().isDead())
                switch (model.getLevel().getMainCharacter().getCurrentAnimation()) {
                    case LEFT -> model.getLevel().getMainCharacter().changePath(Level.Animation.LEFT);
                    case RIGHT -> model.getLevel().getMainCharacter().changePath(Level.Animation.RIGHT);
                }

            GameController.getInstance().setFiring(false);
            GameController.getInstance().setBubbleDelay(false);
        });

        a.start();
    }

    /**
     * Check if an entity has been hit from a bubble
     *
     * @param x entity x coordinate
     * @param y entity y coordinate
     * @return if an entity has been hit from a bubble
     */
    public boolean hitFromBubble(int x, int y) {
        return model.getLevel().getBubbles().parallelStream()
                .anyMatch(bubble -> {
                            boolean hit = false;
                            for (int i = 0; i < bubble.getHeight(); i++) {
                                hit = x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y >= bubble.getY() && y < bubble.getY() + 32;
                                hit |= x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= bubble.getY() && y + model.getLevel().getMainCharacter().getHeight() < bubble.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }

    /**
     * Get bubble that hit an entity
     * Useful to change bubble state after an entity has been hit
     *
     * @param x entity x coordinate
     * @param y entity y coordinate
     * @return bubble that hit an entity
     */
    public NormalBubble getBubbleHit(int x, int y) {
        return model.getLevel().getBubbles().parallelStream()
                .filter(bubble -> {
                            boolean hit = false;
                            for (int i = 0; i < bubble.getHeight(); i++) {
                                hit = x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y >= bubble.getY() && y < bubble.getY() + 32;
                                hit |= x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= bubble.getY() && y + model.getLevel().getMainCharacter().getHeight() < bubble.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }

    /**
     * Move all the normal bubbles present in the level using <code>moveNormalBubble</code> method
     */
    public void moveNormalBubbles() {
        for (NormalBubble bubble : GameController.getInstance().getAddBubbles())
            model.getLevel().getBubbles().add(bubble);

        GameController.getInstance().getAddBubbles().clear();

        CopyOnWriteArrayList<NormalBubble> bubbles = new CopyOnWriteArrayList<>(model.getLevel().getBubbles());
        for (NormalBubble bubble : bubbles)
            moveNormalBubble(bubble);


        for (NormalBubble bubble : GameController.getInstance().getRemoveBubbles())
            model.getLevel().getBubbles().remove(bubble);

        GameController.getInstance().getRemoveBubbles().clear();
    }

    /**
     * Make a normal bubble move (bubbles fired from player)
     * Define what to do if the bubble hit an enemy, contains an enemy
     * Modify movement based on powerUps
     *
     * @param bubble bubble to move
     */
    private void moveNormalBubble(NormalBubble bubble) {
        int move = model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.BLUE_CANDY) ? 4 : 3;

        if (bubble.getHorizontalDistanceSetting() > 0) {
            if (bubble.isContainsEnemy()) bubble.clearHorizontalDistance();

            if (!bubble.isContainsEnemy())
                if (isValidBubblePosition(bubble)) {
                    bubble.decreaseHorizontalDistanceSetting();

                    switch (bubble.getCurrentAnimation()) {
                        case LEFT:
                            bubble.move(-move);
                            break;

                        case RIGHT:
                            bubble.move(move);
                    }
                } else {
                    bubble.changePath(Level.Animation.BUBBLE_END);
                    bubble.clearHorizontalDistance();
                }
        }

        if (bubble.getHorizontalDistanceSetting() == 0)
            bubble.changePath(Level.Animation.BUBBLE_END);

        if (bubble.getHorizontalDistanceSetting() <= 0) {
            if (model.getLevel().getBricks().stream().noneMatch(brick -> brick.getY() + Brick.HEIGHT == bubble.getY() &&
                    ((brick.getX() < bubble.getX() && brick.getX() + Brick.WIDTH >= bubble.getX()) ||
                            (brick.getX() < bubble.getX() + 16 && brick.getX() + Brick.WIDTH >= bubble.getX() + 16) ||
                            (brick.getX() < bubble.getX() + bubble.getWidth() && brick.getX() + Brick.WIDTH >= bubble.getX() + bubble.getWidth())
                    )
            )) {
                bubble.fly(0, -1);

            } else {
                switch (bubble.getCurrentAnimation()) {
                    case LEFT:
                        if (model.getLevel().getBricks().
                                stream().filter(brick -> brick.getX() <= bubble.getX() && ((brick.getY() + 16 > bubble.getY() && brick.getY() <= bubble.getY()) || (brick.getY() + 16 > bubble.getY() + 16 && brick.getY() <= bubble.getY() + 16))).
                                allMatch(brick -> brick.getX() + Brick.WIDTH < bubble.getX())
                        )
                            bubble.move(-1);

                        else
                            bubble.setCurrentAnimation(Level.Animation.RIGHT);

                        break;

                    case RIGHT:
                        if (model.getLevel().getBricks().
                                stream().filter(brick -> brick.getX() >= bubble.getX() && ((brick.getY() + 16 > bubble.getY() && brick.getY() <= bubble.getY()) || (brick.getY() + 16 > bubble.getY() + 16 && brick.getY() <= bubble.getY() + 16))).
                                allMatch(brick -> brick.getX() > bubble.getX() + bubble.getWidth()))
                            bubble.move(1);

                        else
                            bubble.setCurrentAnimation(Level.Animation.LEFT);
                }
            }
        }

        if (bubble.getY() < 0) EnemiesHelper.getInstance().respawnEnemy(bubble);
    }

    /**
     * Make an effect bubble move
     *
     * @param bubble effect bubble to move
     */
    private void moveEffectBubble(EffectBubble bubble) {
        if (model.getLevel().getBricks().stream().noneMatch(brick -> brick.getY() + Brick.HEIGHT == bubble.getY() &&
                ((brick.getX() < bubble.getX() && brick.getX() + Brick.WIDTH >= bubble.getX()) ||
                        (brick.getX() < bubble.getX() + 16 && brick.getX() + Brick.WIDTH >= bubble.getX() + 16) ||
                        (brick.getX() < bubble.getX() + bubble.getWidth() && brick.getX() + Brick.WIDTH >= bubble.getX() + bubble.getWidth())
                )
        )) {
            bubble.fly(0, -1);
        } else
            switch (bubble.getCurrentAnimation()) {
                case LEFT:
                    if (model.getLevel().getBricks().
                            stream().filter(brick -> brick.getX() <= bubble.getX() && ((brick.getY() + 16 > bubble.getY() && brick.getY() <= bubble.getY()) || (brick.getY() + 16 > bubble.getY() + 16 && brick.getY() <= bubble.getY() + 16))).
                            allMatch(brick -> brick.getX() + Brick.WIDTH < bubble.getX())
                    )
                        bubble.move(-1);

                    else
                        bubble.setCurrentAnimation(Level.Animation.RIGHT);
                    break;

                case RIGHT:
                    if (model.getLevel().getBricks().
                            stream().filter(brick -> brick.getX() >= bubble.getX() && ((brick.getY() + 16 > bubble.getY() && brick.getY() <= bubble.getY()) || (brick.getY() + 16 > bubble.getY() + 16 && brick.getY() <= bubble.getY() + 16))).
                            allMatch(brick -> brick.getX() > bubble.getX() + bubble.getWidth()))
                        bubble.move(1);
                    else
                        bubble.setCurrentAnimation(Level.Animation.LEFT);
            }
        if (bubble.getY() < 0)
            GameController.getInstance().getRemoveEffectBubbles().add(bubble);

    }

    /**
     * Move all the effect bubbles present in the level using <code>moveEffectBubble</code> method
     */
    public void moveEffectBubbles() {
        for (EffectBubble bubble : GameController.getInstance().getAddEffectBubbles())
            model.getLevel().getEffectBubbles().add(bubble);

        GameController.getInstance().getAddEffectBubbles().clear();

        CopyOnWriteArrayList<EffectBubble> effectBubbles = new CopyOnWriteArrayList<>(model.getLevel().getEffectBubbles());
        for (EffectBubble bubble : effectBubbles)
            moveEffectBubble(bubble);

        for (EffectBubble bubble : GameController.getInstance().getRemoveEffectBubbles())
            model.getLevel().getEffectBubbles().remove(bubble);
        GameController.getInstance().getRemoveEffectBubbles().clear();
    }

    /**
     * Return the effect bubble that has hit something
     *
     * @param x entity x coordinate
     * @param y entity y coordinate
     * @return effect bubble that has hit something
     */
    private EffectBubble getHitFromEffectBubble(int x, int y) {
        return model.getLevel().getEffectBubbles().stream()
                .filter(bubble -> {
                            boolean hit = false;
                            for (int i = 0; i < bubble.getHeight(); i++) {
                                hit = x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y >= bubble.getY() && y < bubble.getY() + 32;
                                hit |= x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= bubble.getY() && y + model.getLevel().getMainCharacter().getHeight() < bubble.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }

    /**
     * Check if effect bubble hit something
     *
     * @param x entity x coordinate
     * @param y entity y coordinate
     * @return if effect bubble hit something
     */
    private boolean hitFromEffectBubble(int x, int y) {
        return model.getLevel().getEffectBubbles().stream()
                .anyMatch(bubble -> {
                            boolean hit = false;
                            for (int i = 0; i < bubble.getHeight(); i++) {
                                hit = x + i >= bubble.getX() && x + i < bubble.getX() + bubble.getWidth() && y >= bubble.getY() && y < bubble.getY() + bubble.getHeight();
                                hit |= x + i >= bubble.getX() && x + i < bubble.getX() + bubble.getWidth() && y + model.getLevel().getMainCharacter().getHeight() >= bubble.getY() && y + model.getLevel().getMainCharacter().getHeight() < bubble.getY() + bubble.getHeight();
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }

    /**
     * Dispatch effect of the effect bubble if it has been hit
     * Use <code>hitFromEffectBubble</code> and <code>getHitFromEffectBubble</code>
     */
    public void dispatchEffectBubbleEffect() {
        if (hitFromEffectBubble(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {
            EffectBubble bubble = getHitFromEffectBubble(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY());
            GameController.getInstance().getRemoveEffectBubbles().add(bubble);
            GameController.getInstance().increaseScore(bubble.getScore());
            switch (bubble.getType()) {
                case BUBBLE_HEALTH ->
                        model.getLevel().getMainCharacter().setHealth(model.getLevel().getMainCharacter().getHealth() + 1);

                case BUBBLE_LIGHTNING -> {
                    bubbleLightningEffect();
                    GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.CLOCK, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.CLOCK) - 1);
                }
            }
        }
    }

    /**
     * Make effect bubble spawn
     * Lightning Bubble: has 1/3 spawn chance at level start if player has reached at least a score of 1000
     * Health Bubble: has 1/3 spawn chance at level start if player has only one life
     * Spawn nothing: has 1/3 spawn chance
     * Use <code>spawnHealthBubble</code> and <code>spawnLightningBubble</code>
     */
    public void generateEffectBubble() {
        switch ((int) (Math.random() * 2)) {
            case 0:
                spawnHealthBubble();
                break;
            case 1:
                spawnLightningBubble();
                break;
        }
    }

    /**
     * Spawn an <code>HealthBubble</code> if player has one life
     * Spawn at random position in the level map (at a valid position)
     */
    private void spawnHealthBubble() {
        int minX = 64;
        int maxX = 432;
        int minY = 48;
        int maxY = 400;
        EffectBubble bubble;
        int randomX, randomY;
        Random random = new Random();
        if (model.getLevel().getMainCharacter().getHealth() == 1) {
            do {
                randomX = random.nextInt((maxX - minX) + 1) + minX;
                randomY = random.nextInt((maxY - minY) + 1) + minY;
                bubble = new EffectBubble(randomX, randomY, EffectBubble.Type.BUBBLE_HEALTH);
            } while (!isValidBubblePosition(bubble));
            GameController.getInstance().getAddEffectBubbles().add(bubble);
        }
    }

    /**
     * Spawn an <code>LightningBubble</code> if player has at least a score of 1000
     * Spawn at random position in the level map (at a valid position)
     */
    private void spawnLightningBubble() {
        int minX = 64;
        int maxX = 432;
        int minY = 48;
        int maxY = 400;
        EffectBubble bubble;
        int randomX, randomY;
        Random random = new Random();
        if (model.getLevel().getScore() >= 1000) {
            do {

                randomX = random.nextInt((maxX - minX) + 1) + minX;
                randomY = random.nextInt((maxY - minY) + 1) + minY;
                bubble = new EffectBubble(randomX, randomY, EffectBubble.Type.BUBBLE_LIGHTNING);
            } while (!isValidBubblePosition(bubble));
            GameController.getInstance().getAddEffectBubbles().add(bubble);
        }
    }

    /**
     * Spawn a <code>NormalBubble</code> besides every enemy to imprison them
     */
    private void bubbleLightningEffect() {
        CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<>(model.getLevel().getEnemies());
        for (Monster monster : monsters)
            GameController.getInstance().getAddBubbles().add(new NormalBubble(monster.getX(), monster.getY(), monster.getCurrentAnimation()));
    }

    /**
     * Check if a bubble is in a valid position (not in a brick) to make it spawn correctly
     *
     * @param bubble bubble to check position
     * @return if a bubble is in a valid position (not in a brick) to make it spawn correctly
     */
    public boolean isValidBubblePosition(Bubble bubble) {
        return model.getLevel().getBricks().stream().noneMatch(
                brick -> {
                    boolean resultY = false;
                    boolean resultX = false;
                    for (int i = 0; i < bubble.getHeight(); i++) {
                        resultY |= brick.getY() < bubble.getY() + i && brick.getY() + Brick.HEIGHT > bubble.getY() + i;
                        resultX |= brick.getX() + Brick.WIDTH >= bubble.getX() + i && brick.getX() <= bubble.getX() + i;
                    }

                    return resultY && resultX;
                }
        );
    }
}
