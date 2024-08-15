package model.entity.objects.consumable;

import model.entity.Entity;

public class Loot extends Consumable {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    /**
     * Loot constructor
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param type name of the loot
     */
    public Loot(int x, int y, Type type) {
        super(x, y, WIDTH, HEIGHT, Entity.Type.LOOT, type.getFilename(), type.getScore());
    }

    /**
     * Loot type with icon filename and sore associated
     */
    public enum Type {
        APPLE("apple.png", 100),
        BANANA("bananas.png", 200),
        CHERRIES("cherries.png", 300),
        GRAPE("grape.png", 400),
        ICE_CREAM_CUP("ice_cream_cup.png", 500),
        LEMON("lemon.png", 600),
        ORANGE("orange.png", 700),
        PEACH("peach.png", 800),
        PEAR("pear.png", 900);

        private final String FILENAME;
        private final int SCORE;

        /**
         * Type constructor
         *
         * @param filename loot icon filename
         * @param score    score that loot gives when player collect it
         */
        Type(String filename, int score) {
            this.FILENAME = filename;
            this.SCORE = score;
        }

        /**
         * Get loot filename
         *
         * @return icon filename
         */
        public String getFilename() {
            return FILENAME;
        }

        /**
         * Get score that loot gives when player collect it
         *
         * @return score that loot gives when player collect it
         */
        public int getScore() {
            return SCORE;
        }
    }
}
