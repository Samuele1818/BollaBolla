package model.entity;

import model.Level;
import model.utils.FileManager;

import java.io.Serial;
import java.io.Serializable;

public abstract class Entity implements Serializable {
    @Serial
    private final static long serialVersionUID = 5081307589196485457L;
    // Dimension of entity
    private final int WIDTH, HEIGHT;
    // Coordinated
    private int x, y;
    private Level.Animation currentAnimation;
    // folder that contains the animations and the specific animation path
    private String imagesFolder, skinPath;

    /**
     * Entity constructor
     *
     * @param x        current x position
     * @param y        current y position
     * @param width    entity width
     * @param height   entity height
     * @param type     entity type
     * @param skinPath specific entity animation
     */
    public Entity(int x, int y, int width, int height, Type type, String skinPath) {
        this(x, y, width, height, type, skinPath, Level.Animation.RIGHT);

        changeType(type);
    }


    /**
     * Entity constructor
     *
     * @param x                current x position
     * @param y                current y position
     * @param width            entity width
     * @param height           entity height
     * @param type             entity type
     * @param skinPath         entity width
     * @param currentAnimation current entity animation
     */
    public Entity(int x, int y, int width, int height, Type type, String skinPath, Level.Animation currentAnimation) {
        this.x = x;
        this.y = y;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.skinPath = skinPath;
        this.currentAnimation = currentAnimation;

        changeType(type);
    }

    /**
     * Get x position of the entity
     *
     * @return x position of the entity
     */
    public int getX() {
        return x;
    }

    /**
     * Set x position of the entity
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get y position of the entity
     *
     * @return y position of the entity
     */
    public int getY() {
        return y;
    }

    /**
     * Set y position of the entity
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get width of the entity
     *
     * @return width of the entity
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Get height of the entity
     *
     * @return height of the entity
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Change entity animation
     *
     * @param animation new animation of the entity
     */
    public void changePath(Level.Animation animation) {
        skinPath = animation.getAnimationFileName();
    }

    /**
     * Get folder that contains images of current entity
     *
     * @return path to images folder of the current entity
     */
    public String getImagesFolder() {
        return imagesFolder;
    }

    /**
     * Chane type of the entity
     *
     * @param type new type of the entity
     */
    public void changeType(Type type) {
        this.imagesFolder = type.getImagesFolder();
    }

    /**
     * Get current animation of the entity
     *
     * @return current entity animation
     */
    public Level.Animation getCurrentAnimation() {
        return currentAnimation;
    }

    /**
     * Set current animation of the entity
     *
     * @param currentAnimation new entity animation
     */
    public void setCurrentAnimation(Level.Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    /**
     * Get current path of the current animation image
     *
     * @return path of the current animation image
     */
    public String getCharacterPath() {
        return skinPath;
    }

    /**
     * Entity type with images folder associated
     */
    public enum Type implements Serializable {
        BOB(FileManager.getResource("animation", "bob")),
        BUB(FileManager.getResource("animation", "bub")),
        ZENCHAN(FileManager.getResource("animation", "zenchan")),
        INVADER(FileManager.getResource("animation", "invader")),
        MONSTA(FileManager.getResource("animation", "monsta")),
        MIGHTA(FileManager.getResource("animation", "mighta")),
        PULPUL(FileManager.getResource("animation", "pulpul")),
        DRUNK(FileManager.getResource("animation", "drunk")),
        NORMAL_BUBBLE(FileManager.getResource("animation", "normal_bubble")),
        EFFECT_BUBBLE(FileManager.getResource("animation", "effect_bubble")),
        LOOT(FileManager.getResource("animation", "loot")),
        POWER_UP(FileManager.getResource("animation", "power_up")),
        BRICK("");

        @Serial
        private static final long serialVersionUID = 4988872341489519317L;
        private final String IMAGES_FOLDER;

        /**
         * Type constructor
         *
         * @param folder path to folder
         */
        Type(String folder) {
            this.IMAGES_FOLDER = folder;
        }

        /**
         * Get images folder
         *
         * @return images folder
         */
        public String getImagesFolder() {
            return IMAGES_FOLDER;
        }
    }
}
