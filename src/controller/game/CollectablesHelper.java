package controller.game;

import controller.GameController;
import model.Model;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.NormalBubble;
import model.utils.AudioManager;
import model.utils.FileManager;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectablesHelper {
    private static CollectablesHelper instance = null;

    private Model model;

    /**
     * CollectablesHelper constructor
     * Init Model
     */
    CollectablesHelper() {
        model = Model.getInstance();
    }

    /**
     * Get CollectablesHelper instance
     *
     * @return CollectablesHelper instance
     */
    public static CollectablesHelper getInstance() {
        if (instance == null) instance = new CollectablesHelper();
        return instance;
    }

    /**
     * Reset counter of the clock powerUp
     */
    public void resetClockCounter() {
        GameController.getInstance().setClockCounter(500);
    }

    /**
     * Make powerUps spawn.
     * A powerUp spawns if counter of the powerUp in <code>powerUpsCounter</code> is 0
     * To make powerUp spawn use <code>spawnPowerUp</code>
     */
    public void spawnPowerUps() {
        Set<Map.Entry<PowerUp.Type, Integer>> powerUps = GameController.getInstance().getPowerUpCounter().entrySet();
        for (Map.Entry<PowerUp.Type, Integer> Type : powerUps) {
            switch (Type.getKey()) {

                case PowerUp.Type.BLUE_CANDY, PowerUp.Type.PINK_CANDY,
                     PowerUp.Type.YELLOW_CANDY -> {
                    if (Type.getValue() == 0) {
                        spawnPowerUp(Type.getKey());
                        GameController.getInstance().getPowerUpCounter().put(Type.getKey(), 6);
                    }
                }

                case PowerUp.Type.RED_SHOE -> {
                    if (Type.getValue() == 0) {
                        spawnPowerUp(Type.getKey());
                        GameController.getInstance().getPowerUpCounter().put(Type.getKey(), 1000);
                    }
                }
                case PowerUp.Type.PINK_RING, PowerUp.Type.RED_RING, PowerUp.Type.BLUE_RING -> {
                    if (Type.getValue() == 0) {
                        spawnPowerUp(Type.getKey());
                        GameController.getInstance().getPowerUpCounter().put(Type.getKey(), 3);
                    }
                }
                case PowerUp.Type.PINK_UMBRELLA, PowerUp.Type.ORANGE_UMBRELLA,
                     PowerUp.Type.RED_UMBRELLA -> {
                    if (Type.getValue() == 0) {
                        spawnPowerUp(Type.getKey());
                        GameController.getInstance().getPowerUpCounter().put(Type.getKey(), 5);
                    }
                }
                case PowerUp.Type.CLOCK, PowerUp.Type.YELLOW_LANTERN -> {
                    if (Type.getValue() == 0) {
                        spawnPowerUp(Type.getKey());
                        GameController.getInstance().getPowerUpCounter().put(Type.getKey(), 2);
                    }
                }

            }
        }
    }

    /**
     * Make a single powerUp spawn
     * Calculate coordinates (use maximum height as spawn y coordinate so coordinates checks are not needed cause spawn position is always valid)
     *
     * @param type powerUp type
     */
    public void spawnPowerUp(PowerUp.Type type) {
        Random random = new Random();
        int minX = 64;
        int maxX = 432;
        int randomX = random.nextInt((maxX - minX) + 1) + minX;
        GameController.getInstance().getAddPowerUps().add(new PowerUp(randomX, 48, type));
    }

    /**
     * Reset <code>powerUpsCounter</code> to default values
     */
    public void resetPowerUp() {
        GameController.getInstance().getPowerUpCounter().clear();
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.BLUE_CANDY, 6);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_CANDY, 6);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.RED_SHOE, 1000);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.YELLOW_CANDY, 6);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_RING, 3);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.RED_RING, 3);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.BLUE_RING, 3);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.RED_UMBRELLA, 5);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_UMBRELLA, 5);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.ORANGE_UMBRELLA, 5);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.CLOCK, 2);
        GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.YELLOW_LANTERN, 2);
    }

    /**
     * Check if any powerUp has been hit
     * If a powerUp has been hit, add it to <code>powerUps</code> list and gave his effect to the player
     */
    public void dispatchEffectPowerUp() {
        if (hitFromPowerUps(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {

            PowerUp powerUp = getHitFromPowerUps(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY());

            GameController.getInstance().getRemovePowerUps().add(powerUp);
            GameController.getInstance().increaseScore(powerUp.getScore() + powerUp.getScore());

            AudioManager.getInstance().play(FileManager.getResource("audio", "collect.wav"));
            switch (powerUp.getPowerUpType()) {
                case RED_SHOE -> {
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.RED_SHOE)) {
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.RED_SHOE, true);
                        model.getLevel().getMainCharacter().setSpeed(model.getLevel().getMainCharacter().getSpeed() * 2);
                    }
                }

                case BLUE_CANDY -> {
                    GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.BLUE_RING, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.BLUE_RING) - 1);

                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.BLUE_CANDY))
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.BLUE_CANDY, true);


                }

                case PINK_CANDY -> {
                    GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.PINK_RING, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.PINK_RING) - 1);

                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.PINK_CANDY)) {
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.PINK_CANDY, true);
                        NormalBubble.setHorizontalDistanceSetting(64);
                    }
                }

                case YELLOW_CANDY -> {
                    GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.RED_RING, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.RED_RING) - 1);
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.YELLOW_CANDY))
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.YELLOW_CANDY, true);

                }

                case PINK_RING -> {
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.PINK_RING))
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.PINK_RING, true);
                }

                case RED_RING -> {
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.RED_RING))
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.RED_RING, true);
                }

                case BLUE_RING -> {
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.BLUE_RING))
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.BLUE_RING, true);
                }
                case PINK_UMBRELLA -> {
                    GameController.getInstance().reset();
                    model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth(), model.getLevel().getLevel() + 3, model.getLevel().getMainCharacter().getPowerUps());

                }

                case RED_UMBRELLA -> {
                    GameController.getInstance().reset();
                    model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth(), model.getLevel().getLevel() + 5, model.getLevel().getMainCharacter().getPowerUps());
                }
                case ORANGE_UMBRELLA -> {
                    GameController.getInstance().reset();
                    model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth(), model.getLevel().getLevel() + 7, model.getLevel().getMainCharacter().getPowerUps());
                }

                case CLOCK -> {
                    resetClockCounter();
                    GameController.getInstance().getPowerUpCounter().put(PowerUp.Type.YELLOW_LANTERN, GameController.getInstance().getPowerUpCounter().get(PowerUp.Type.YELLOW_LANTERN) - 1);
                }
                case YELLOW_LANTERN -> {
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.YELLOW_CANDY))
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.YELLOW_CANDY, true);
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.PINK_CANDY)) {
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.PINK_CANDY, true);
                        NormalBubble.setHorizontalDistanceSetting(64);
                    }
                    if (!model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.BLUE_CANDY))
                        model.getLevel().getMainCharacter().getPowerUps().put(PowerUp.Type.BLUE_CANDY, true);

                }

            }
        }
    }

    /**
     * Check if main character hit a powerUp
     *
     * @param x mainCharacter x coordinate
     * @param y mainCharacter y coordinate
     * @return if main character hit a powerUp
     */
    private boolean hitFromPowerUps(int x, int y) {
        return model.getLevel().getPowerUps().stream()
                .anyMatch(powerUp -> {
                            boolean hit = false;
                            for (int i = 0; i < powerUp.getHeight(); i++) {
                                hit = x + i >= powerUp.getX() && x + i < powerUp.getX() + 32 && y >= powerUp.getY() && y < powerUp.getY() + 32;
                                hit |= x + i >= powerUp.getX() && x + i < powerUp.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= powerUp.getY() && y + model.getLevel().getMainCharacter().getHeight() < powerUp.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }

    /**
     * Get the powerUp that has been hit from main character
     *
     * @param x main character x coordinate
     * @param y main character y coordinate
     * @return the powerUp that has been hit from main character
     */
    private PowerUp getHitFromPowerUps(int x, int y) {
        return model.getLevel().getPowerUps().parallelStream()
                .filter(powerUp -> {
                            boolean hit = false;
                            for (int i = 0; i < powerUp.getHeight(); i++) {
                                hit = x + i >= powerUp.getX() && x + i < powerUp.getX() + 32 && y >= powerUp.getY() && y < powerUp.getY() + 32;
                                hit |= x + i >= powerUp.getX() && x + i < powerUp.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= powerUp.getY() && y + model.getLevel().getMainCharacter().getHeight() < powerUp.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }

    /**
     * Check if powerUp has to fall down or not by checking if it is on a brick or not
     *
     * @param powerUp powerUp to make controls
     * @return if powerUp has to fall down or not
     */
    public boolean powerUpFall(PowerUp powerUp) {
        boolean isFall;
        int x = powerUp.getX();
        int y = powerUp.getY();
        isFall = model.getLevel().getBricks().stream().
                filter(brick -> brick.getY() >= y && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 > brick.getX() && x + 32 < brick.getX() + 16))).
                anyMatch(brick -> brick.getY() == y + PowerUp.HEIGHT || brick.getY() + 1 == y + PowerUp.HEIGHT);

        return !isFall;
    }

    /**
     * Check for every power up if it has to fall down using <code>powerUpFall</code> method
     * Handle powerUp fall by making its y position decrease
     */
    public void powerUpsFall() {
        for (PowerUp powerUp : GameController.getInstance().getAddPowerUps())
            model.getLevel().getPowerUps().add(powerUp);
        GameController.getInstance().getAddPowerUps().clear();
        for (PowerUp powerUp : GameController.getInstance().getRemovePowerUps())
            model.getLevel().getPowerUps().remove(powerUp);
        GameController.getInstance().getRemovePowerUps().clear();
        CopyOnWriteArrayList<PowerUp> powerUps = new CopyOnWriteArrayList<>(model.getLevel().getPowerUps());
        for (PowerUp powerUp : powerUps) {

            if (powerUpFall(powerUp))
                powerUp.fall();
        }
    }

    /**
     * Get the loot that has been hit from main character
     *
     * @param x main character x coordinate
     * @param y main character y coordinate
     * @return the loot that has been hit from main character
     */
    public Loot getLootsHit(int x, int y) {
        return model.getLevel().getLoots().parallelStream()
                .filter(loot -> {
                            boolean hit = false;
                            for (int i = 0; i < loot.getHeight(); i++) {
                                hit = x + i >= loot.getX() && x + i < loot.getX() + 32 && y >= loot.getY() && y < loot.getY() + 32;
                                hit |= x + i >= loot.getX() && x + i < loot.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= loot.getY() && y + model.getLevel().getMainCharacter().getHeight() < loot.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }

    /**
     * Check if main character has hit a loot
     *
     * @param x main character x coordinate
     * @param y main character y coordinate
     * @return if main character has hit a loot
     */
    public boolean hitFromLoots(int x, int y) {
        return model.getLevel().getLoots().stream()
                .anyMatch(loot -> {
                            boolean hit = false;
                            for (int i = 0; i < loot.getHeight(); i++) {
                                hit = x + i >= loot.getX() && x + i < loot.getX() + loot.getWidth() && y >= loot.getY() && y < loot.getY() + loot.getWidth();
                                hit |= x + i >= loot.getX() && x + i < loot.getX() + loot.getHeight() && y + model.getLevel().getMainCharacter().getHeight() >= loot.getY() && y + model.getLevel().getMainCharacter().getHeight() < loot.getY() + loot.getHeight();
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }

    /**
     * Check if a loot has been hit using <code>hitFromLoots</code> method
     * If there is a hit get loot object using <code>getLootsHit</code> method and dispatch loot effect (increase game score)
     */
    public void dispatchLootsEffect() {
        for (Loot loot : GameController.getInstance().getAddLoots())
            model.getLevel().getLoots().add(loot);
        GameController.getInstance().getAddLoots().clear();
        for (Loot loot : GameController.getInstance().getRemoveLoots())
            model.getLevel().getLoots().remove(loot);
        GameController.getInstance().getRemoveLoots().clear();

        if (hitFromLoots(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {
            AudioManager.getInstance().play(FileManager.getResource("audio", "collect.wav"));
            Loot loot = getLootsHit(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY());
            GameController.getInstance().getRemoveLoots().add(loot);
            GameController.getInstance().increaseScore(loot.getScore());
        }
    }

}
