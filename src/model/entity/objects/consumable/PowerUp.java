package model.entity.objects.consumable;

public class PowerUp extends Consumable{




    private PowerUpType powerUpType;

    public static final int WIDTH=24;
    public static final int HEIGHT=24;

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


        YELLOW_CANDY("yellow_candy.png",100),  //saltare
        BLUE_CANDY("blue_candy.png",100),      //scoppia bolle
        PINK_CANDY("pink_candy.png",100),      //lancia bolle
        BLU_UMBRELLA("blu_umbrella.png",2000),
        PINK_UMBRELLA("pink_umbrella.png",2000),
        RED_UMBRELLA("red_umbrella.png",2000),
        RED_SHOE("red_shoe.png",100),     //cammina
        BLUE_RING("blu_ring.png",3000),    //mangia caramelle blue
        PINK_RING("pink_ring.png",4000),   //mangia caramello rosa
        RED_RING("red_ring.png",4000),    //mangia caramelle gialle
        YELLOW_LANTERN("yellow_lantern.png",5000),
        CLOCK("clock.png",6000);


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
