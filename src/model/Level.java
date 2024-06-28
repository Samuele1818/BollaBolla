package model;

import model.entity.Monster;
import model.entity.monster.Character;
import model.entity.monster.enemies.Enemie;
import model.entity.monster.enemies.Pulpul;
import model.entity.monster.enemies.ZenChan;
import model.entity.objects.Brick;
import model.entity.objects.bubble.Bubble;
import model.entity.objects.consumable.Consumable;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import model.files.FileManager;
import model.play.Player;
import model.utils.GenerateLevel;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Observable;

public class Level extends Observable implements Serializable {

    public static final int COLUMNS = 32;
    public static final int ROWS = 29;
    public static final int PADDING = 2;
    @Serial
    private final static long serialVersionUID = 6920834105100098501L;
    public static final String LEVEL_FOLDER = "level";
    private Character mainCharacter;
    // Bricks contained by the map
    private ArrayList<Brick> bricks;
    // Enemies present in the map
    private ArrayList<Monster> enemies;
    transient private ArrayList<Bubble> bubbles;
    transient private ArrayList<Monster> killedEnemies;
    // Items present in the ma
    private ArrayList<Consumable> consumables;
    // Bricks image
    private String bricksImage;
    // Level number to load the map
    private int level;
    private int score;
    transient ArrayList<Loot> loots;
    transient ArrayList<PowerUp> powerUps;

    transient private GenerateLevel generateLevel;

    public Level(String bricksImage, Character.Type mainCharacter) {
        bricks = new ArrayList<>();
        this.bricksImage = bricksImage;
        level = 0;
        this.mainCharacter = new Character(mainCharacter);
        this.killedEnemies = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.bubbles = new ArrayList<>();
        this.loots = new ArrayList<>();
        powerUps = new ArrayList<>();
        this.score = 0;

        generateLevel = new GenerateLevel(this);

        init();
//        resetLevel(3, 1);
    }

    public void init() {

        this.killedEnemies = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.bubbles = new ArrayList<>();
        this.loots = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        FileManager.createDirectory(LEVEL_FOLDER);

        for (int j = PADDING; j < COLUMNS - PADDING; j++) {

            if (j * Brick.WIDTH <= 13 * Brick.HEIGHT || j * Brick.WIDTH > 17 * Brick.HEIGHT)
                bricks.add(new Brick(j * Brick.WIDTH, Brick.HEIGHT * PADDING));
            bricks.add(new Brick(j * Brick.WIDTH, (ROWS - PADDING - 1) * Brick.HEIGHT));

        }

        for (int i = PADDING; i < ROWS - PADDING; i++) {
            bricks.add(new Brick(Brick.WIDTH * PADDING, i * Brick.HEIGHT));
            bricks.add(new Brick((COLUMNS - PADDING - 1) * Brick.WIDTH, i * Brick.HEIGHT));

        }

        //System.out.println(bricks.size());

        enemies.add(new Pulpul((COLUMNS - PADDING - 6) * Brick.WIDTH, 200));
        enemies.add(new ZenChan((COLUMNS - PADDING - 5) * Brick.WIDTH, 200));
        String fileName = String.valueOf(Path.of(LEVEL_FOLDER, "1"));


        FileManager.serialize(this, fileName);


        spawnEntity(new Character(Player.getInstance().getMainCharacter()));

    }

    public void resetLevel(int health, int level) {
        // Reset arrays
        this.killedEnemies = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.consumables = new ArrayList<>();
        this.bubbles = new ArrayList<>();
        this.loots = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        
        this.level = level;

        //--------------------
        
        if(!FileManager.checkExists(String.valueOf(Path.of(LEVEL_FOLDER, String.valueOf(this.level))))) {
            new GenerateLevel(this).regenerateLevel(this.level);
        }

        // Load level based on parameter level
        String fileName = LEVEL_FOLDER + File.separator + this.level;
        Level level1 = FileManager.deserialize(fileName);

        // Init
        this.mainCharacter = level1.mainCharacter;
        this.bricks = level1.bricks;

        this.bricksImage = level1.bricksImage;
        this.enemies = level1.enemies;
        this.consumables = level1.consumables;

        // Spawn character
        spawnEntity(new Character(Player.getInstance().getMainCharacter()));
        this.getMainCharacter().setHealth(health);

        // Notify
        setChanged();
        notifyObservers(this);
    }

    public Character getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(Character mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public ArrayList<Monster> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Monster> enemies) {
        this.enemies = enemies;
    }

    public ArrayList<Consumable> getConsumables() {
        return consumables;
    }

    public void setConsumables(ArrayList<Consumable> consumables) {
        this.consumables = consumables;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public String getBricksImage() {
        return bricksImage;
    }

    public void setBricksImage(String bricksImage) {
        this.bricksImage = bricksImage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Bubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(ArrayList<Bubble> bubbles) {
        this.bubbles = bubbles;
    }

    public void spawnEntity(Entity entity) {
        switch (entity.getClass().getSimpleName()) {
            case "Enemies" -> enemies.add((Enemie) entity);
            case "Consumable" -> consumables.add((Consumable) entity);
            case "Character" -> mainCharacter = (Character) entity;
            case "NormalBubble" -> bubbles.add((Bubble) entity);

            //default -> throw new IllegalStateException("Unexpected value: " + entity.getClass().getSimpleName());
        }
    }

    public void notifica() {
        setChanged();
        notifyObservers(this);
    }

    public ArrayList<Monster> getKilledEnemies() {
        return killedEnemies;
    }

    public void setKilledEnemies(ArrayList<Monster> killedEnemies) {
        this.killedEnemies = killedEnemies;
    }


    public ArrayList<Loot> getLoots() {
        return loots;
    }

    public void setLoots(ArrayList<Loot> loots) {
        this.loots = loots;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public enum Direction {
        LEFT("walk_left.gif"), RIGHT("walk_right.gif"),
        FIRE_RIGHT("fire_right.png"), FIRE_LEFT("fire_left.png"),
        ZENCHAN_BUBBLE("zenchan.gif"), INVADER_BUBBLE("invader.gif"),
        MONSTA_BUBBLE("monsta.gif"), PULPUL_BUBBLE("pulpul.gif"),
        DRUNK_BUBBLE("drunk.gif"), MIGHTA_BUBBLE("bobbub.gif"),
        BUBBLE("bubble.png"),BUBBLE_END("bubbleend.png"),
        DEAD_LEFT("dead_left.gif"), DEAD_RIGHT("dead_right.gif");
        private final String imagesMovements;

        Direction(String folder) {
            this.imagesMovements = folder;
        }

        public String getImagesMovements() {
            return imagesMovements;
        }
    }
}

