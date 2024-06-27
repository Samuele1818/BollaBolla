package model.entity.objects.consumable;

public class PowerUp extends Consumable{




    public PowerUp(int x, int y, PowerUpType skinPath) {
        super(x, y, WIDTH, HEIGHT, Type.POWER_UP, skinPath.getName());
    }

    public enum PowerUpType {

        BLUE_CANDY("blue_candy.png",1000),        // saltare 35 volte img messa
        PURPLE_CANDY("purple_candy.png",1000),  //scoppiare 35 bolle img messa
        BLU_UMBRELLA("blu_umbrella.png",2000),
        PINK_UMBRELLA("pink_umbrella.png",2000),   //img messa
        RED_SHOE("red_shoe.png",3000),     //img messa
        BLU_RING("blu_ring.png",3000),
        PURPLE_RING("purple_ring.png",4000),
        RED_RING("red_ring.png",4000),
        BLU_LANTERN("blu_lantern.png",5000),
        YELLOW_LANTERN("yellow_lantern.png",5000),
        CLOCK("clock.png",6000),
        BLUE_CROSS("blue_cross.png",6000);


        int score;
        String name;
        PowerUpType(String name,int score){
            this.name=name;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }
    }


}
