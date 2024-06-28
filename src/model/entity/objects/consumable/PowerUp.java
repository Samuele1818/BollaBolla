package model.entity.objects.consumable;

public class PowerUp extends Consumable{




    private PowerUpType powerUpType;

    public static final int WIDTH=32;
    public static final int HEIGHT=16;

    public PowerUp(int x, int y, PowerUpType powerUpType) {
        super(x, y,WIDTH ,HEIGHT , Type.POWER_UP, powerUpType.getName(), powerUpType.getScore(),0);
        this.powerUpType = powerUpType;
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    public void setPowerUpType(PowerUpType powerUpType) {
        this.powerUpType = powerUpType;
    }

    public enum PowerUpType {


        YELLOW_CANDY("yellow_candy.png",100),  //implementata
        BLUE_CANDY("blue_candy.png",100),
        PINK_CANDY("pink_candy.png",100),      //implementata
        BLU_UMBRELLA("blu_umbrella.png",2000),
        PINK_UMBRELLA("pink_umbrella.png",2000),
        RED_SHOE("red_shoe.png",100),     //implementata
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
            this.score=score;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }
    }


}
