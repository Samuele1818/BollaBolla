package model;

import model.entity.monster.Character;
import model.entity.monster.enemies.Enemy;
import model.entity.objects.Brick;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.EffectBubble;
import model.entity.objects.consumable.bubble.NormalBubble;
import model.play.Player;
import model.utils.FileManager;
import model.utils.GenerateLevel;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class Level extends Observable implements Serializable {
    public final static int START_LEVEL = 1;
    public static final int COLUMNS = 32;
    public static final int ROWS = 29;
    public static final int PADDING = 2;
    public static final String LEVEL_FOLDER = "level";
    public static final int LEVELS_NUMBER = 16;
    @Serial
    private final static long serialVersionUID = 6920834105100098501L;
    // Loots present in the map (not stored in the serialized file)
    transient ArrayList<Loot> loots;
    // Power up present in the map (not stored in the serialized file)
    transient ArrayList<PowerUp> powerUps;
    // Special bubbles in the map (lightning, health) (not stored in the serialized file)
    transient ArrayList<EffectBubble> effectBubbles;
    // Bricks contained by the map
    private ArrayList<Brick> bricks;
    // Bubbles (not stored in the serialized file)
    transient private ArrayList<NormalBubble> bubbles;
    // Enemies present in the map
    private ArrayList<Enemy> enemies;
    // Killed enemies (not stored in the serialized file)
    transient private ArrayList<Enemy> killedEnemies;
    private Character mainCharacter;
    // Bricks image
    private String bricksImage;
    // Level number to load the map
    private int level;
    private int score;
    /**
     * Level constructor
     *
     * @param bricksImage   image of the bricks contained in the level
     * @param mainCharacter current character used (Bob or Bub)
     */
    public Level(String bricksImage, Character.Type mainCharacter) {
        this.bricks = new ArrayList<>();

        this.loots = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        this.bubbles = new ArrayList<>();
        this.effectBubbles = new ArrayList<>();

        this.killedEnemies = new ArrayList<>();
        this.enemies = new ArrayList<>();

        this.score = 0;
        this.level = 0;
        this.bricksImage = bricksImage;
        this.mainCharacter = new Character(mainCharacter);
    }

    /**
     * Reset everything and initialize a new level from file (if not present the file will be created using regenerate level)
     *
     * @param health   current health of main character
     * @param level    new level to initialize
     * @param powerUps list of current powerUps
     */
    public void resetLevel(int health, int level, HashMap<PowerUp.Type, Boolean> powerUps) {
        this.level = level;

        // If level number exceed maximum level return
        if (level > LEVELS_NUMBER) return;

        // Reset arrays
        this.bricks = new ArrayList<>();

        this.enemies = new ArrayList<>();
        this.killedEnemies = new ArrayList<>();

        this.loots = new ArrayList<>();
        this.powerUps = new ArrayList<>();

        this.bubbles = new ArrayList<>();
        this.effectBubbles = new ArrayList<>();

        FileManager.createDirectory(LEVEL_FOLDER);

        if (FileManager.checkExists(String.valueOf(Path.of(LEVEL_FOLDER, String.valueOf(this.level)))))
            new GenerateLevel(this).regenerateLevel(this.level);

        // Load level based on parameter level
        String fileName = LEVEL_FOLDER + File.separator + this.level;
        Level loadedLevel = FileManager.deserialize(fileName);

        // Init
        this.bricks = loadedLevel.bricks;
        this.mainCharacter = loadedLevel.mainCharacter;
        // Spawn character
        spawnMainCharacter(new Character(Player.getInstance().getMainCharacter()));
        this.getMainCharacter().setHealth(health);
        this.enemies = loadedLevel.enemies;
        this.bricksImage = loadedLevel.bricksImage;


        if (powerUps != null) this.getMainCharacter().setPowerUps(powerUps);

        // Notify observers
        setChanged();
        notifyObservers(this);
    }

    /**
     * Get the main character used in the level
     *
     * @return main character used in the level
     */
    public Character getMainCharacter() {
        return mainCharacter;
    }

    /**
     * Return a list of current bricks
     *
     * @return bricks of the level
     */
    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    /**
     * Add a new brick in the level
     *
     * @param brick Brick to add
     */
    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    /**
     * Return a list of current enemies
     *
     * @return current enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Get current score
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set current score
     *
     * @param score new score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get bricks image used in this level
     *
     * @return image of the bricks
     */
    public String getBricksImage() {
        return bricksImage;
    }

    /**
     * Set the image used by the bricks
     *
     * @param bricksImage path to image to use
     */
    public void setBricksImage(String bricksImage) {
        this.bricksImage = bricksImage;
    }

    /**
     * Get current level number
     *
     * @return level number
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get normal bubble (fired by main character)
     *
     * @return list of normal bubble in the level
     */
    public ArrayList<NormalBubble> getBubbles() {
        return bubbles;
    }

    /**
     * Get list of special bubbles (lightning, health)
     *
     * @return list of special bubbles
     */
    public ArrayList<EffectBubble> getEffectBubbles() {
        return effectBubbles;
    }

    /**
     * Set current main character
     *
     * @param character an instance of the main character
     */
    public void spawnMainCharacter(Character character) {
        this.mainCharacter = character;
    }

    /**
     * Notify observer that data of the level is changed
     */
    public void notifyLevelChanged() {
        setChanged();
        notifyObservers(this);
    }

    /**
     * Get enemies killed by player
     *
     * @return list of enemies killed
     */
    public ArrayList<Enemy> getKilledEnemies() {
        return killedEnemies;
    }

    /**
     * Get loots of the current level
     *
     * @return list of loots contained in the level
     */
    public ArrayList<Loot> getLoots() {
        return loots;
    }

    /**
     * Get powerUps of the current level
     *
     * @return list of powerups contained in the level
     */
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    /**
     * enumeration that manages the animations of the classes that can have animation changes during the game
     */
    public enum Animation {
        LEFT("walk_left.gif"), RIGHT("walk_right.gif"),
        FIRE_RIGHT("fire_right.png"), FIRE_LEFT("fire_left.png"),
        ZENCHAN_BUBBLE("zenchan.gif"), INVADER_BUBBLE("invader.gif"),
        MONSTA_BUBBLE("monsta.gif"), PULPUL_BUBBLE("pulpul.gif"),
        DRUNK_BUBBLE("drunk.gif"), MIGHTA_BUBBLE("mighta.gif"),
        NORMAL_BUBBLE("bubble.png"), BUBBLE_END("bubble_end.png"),
        DEAD_LEFT("dead_left.gif"), DEAD_RIGHT("dead_right.gif");

        private final String animationFilesName;

        /**
         * Animation constructor
         *
         * @param fileName file name of the animation
         */
        Animation(String fileName) {
            this.animationFilesName = fileName;
        }

        /**
         * Get file name of the animation
         *
         * @return animation file name
         */
        public String getAnimationFileName() {
            return animationFilesName;
        }
    }
}