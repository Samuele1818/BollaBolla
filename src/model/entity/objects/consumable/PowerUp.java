package model.entity.objects.consumable;

import model.behaviour.Fall;
import model.entity.Entity;

public class PowerUp extends Consumable implements Fall {
    public static final int WIDTH = 24;
    public static final int HEIGHT = 24;
    private Type type;
    /**
     * PowerUp constructor
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param type name of the powerUp
     */
    public PowerUp(int x, int y, Type type) {
        super(x, y, WIDTH, HEIGHT, Entity.Type.POWER_UP, type.getFilename(), type.getScore());
        this.type = type;
    }

    /**
     * Get powerUp type
     *
     * @return powerUp type
     */
    public Type getPowerUpType() {
        return type;
    }

    /**
     * PowerUp fall behaviour
     */
    @Override
    public void fall() {
        setY(getY() + 1);
    }

    /**
     * PowerUp type with icon filename and sore associated
     */
    public enum Type {
        YELLOW_CANDY("yellow_candy.png", 100),
        BLUE_CANDY("blue_candy.png", 100),
        PINK_CANDY("pink_candy.png", 100),
        RED_UMBRELLA("red_umbrella.png", 300),
        PINK_UMBRELLA("pink_umbrella.png", 300),
        ORANGE_UMBRELLA("orange_umbrella.png", 300),
        RED_SHOE("red_shoe.png", 100),
        BLUE_RING("blue_ring.png", 100),
        PINK_RING("pink_ring.png", 100),
        RED_RING("red_ring.png", 100),
        YELLOW_LANTERN("yellow_lantern.png", 5000),
        CLOCK("clock.png", 6000);

        private final int SCORE;
        private final String FILENAME;

        /**
         * Type constructor
         *
         * @param filename powerUp icon filename
         * @param score    score that powerup gives when player collect it
         */
        Type(String filename, int score) {
            this.FILENAME = filename;
            this.SCORE = score;
        }

        /**
         * Get score that loot gives when player collect it
         *
         * @return score that powerUp gives when player collect it
         */
        public int getScore() {
            return SCORE;
        }

        /**
         * Get powerUp filename
         *
         * @return icon filename
         */
        public String getFilename() {
            return FILENAME;
        }
    }
}
