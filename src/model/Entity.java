package model;

import model.entity.monster.Character;
import model.files.FileManager;

import java.io.Serial;
import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected final static int WEIGHT = 3;
    @Serial
    private final static long serialVersionUID = 5081307589196485457L;
    private int x;
    private int y;
    private int width;
    private int height;
    private Level.Direction currentDirection;
    private String imagesFolder;
    private String skinPath;

    public Entity(int x, int y, int width, int height, Type type, String skinPath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.skinPath = skinPath;
        currentDirection = Level.Direction.RIGHT;
        changeCharacterType(type);
    }

    public Entity(int x, int y, int width, int height, Type type, String skinPath,Level.Direction currentDirection) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.skinPath = skinPath;
        this.currentDirection = currentDirection;
        changeCharacterType(type);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void changeCharacterPath(Level.Direction direction) {
        skinPath = direction.getImagesMovements();
    }

    public String getImagesFolder() {
        return imagesFolder;
    }

    public void setImagesFolder(String imagesFolder) {
        this.imagesFolder = imagesFolder;
    }

    public void changeCharacterType(Character.Type type) {
        this.imagesFolder = type.getImageFolder();
    }

    public String getSkinPath() {
        return skinPath;
    }

    public void setSkinPath(String skinPath) {
        this.skinPath = skinPath;
    }

    public Level.Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Level.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public String getCharacterPath() {
        return skinPath;
    }





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
        LOOT(FileManager.getResource("animation", "loot")),
        POWER_UP(FileManager.getResource("animation", "power_up")),
        BRICK("");

        @Serial
        private static final long serialVersionUID = 4988872341489519317L;
        private final String imagesFolder;

        Type(String folder) {
            this.imagesFolder = folder;
        }

        public String getImageFolder() {
            return imagesFolder;
        }


    }
}
