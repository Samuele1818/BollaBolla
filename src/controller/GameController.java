package controller;

import controller.game.BubbleHelper;
import controller.game.CollectablesHelper;
import controller.game.EnemiesHelper;
import controller.game.MainCharacterHelper;
import model.Level;
import model.Model;
import model.entity.monster.Character;
import model.entity.monster.enemies.Enemy;
import model.entity.objects.Brick;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.EffectBubble;
import model.entity.objects.consumable.bubble.NormalBubble;
import model.play.register.GameRecord;
import model.utils.AudioManager;
import model.utils.FileManager;
import view.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class GameController implements KeyListener {
    private static final int FRAME_RATE = 16;

    private static GameController instance = null;
    // Helpers with methods used to handle the game cycle
    private EnemiesHelper enemiesController;
    private BubbleHelper bubbleController;
    private MainCharacterHelper mainCharacterController;
    private CollectablesHelper collectablesController;
    private HashMap<PowerUp.Type, Integer> powerUpCounter;
    private Model model;
    private View view;
    // Main character current speed
    private int currentSpeed;
    private int clockCounter;
    // If main character is firing
    private boolean isFiring;
    //gameLoop break while changing level
    private boolean isLevelChange;
    // Player can fire now
    private boolean bubbleDelay;
    // Game loop flags
    private boolean isLevelEnded;
    private boolean isDead;
    private boolean isPause;
    private boolean isGameEnded;
    private ArrayList<NormalBubble> addBubbles;
    private ArrayList<NormalBubble> removeBubbles;
    private ArrayList<Enemy> removeEnemies;
    private ArrayList<Enemy> addEnemies;
    private ArrayList<Enemy> addKilledEnemies;
    private ArrayList<Enemy> removeKilledEnemies;
    private ArrayList<Loot> removeLoots;
    private ArrayList<Loot> addLoots;
    private ArrayList<PowerUp> removePowerUps;
    private ArrayList<PowerUp> addPowerUps;
    private ArrayList<EffectBubble> removeEffectBubbles;
    private ArrayList<EffectBubble> addEffectBubbles;

    /**
     * GameController constructor
     * Init Model and View
     * Init every array list / map
     * Init helper instances
     * Put default <code>powerUpsCounter</code> values
     */
    private GameController() {
        enemiesController = EnemiesHelper.getInstance();
        bubbleController = BubbleHelper.getInstance();
        mainCharacterController = MainCharacterHelper.getInstance();
        collectablesController = CollectablesHelper.getInstance();

        clockCounter = 0;
        isLevelEnded = false;
        isPause = false;
        isGameEnded = false;

        reset();

        powerUpCounter = new HashMap<>();
        powerUpCounter.put(PowerUp.Type.BLUE_CANDY, 6);
        powerUpCounter.put(PowerUp.Type.PINK_CANDY, 6);
        powerUpCounter.put(PowerUp.Type.RED_SHOE, 1000);
        powerUpCounter.put(PowerUp.Type.YELLOW_CANDY, 6);
        powerUpCounter.put(PowerUp.Type.PINK_RING, 3);
        powerUpCounter.put(PowerUp.Type.RED_RING, 3);
        powerUpCounter.put(PowerUp.Type.BLUE_RING, 3);
        powerUpCounter.put(PowerUp.Type.RED_UMBRELLA, 5);
        powerUpCounter.put(PowerUp.Type.PINK_UMBRELLA, 5);
        powerUpCounter.put(PowerUp.Type.ORANGE_UMBRELLA, 5);
        powerUpCounter.put(PowerUp.Type.CLOCK, 2);
        powerUpCounter.put(PowerUp.Type.YELLOW_LANTERN, 2);
    }

    /**
     * Get GameController instance
     *
     * @return GameController instance
     */
    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    /**
     * Init View and Model
     * Add key listener to MapPanel
     */
    public void init() {
        model = Model.getInstance();
        view = View.getInstance();

        view.getMapPanel().addKeyListener(this);
    }

    /**
     * Reset all array list of GameController
     * and every field that regard the main character
     */
    public void reset() {
        currentSpeed = 0;
        isFiring = false;
        bubbleDelay = false;
        isDead = false;

        addEffectBubbles = new ArrayList<>();
        removeEffectBubbles = new ArrayList<>();
        addBubbles = new ArrayList<>();
        removeBubbles = new ArrayList<>();

        removeEnemies = new ArrayList<>();
        addEnemies = new ArrayList<>();
        addKilledEnemies = new ArrayList<>();
        removeKilledEnemies = new ArrayList<>();

        removeLoots = new ArrayList<>();
        addLoots = new ArrayList<>();
        removePowerUps = new ArrayList<>();
        addPowerUps = new ArrayList<>();

        bubbleController.generateEffectBubble();
    }

    /**
     * Game loop cycle, stop when player dies or win/lose and add the game played in the game register
     * Can be paused by pressing ESC key
     * It manages the entire game, play and stop the audio clip
     */
    public void gameLoop() {
        reset();
        AudioManager.getInstance().playMenu(FileManager.getResource("audio", "room_theme.wav"));

        while (model.getLevel().getMainCharacter().getHealth() != 0 && model.getLevel().getLevel() <= 16 && !isGameEnded) {
            try {
                Thread.sleep(FRAME_RATE);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isGameEnded) {
                isPause = false;
                isGameEnded = false;
                break;
            }

            if (isPause || isDead || isLevelChange) continue;

            mainCharacterController.fireAnimation();

            collectablesController.spawnPowerUps();
            enemiesController.enemiesKill();
            mainCharacterController.move();

            collectablesController.dispatchEffectPowerUp();
            bubbleController.dispatchEffectBubbleEffect();
            collectablesController.dispatchLootsEffect();
            enemiesController.collisionEnemies();

            enemiesController.killedEnemiesMove();
            bubbleController.moveNormalBubbles();
            bubbleController.moveEffectBubbles();

            enemiesController.enemiesMove();

            collectablesController.powerUpsFall();


            if (!bubbleDelay && isFiring)
                bubbleController.bubbleFireDelay();

            checkLevelEnded();

            model.getLevel().notifyLevelChanged();
        }

        AudioManager.getInstance().stop();
        if (model.getLevel().getMainCharacter().getHealth() == 0) {
            model.getLevel().getMainCharacter().setHealth(Character.HEALTH);
            view.changePanel(View.Screen.LOSE);
            reset();
            collectablesController.resetPowerUp();
            model.getPlayer().insertGame(
                    new GameRecord(model.getPlayer().getMainCharacter(), false, model.getLevel().getScore(), model.getLevel().getLevel(), Calendar.getInstance().getTime())
            );
        }

        if (model.getLevel().getLevel() > 16) {
            view.changePanel(View.Screen.WIN);
            reset();
            collectablesController.resetPowerUp();
            model.getPlayer().insertGame(
                    new GameRecord(model.getPlayer().getMainCharacter(), true, model.getLevel().getScore(), model.getLevel().getLevel(), Calendar.getInstance().getTime())
            );

        }
        model.getLevel().setScore(0);
    }

    /**
     * Check if entity is in a valid position based on left/right direction
     *
     * @param animation entity is moving left or right
     * @param x         entity x coordinate
     * @param y         entity y coordinate
     * @return if entity is in a valid position
     */
    public boolean isValidPositionBrick(Level.Animation animation, int x, int y) {
        return switch (animation) {
            case LEFT -> model.getLevel().getBricks().
                    stream().filter(brick -> brick.getX() <= x && ((brick.getY() + 16 > y && brick.getY() <= y) || (brick.getY() + 16 > y + 16 && brick.getY() <= y + 16))).
                    allMatch(brick -> brick.getX() + Brick.WIDTH <= x);
            case RIGHT -> model.getLevel().getBricks().
                    stream().filter(brick -> brick.getX() >= x && ((brick.getY() + 16 > y && brick.getY() <= y) || (brick.getY() + 16 > y + 16 && brick.getY() <= y + 16))).
                    allMatch(brick -> brick.getX() >= x + Character.WIDTH);
            default -> false;
        };
    }

    /**
     * Increase game score
     *
     * @param score score to add to the current score
     */
    public void increaseScore(int score) {
        model.getLevel().setScore(model.getLevel().getScore() + score);
    }

    /**
     * Check if level is ended
     * If true, wait 5 seconds and then resetLevel and go to the next
     */
    private void checkLevelEnded() {
        boolean valid = model.getLevel().getBubbles().stream().noneMatch(NormalBubble::isContainsEnemy);

        if (!isDead && !isLevelEnded && model.getLevel().getEnemies().isEmpty() && model.getLevel().getKilledEnemies().isEmpty() && valid) {
            isLevelEnded = true;
            Thread a = new Thread(() -> {
                try {
                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                isLevelChange = true;
                reset();
                model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth(), model.getLevel().getLevel() + 1, model.getLevel().getMainCharacter().getPowerUps());
                isLevelEnded = false;
                isLevelChange = false;
            });

            a.start();
        }
    }

    /**
     * Handle main character:
     * Enter -> Fire a bubble
     * A -> Stop left movement
     * D -> Stop right movement
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (isDead)
            return;

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_A -> {
                currentSpeed = 0;
                model.getLevel().getMainCharacter().setCurrentAnimation(Level.Animation.LEFT);
            }
            case KeyEvent.VK_D -> {
                currentSpeed = 0;
                model.getLevel().getMainCharacter().setCurrentAnimation(Level.Animation.RIGHT);
            }

            case KeyEvent.VK_ENTER -> {
                if (!isFiring) {
                    isFiring = true;
                    NormalBubble bubble = model.getLevel().getMainCharacter().fire();
                    if (!(bubble.getHorizontalDistanceSetting() != 0 && !bubbleController.isValidBubblePosition(bubble))) {
                        addBubbles.add(bubble);
                        powerUpCounter.put(PowerUp.Type.PINK_CANDY, powerUpCounter.get(PowerUp.Type.PINK_CANDY) - 1);
                        AudioManager.getInstance().play(FileManager.getResource("audio", "bubble.wav"));
                    }
                }
            }
        }
    }

    /**
     * Handle main character:
     * A -> move left
     * D -> move right
     * space -> jump
     * Handle pause:
     * escape -> pause the game
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (isDead) {
            switch (model.getLevel().getMainCharacter().getCurrentAnimation()) {
                case LEFT:
                    model.getLevel().getMainCharacter().changePath(Level.Animation.DEAD_LEFT);
                    break;
                case RIGHT:
                    model.getLevel().getMainCharacter().changePath(Level.Animation.DEAD_RIGHT);
            }
            return;
        }
        int keyCode = e.getKeyCode();


        switch (keyCode) {
            case KeyEvent.VK_A -> {
                model.getLevel().getMainCharacter().setCurrentAnimation(Level.Animation.LEFT);
                currentSpeed = -2;
            }

            case KeyEvent.VK_D -> {
                model.getLevel().getMainCharacter().setCurrentAnimation(Level.Animation.RIGHT);
                currentSpeed = 2;
            }

            case KeyEvent.VK_SPACE -> {
                if (model.getLevel().getMainCharacter().canJump()) {
                    model.getLevel().getMainCharacter().setJumping(true);
                    model.getLevel().getMainCharacter().setCanJump(false);
                    powerUpCounter.put(PowerUp.Type.YELLOW_CANDY, powerUpCounter.get(PowerUp.Type.YELLOW_CANDY) - 1);
                    if (model.getLevel().getMainCharacter().getPowerUps().get(PowerUp.Type.PINK_RING))
                        increaseScore(100);

                    AudioManager.getInstance().play(FileManager.getResource("audio", "jump.wav"));

                }
            }

            case KeyEvent.VK_ESCAPE -> {
                view.changePanel(View.Screen.PAUSE);
                if (!isPause)
                    isPause = true;
            }
        }
    }

    /**
     * Do nothing
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Get character current speed
     * @return character current speed
     */
    public int getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Set character current speed
     * @param currentSpeed character new speed
     */
    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * Get clock counter value
     * @return clock counter value
     */
    public int getClockCounter() {
        return clockCounter;
    }

    /**
     * Set clock counter value
     * @param clockCounter new counter value
     */
    public void setClockCounter(int clockCounter) {
        this.clockCounter = clockCounter;
    }

    /**
     * Check if character is firing
     * @return if character is firing
     */
    public boolean isFiring() {
        return isFiring;
    }

    /**
     * Set if is character firing
     * @param firing if is character firing
     */
    public void setFiring(boolean firing) {
        isFiring = firing;
    }

    /**
     * Set if character can fire
     * @param bubbleDelay character can fire
     */
    public void setBubbleDelay(boolean bubbleDelay) {
        this.bubbleDelay = bubbleDelay;
    }

    /**
     * Check if character is dead
     * @return character is dead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Set if character is dead
     * @param dead if character is dead
     */
    public void setDead(boolean dead) {
        isDead = dead;
    }

    /**
     * Set if game is paused
     * @param pause if game is paused
     */
    public void setPause(boolean pause) {
        isPause = pause;
    }

    /**
     * Set game ended
     * @param gameEnded is game ended
     */
    public void setGameEnded(boolean gameEnded) {
        isGameEnded = gameEnded;
    }

    /**
     * Get arraylist of normal bubble to add from the main list
     * @return arraylist of normal bubble to add
     */
    public ArrayList<NormalBubble> getAddBubbles() {
        return addBubbles;
    }

    /**
     * Get arraylist of normal bubble to remove from the main list
     * @return arraylist of normal bubble to remove
     */
    public ArrayList<NormalBubble> getRemoveBubbles() {
        return removeBubbles;
    }

    /**
     * Get arraylist of killed enemies
     * @return arraylist of killed enemies
     */
    public ArrayList<Enemy> getAddKilledEnemies() {
        return addKilledEnemies;
    }

    /**
     * Get arraylist of enemies to remove from main list
     * @return arraylist of enemies to remove
     */
    public ArrayList<Enemy> getRemoveKilledEnemies() {
        return removeKilledEnemies;
    }

    /**
     * Get arraylist of loots to remove from main list
     * @return arraylist of loots to remove
     */
    public ArrayList<Loot> getRemoveLoots() {
        return removeLoots;
    }

    /**
     * Get arraylist of loots to add from the main list
     * @return arraylist of loots to add
     */
    public ArrayList<Loot> getAddLoots() {
        return addLoots;
    }

    /**
     * Get arraylist of powerUps to remove from main list
     * @return arraylist of powerUps to remove
     */
    public ArrayList<PowerUp> getRemovePowerUps() {
        return removePowerUps;
    }

    /**
     * Get arraylist of powerUps to add from main list
     * @return arraylist of powerUps to add
     */
    public ArrayList<PowerUp> getAddPowerUps() {
        return addPowerUps;
    }

    /**
     * Get arraylist of effect bubbles to remove from main list
     * @return arraylist of effect bubbles to remove
     */
    public ArrayList<EffectBubble> getRemoveEffectBubbles() {
        return removeEffectBubbles;
    }

    /**
     * Get arraylist of effect bubbles to add from main list
     * @return arraylist of effect bubbles to add
     */
    public ArrayList<EffectBubble> getAddEffectBubbles() {
        return addEffectBubbles;
    }

    /**
     * Get HashMap with powerUps associated with counters to make them spawn
     * @return HashMap with powerUps associated with counters to make them spawn
     */
    public HashMap<PowerUp.Type, Integer> getPowerUpCounter() {
        return powerUpCounter;
    }

    /**
     * Get arraylist of enemies to remove from main list
     * @return arraylist of enemies to add
     */
    public ArrayList<Enemy> getRemoveEnemies() {
        return removeEnemies;
    }

    /**
     * Get arraylist of enemies to add from main list
     * @return arraylist of enemies to add
     */
    public ArrayList<Enemy> getAddEnemies() {
        return addEnemies;
    }
}