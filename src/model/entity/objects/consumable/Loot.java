package model.entity.objects.consumable;

public class Loot extends Consumable {

    private int score;


    public Loot(int x, int y, NameLoot name) {
        super(x, y, WIDTH, HEIGHT, Type.LOOT, name.getName());
        score = name.getScore();
    }

    public int getScore() {
        return score;
    }

    public enum NameLoot {
        APPLE("apple.png", 100),
        BANANA("bananas.png", 200),
        CHERRIES("cherries.png", 300),
        GRAPE("grape.png", 400),
        ICE_CREAM_CUP("ice_cream_cup.png", 500),
        LEMON("lemon.png", 600),
        ORANGE("orange.png", 700),
        PEACH("peach.png", 800),
        PEAR("pear.png", 900);


        private final String name;
        private final int score;

        NameLoot(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}
