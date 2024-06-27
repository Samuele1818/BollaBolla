package controller;

import model.Level;
import model.Model;
import model.entity.Monster;
import model.entity.monster.Character;
import model.entity.monster.enemies.*;
import model.entity.objects.Brick;
import model.entity.objects.bubble.Bubble;
import model.entity.objects.bubble.NormalBubble;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import view.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GameController implements KeyListener {
    private static final int FRAME_RATE = 16;
    private static GameController instance = null;
    private Model model;
    private View view;
    private int currentSpeed;
    private boolean isJumping;
    private boolean possibilityOfJumping;
    private boolean isFire;
    private boolean delay;
    private ArrayList<Bubble> addBubble;
    private ArrayList<Bubble> removeBubble;
    private ArrayList<Monster> removeMonster;
    private ArrayList<Monster> addMonster;
    private ArrayList<Monster> addKilledMonster;
    private ArrayList<Monster> removeKilledMonster;
    private ArrayList<Loot> removeLoot;
    private ArrayList<Loot> addLoot;
    private ArrayList<PowerUp> removePowerUp;
    private ArrayList<PowerUp> addPowerUp;
    
    
    
    //powerUp Counter
    private int counterBluCandy;
    private int counterPurpleCandy;

    public void resetCounterBluCandy(){
        counterBluCandy=1;
    }
    public void resetCounterPurpleCandy(){
        counterBluCandy=35;
    }
    
    private GameController() {

        currentSpeed = 0;
        isJumping = false;
        possibilityOfJumping = true;
        isFire = false;
        delay = false;
        addBubble = new ArrayList<>();
        removeBubble = new ArrayList<>();
        removeMonster = new ArrayList<>();
        addMonster = new ArrayList<>();
        addKilledMonster = new ArrayList<>();
        removeKilledMonster = new ArrayList<>();
        removeLoot = new ArrayList<>();
        addLoot = new ArrayList<>();
        removePowerUp = new ArrayList<>();
        addPowerUp = new ArrayList<>();

        counterBluCandy=3;
        counterPurpleCandy=35;
        
    }


    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    public void init() {
        model = Model.getInstance();
        view = View.getInstance();


        view.getMapPanel().addKeyListener(this);
    }

    public void GameLoop() {


        while (model.getLevel().getMainCharacter().getHealth() != 0 || model.getLevel().getLevel() > 16) {
            fireAnimation();
            mainCharacterMove();
            EnemiesKill();

            killedEnemiesMove();
            enemiesMove();

            hitLoots();

            spawnPowerUp();
            powerUpMove();

            collisionEnemies();
            bubbleMove();


            model.getLevel().notifica();

            if (!delay && isFire)
                delayBubble();


            try {
                Thread.sleep(FRAME_RATE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (model.getLevel().getMainCharacter().getHealth() == 0) {
            model.getLevel().getMainCharacter().setHealth(Character.HEALTH);
            view.changePanel(View.Screen.LOSE);
        }
    }

    private void delayBubble() {
        delay = true;
        Thread a = new Thread(() -> {

            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch (model.getLevel().getMainCharacter().getCurrentDirection()) {
                case LEFT:
                    model.getLevel().getMainCharacter().changeCharacterPath(Level.Direction.LEFT);
                    break;
                case RIGHT:
                    model.getLevel().getMainCharacter().changeCharacterPath(Level.Direction.RIGHT);
                    break;
            }

            isFire = false;
            delay = false;
        });
        a.start();
    }

    private void mainCharacterMove() {


        //------------------------------------//
        //hit character control
        if (hitEnemies(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {
            isJumping = false;
            model.getLevel().getMainCharacter().resetHumpHeight();

            switch(model.getLevel().getMainCharacter().getCurrentDirection()){
                case LEFT:model.getLevel().getMainCharacter().changeCharacterPath(Level.Direction.DEAD_LEFT);break;
                case RIGHT:model.getLevel().getMainCharacter().changeCharacterPath(Level.Direction.DEAD_RIGHT);
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            model.getLevel().getEnemies().clear();
            model.getLevel().getBubbles().clear();
            model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth() - 1, model.getLevel().getLevel());

        }

        //------------------------------------//
        //movement either to the right or to the left
        if (currentSpeed != 0 && isValidPositionBrickMainCharacter(model.getLevel().getMainCharacter().getCurrentDirection(), model.getLevel().getMainCharacter().getX() + currentSpeed, model.getLevel().getMainCharacter().getY()))
            model.getLevel().getMainCharacter().move(currentSpeed);

        //------------------------------------//
        //possibility of jumping
        if (possibilityOfJumping && isJumping && model.getLevel().getMainCharacter().getJumpHeight() > 0) {

                model.getLevel().getMainCharacter().jump();
                model.getLevel().getMainCharacter().setJumpHeight(model.getLevel().getMainCharacter().getJumpHeight() - 1);

        }

        //------------------------------------//
        //end-of-jump control
        if (model.getLevel().getMainCharacter().getJumpHeight() == 0) {
            if (fallMainCharacter(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY())) {
                isJumping = false;
                model.getLevel().getMainCharacter().resetHumpHeight();

            }
        }

        //------------------------------------//
        //fall control
        if (!isJumping || !possibilityOfJumping) {
            possibilityOfJumping = !fallMainCharacter(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY());

        }

    }

    private Monster monsterHit(int x, int y) {


        return model.getLevel().getEnemies().parallelStream()
                .filter(enemies -> {
                            boolean hit = false;
                            for (int i = 0; i < enemies.getHeight(); i++) {
                                hit = x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y >= enemies.getY() && y < enemies.getY() + 32;
                                hit |= x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= enemies.getY() && y + model.getLevel().getMainCharacter().getHeight() < enemies.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }

    private boolean hitEnemies(int x, int y) {
        return model.getLevel().getEnemies().parallelStream()
                .anyMatch(enemies -> {
                            boolean hit = false;
                            for (int i = 0; i < enemies.getHeight(); i++) {
                                hit = x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y >= enemies.getY() && y < enemies.getY() + 32;
                                hit |= x + i >= enemies.getX() && x + i < enemies.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= enemies.getY() && y + model.getLevel().getMainCharacter().getHeight() < enemies.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }

    private boolean isValidPositionBrickMainCharacter(Level.Direction direction, int x, int y) {
        model.getLevel().getMainCharacter().changeCharacterPath(model.getLevel().getMainCharacter().getCurrentDirection());
        fireAnimation();

        return isValidPositionBrick(direction, x, y);
    }

    private boolean isValidPositionBrick(Level.Direction direction, int x, int y) {
        return switch (direction) {
            case LEFT -> model.getLevel().getBricks().
                    stream().filter(brick -> brick.getX() <= x && ((brick.getY() + 16 > y && brick.getY() <= y) || (brick.getY() + 16 > y + 16 && brick.getY() <= y + 16))).
                    allMatch(brick -> brick.getX() + Brick.WIDTH <= x) && model.getLevel().getMainCharacter().getX()>Brick.HEIGHT *3 ;
            case RIGHT -> model.getLevel().getBricks().
                    stream().filter(brick -> brick.getX() >= x && ((brick.getY() + 16 > y && brick.getY() <= y) || (brick.getY() + 16 > y + 16 && brick.getY() <= y + 16))).
                    allMatch(brick -> brick.getX() >= x + Character.WIDTH) && model.getLevel().getMainCharacter().getX()+Character.WIDTH<View.WINDOWS_WIDTH -(3*Brick.HEIGHT) ;
            default -> false;
        };
    }

    public void fireAnimation() {
        if (isFire) {
            switch (model.getLevel().getMainCharacter().getCurrentDirection()) {
                case LEFT:
                    model.getLevel().getMainCharacter().changeCharacterPath(Level.Direction.FIRE_LEFT);
                    break;
                case RIGHT:
                    model.getLevel().getMainCharacter().changeCharacterPath(Level.Direction.FIRE_RIGHT);
                    break;
            }
        }
    }

    private boolean fallMainCharacter(int x, int y) {
        boolean isFall;
        isFall = model.getLevel().getBricks().stream().
                noneMatch(brick -> brick.getY() == y+Character.HEIGHT && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16)|| (x + 32 >= brick.getX() && x + 16 < brick.getX() + 32)));
        //System.out.println(isFall);

        if (isFall)
            model.getLevel().getMainCharacter().fall();

        return isFall;
    }

    private void EnemiesKill(){

        if(hitBubble(model.getLevel().getMainCharacter().getX(),model.getLevel().getMainCharacter().getY())) {
            Bubble bubble = bubbleHit(model.getLevel().getMainCharacter().getX(), model.getLevel().getMainCharacter().getY());
            if (bubble.getFireDelay() <= 0) {
                Level.Direction direction =model.getLevel().getMainCharacter().getCurrentDirection();
                Level.Direction directionMonster;
                switch (bubble.getCurrentDirection()){
                    case LEFT: directionMonster = Level.Direction.DEAD_LEFT;break;
                    case RIGHT: directionMonster = Level.Direction.DEAD_RIGHT;break;
                    default: directionMonster = Level.Direction.DEAD_LEFT;break;

                }



                switch (bubble.getCharacterPath()) {
                    case "bubble.png":
                        model.getLevel().setScore(model.getLevel().getScore()+10);
                        removeBubble.add(bubble);
                        break;
                    case "bubbleend.png":
                        model.getLevel().setScore(model.getLevel().getScore()+10);
                        removeBubble.add(bubble);
                        break;
                    case "zenchan.gif":


                        addKilledMonster.add(new ZenChan(bubble.getX(), bubble.getY(),directionMonster.getImagesMovements(),direction));
                        removeBubble.add(bubble);
                        break;
                    case "monsta.gif":
                        addKilledMonster.add(new Monsta(bubble.getX(), bubble.getY(),directionMonster.getImagesMovements(),direction));
                        removeBubble.add(bubble);
                        break;
                    case "pulpul.gif":
                        addKilledMonster.add(new Pulpul(bubble.getX(), bubble.getY(),directionMonster.getImagesMovements(),direction));
                        removeBubble.add(bubble);
                        break;
                    case "mighta.gif":
                        addKilledMonster.add(new Mighta(bubble.getX(), bubble.getY(),directionMonster.getImagesMovements(),direction));
                        removeBubble.add(bubble);
                        break;
                    case "invader.gif":
                        addKilledMonster.add(new Invader(bubble.getX(), bubble.getY(),directionMonster.getImagesMovements(),direction));
                        removeBubble.add(bubble);
                        break;
                    case "drunk.gif":
                        addKilledMonster.add(new Drunk(bubble.getX(), bubble.getY(), directionMonster.getImagesMovements(),direction));
                        removeBubble.add(bubble);
                        break;

                }

                }

            }
        }

    private boolean hitBubble(int x, int y) {
        return model.getLevel().getBubbles().parallelStream()
                .anyMatch(bubble -> {
                            boolean hit = false;
                            for (int i = 0; i < bubble.getHeight(); i++) {
                                hit = x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y >= bubble.getY() && y < bubble.getY() + 32;
                                hit |= x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= bubble.getY() && y + model.getLevel().getMainCharacter().getHeight() < bubble.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }

    private Bubble bubbleHit(int x, int y) {


        return model.getLevel().getBubbles().parallelStream()
                .filter(bubble -> {
                            boolean hit = false;
                            for (int i = 0; i < bubble.getHeight(); i++) {
                                hit = x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y >= bubble.getY() && y < bubble.getY() + 32;
                                hit |= x + i >= bubble.getX() && x + i < bubble.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= bubble.getY() && y + model.getLevel().getMainCharacter().getHeight() < bubble.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }


    //--------------------------------------------------------------------------------------------//


    private void bubbleMove() {
        for (Bubble bubble : addBubble)
            model.getLevel().getBubbles().add(bubble);

        addBubble.clear();

        for (Bubble bubble : model.getLevel().getBubbles()) {


            if (bubble.getClass().getSimpleName().equals("NormalBubble"))
                moveNormalBubble((NormalBubble) bubble);
        }
        for (Bubble bubble : removeBubble)
            model.getLevel().getBubbles().remove(bubble);

        removeBubble.clear();
    }

    private boolean isValidPositionBrickBubble(Bubble bubble) {
        return model.getLevel().getBricks().stream().noneMatch(
                brick -> {
                    boolean resultY = false;
                    boolean resultX = false;
                    for (int i = 0; i < bubble.getHeight(); i++) {
                        resultY |= brick.getY() < bubble.getY() + i && brick.getY() + Brick.HEIGHT > bubble.getY() + i;
                        resultX |= brick.getX() + Brick.WIDTH >= bubble.getX() + i && brick.getX() <= bubble.getX() + i;
                    }

                    return resultY && resultX;
                }
        );
    }

    private void collisionEnemies() {
        ArrayList<Bubble> bubbles = model.getLevel().getBubbles().stream().filter(bubble -> bubble.getFireDelay() > 0).collect(Collectors.toCollection(ArrayList::new));
        for (Bubble bubble : bubbles) {
            if (!bubble.isEnemies() && hitEnemies(bubble.getX(), bubble.getY())) {
                bubble.setEnemies(true);


                Monster monster = monsterHit(bubble.getX(), bubble.getY());
                removeMonster.add(monster);

                switch (monster.getClass().getSimpleName()) {
                    case "ZenChan" -> bubble.changeCharacterPath(Level.Direction.ZENCHAN_BUBBLE);
                    case "Invader" -> bubble.changeCharacterPath(Level.Direction.INVADER_BUBBLE);
                    case "Monsta" -> bubble.changeCharacterPath(Level.Direction.MONSTA_BUBBLE);
                    case "Pulpul" -> bubble.changeCharacterPath(Level.Direction.PULPUL_BUBBLE);
                    case "Mighta" -> bubble.changeCharacterPath(Level.Direction.MIGHTA_BUBBLE);
                    case "Drunk" -> bubble.changeCharacterPath(Level.Direction.DRUNK_BUBBLE);
                }
            }
        }

    }

    private void moveNormalBubble(NormalBubble bubble) {

        if (bubble.getFireDelay() > 0) {
            if(bubble.isEnemies()){ bubble.clearFireDelay();}
            if(!bubble.isEnemies())
                if (isValidPositionBrickBubble(bubble)) {
                    bubble.decreaseFireDelay();

                    switch (bubble.getCurrentDirection()) {
                        case LEFT:
                            bubble.move(-3);
                            break;
                        case RIGHT:
                            bubble.move(3);
                    }
                } else {bubble.changeCharacterPath(Level.Direction.BUBBLE_END); bubble.clearFireDelay();}

        }
        if(bubble.getFireDelay() == 0) {
            bubble.changeCharacterPath(Level.Direction.BUBBLE_END);

        }

        // FIXME: If inutile
        if (bubble.getFireDelay() <= 0) {

            if (model.getLevel().getBricks().stream().noneMatch(brick -> brick.getY() + Brick.HEIGHT == bubble.getY() - 1 &&
                    ((brick.getX() < bubble.getX() && brick.getX() + Brick.WIDTH >= bubble.getX()) ||
                            (brick.getX() < bubble.getX() + bubble.getWidth() && brick.getX() + Brick.WIDTH >= bubble.getX() + bubble.getWidth()))
            )) {
                bubble.fly(0, -1);
                
            } else
                switch (bubble.getCurrentDirection()) {
                    case LEFT:
                        if (model.getLevel().getBricks().stream().anyMatch(
                                brick -> bubble.getY() > brick.getY() && bubble.getY() < brick.getY() + Brick.HEIGHT && bubble.getX() - 1 >= brick.getX() + brick.getWidth()
                        ))
                            bubble.move(-1);
                        
                        else
                            bubble.setCurrentDirection(Level.Direction.RIGHT);
                        break;
                        
                    case RIGHT:
                        if (model.getLevel().getBricks().stream().anyMatch(
                                brick -> bubble.getY() >= brick.getY() && bubble.getY() < brick.getY() + Brick.HEIGHT && bubble.getX() + bubble.getWidth() + 1 <= brick.getX()
                        ))
                            bubble.move(1);
                        else
                            bubble.setCurrentDirection(Level.Direction.LEFT);
                }
        }

        if (bubble.getY() < 0)
            respawnEnemies(bubble);


    }

    private void respawnEnemies(Bubble bubble) {
        removeBubble.add(bubble);
        switch (bubble.getCharacterPath()) {
            case "zenchan.gif":
                addMonster.add(new ZenChan(15 * 16, bubble.getY()));
                break;
                
            case "invader.gif":
                addMonster.add(new Invader(15 * 16, bubble.getY()));
                break;
                
            case "bobbub.gif":
                addMonster.add(new Mighta(15 * 16, bubble.getY()));
                break;
                
            case "pulpul.gif":
                addMonster.add(new Pulpul(15 * 16, bubble.getY()));
                break;
                
            case "drunk.gif":
                addMonster.add(new Drunk(15 * 16, bubble.getY()));
                break;
                
            case "monsta.gif":
                addMonster.add(new Monsta(15 * 16, bubble.getY()));
                break;
        }
    }


    //----------------------------------------------------------------------------------//
    //Hit Loots


    private Loot lootHit(int x,int y){
        return model.getLevel().getLoots().parallelStream()
                .filter(loot -> {
                            boolean hit = false;
                            for (int i = 0; i < loot.getHeight(); i++) {
                                hit = x + i >= loot.getX() && x + i < loot.getX() + 32 && y >= loot.getY() && y < loot.getY() + 32;
                                hit |= x + i >= loot.getX() && x + i < loot.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= loot.getY() && y + model.getLevel().getMainCharacter().getHeight() < loot.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                ).findFirst().orElse(null);
    }

    private boolean isHitLoots(int x,int y){
        return model.getLevel().getLoots().stream()
                .anyMatch(loot -> {
                            boolean hit = false;
                            for (int i = 0; i < loot.getHeight(); i++) {
                                hit = x + i >= loot.getX() && x + i < loot.getX() + 32 && y >= loot.getY() && y < loot.getY() + 32;
                                hit |= x + i >= loot.getX() && x + i < loot.getX() + 32 && y + model.getLevel().getMainCharacter().getHeight() >= loot.getY() && y + model.getLevel().getMainCharacter().getHeight() < loot.getY() + 32;
                                if (hit) break;
                            }
                            return hit;
                        }
                );
    }
    private void hitLoots(){
        for(Loot loot:addLoot)
            model.getLevel().getLoots().add(loot);
        addLoot.clear();
        for(Loot loot:removeLoot)
            model.getLevel().getLoots().remove(loot);
        removeLoot.clear();

        if(isHitLoots(model.getLevel().getMainCharacter().getX(),model.getLevel().getMainCharacter().getY())){
            Loot loot= lootHit(model.getLevel().getMainCharacter().getX(),model.getLevel().getMainCharacter().getY());
            removeLoot.add(loot);
            model.getLevel().setScore(model.getLevel().getScore()+loot.getScore());
        }

    }

    //----------------------------------------------------------------------------//

    //powerUP

    
    public void spawnPowerUp(){
        Random random = new Random();
        int minX = 64;
        int maxX = 432;

        int minY = 48;
        int maxY = 384;
        if(counterBluCandy == 0) {

            System.out.println("si");
            int randomX = random.nextInt((maxX - minX) + 1) + minX;
            int randomY = random.nextInt((maxY - minY) + 1) + minY;

            resetCounterBluCandy();
            addPowerUp.add(new PowerUp(randomX,randomY, PowerUp.PowerUpType.BLUE_CANDY));
        }
    }

    public void powerUpMove(){

        for(PowerUp powerUp:addPowerUp)
            model.getLevel().getPowerUps().add(powerUp);
        addPowerUp.clear();
        for(PowerUp powerUp:removePowerUp)
            model.getLevel().getPowerUps().remove(powerUp);
        removePowerUp.clear();
        CopyOnWriteArrayList<PowerUp> powerUps =new CopyOnWriteArrayList<>(model.getLevel().getPowerUps());
        for(PowerUp powerUp: powerUps){

            if(powerUpFall(powerUp))
                powerUp.fall();


        }


    }


    public boolean powerUpFall(PowerUp powerUp){
        boolean isFall = false;
        int x = powerUp.getX();
        int y = powerUp.getY();
        isFall = model.getLevel().getBricks().stream().
                filter(brick -> brick.getY() >= y && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 >= brick.getX() && x + 32 < brick.getX() + 16))).
                anyMatch(brick -> brick.getY() == y + Character.HEIGHT || brick.getY()+1 == y + Character.HEIGHT);


        return !isFall;
    }











    
    
    



    //--------------------------------------------------------------------------------------------//
    //enemiesKilled

    private void killedEnemiesMove() {
        for (Monster monster : addKilledMonster)
            model.getLevel().getKilledEnemies().add(monster);
        addKilledMonster.clear();
        for (Monster monster : removeKilledMonster)
            model.getLevel().getKilledEnemies().remove(monster);
        removeKilledMonster.clear();


        CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList(model.getLevel().getKilledEnemies());
        for (Monster monster : monsters) {
            monster = (Enemies) monster;
            int newX = 0;
            int newY = 0;
            if (((Enemies) monster).getDeadSize() > 0) {
                ((Enemies) monster).decreaseDeadSize();

                switch (monster.getCurrentDirection()) {
                    case LEFT:
                        if (monster.getY() > 48)
                            newY = -3;
                        if (monster.getX() > 48)
                            newX = -3;
                        else
                            monster.setCurrentDirection(Level.Direction.RIGHT);
                        break;
                    case RIGHT:
                        if (monster.getY() > 48)
                            newY = -3;
                        if (monster.getX() + monster.getWidth() < View.WINDOWS_WIDTH - 48)
                            newX = 3;
                        else
                            monster.setCurrentDirection(Level.Direction.LEFT);
                        break;
                }
                monster.fly(newX, newY);
            } else {
                if (enemiesFall(monster))
                    monster.fall();
                else {
                    removeKilledMonster.add(monster);
                    switch ((int) (Math.random() * 10)) {
                        case 1:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.APPLE));
                            break;
                        case 2:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.BANANA));
                            break;
                        case 3:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.CHERRIES));
                            break;
                        case 4:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.GRAPE));
                            break;
                        case 5:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.ICE_CREAM_CUP));
                            break;
                        case 6:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.LEMON));
                            break;
                        case 7:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.ORANGE));
                            break;
                        case 8:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.PEACH));
                            break;
                        case 9:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.PEAR));
                            break;
                        default:
                            addLoot.add(new Loot(monster.getX(), monster.getY(), Loot.NameLoot.APPLE));
                            break;
                    }

                }
            }

        }
    }


    //-------------------------------------------------------------------------------//
    //Enemies
    private void enemiesMove() {
        for (Monster monster : addMonster)
            model.getLevel().getEnemies().add(monster);

        addMonster.clear();

        for (Monster monster : removeMonster) {
            System.out.println(monster);
            model.getLevel().getEnemies().remove(monster);
        }

        removeMonster.clear();

        for (Monster monster : model.getLevel().getEnemies()) {
            switch (monster.getClass().getSimpleName()) {
                case "ZenChan":
                    if (enemiesFall(monster)) {
                        monster.fall();
                        break;
                    }
                    zenChanMove((ZenChan) monster);
                    break;
                case "Invader":
                    if (enemiesFall(monster)) {
                        monster.fall();
                        break;
                    }
                    InvaderMove((Invader) monster);
                    break;
                case "Monsta":
                    monstaMove((Monsta) monster);
                    break;
                case "Mighta":
                    mightaMove((Mighta) monster);
                    break;
                case "Pulpul":
                    pulpulMove((Pulpul) monster);
                    break;
                case "Drunk":
                    if (enemiesFall(monster)) {
                        monster.fall();
                        break;
                    }
                    drunkMove((Drunk) monster);
                    break;
            }
        }
    }

    private void monstaMove(Monsta monsta) {
        int newX;
        boolean valid = enemiesFall(monsta);
        if (valid) monsta.fall();
        if (!valid) {
            if (model.getLevel().getBricks().parallelStream().
                    filter(brick -> brick.getY() >= monsta.getY() && ((monsta.getX() >= brick.getX() && monsta.getX() < brick.getX() + 16) || (monsta.getX() + 31 >= brick.getX() && monsta.getX() + 31 < brick.getX() + 16))).
                    allMatch(brick -> brick.getY() > monsta.getY() + Monster.HEIGHT))
                monsta.fall();
            else {

                switch (monsta.getCurrentDirection()) {
                    case LEFT:
                        newX = monsta.getX() - monsta.getSpeed();
                        if (model.getLevel().getBricks().
                                stream().filter(brick -> brick.getX() <= monsta.getX() && ((brick.getY() == monsta.getY()) || (brick.getY() == monsta.getY() + 16)))
                                .allMatch(brick -> brick.getX() + 16 <= newX))
                            monsta.move(-monsta.getSpeed());
                        else {
                            monsta.setCurrentDirection(Level.Direction.RIGHT);
                            monsta.changeCharacterPath(Level.Direction.RIGHT);
                        }
                        break;
                    case RIGHT:
                        newX = monsta.getX() + monsta.getSpeed();
                        if (model.getLevel().getBricks().
                                stream().filter(brick -> brick.getX() >= monsta.getX() && ((brick.getY() == monsta.getY()) || (brick.getY() == monsta.getY() + 16)))
                                .allMatch(brick -> brick.getX() >= newX + 32))
                            monsta.move(monsta.getSpeed());
                        else {
                            monsta.setCurrentDirection(Level.Direction.LEFT);
                            monsta.changeCharacterPath(Level.Direction.LEFT);
                        }
                        break;
                }

            }
        }
    }

    private void InvaderMove(Invader invader) {
        int newX;

        if (!invader.isFear()) {
            if (model.getLevel().getMainCharacter().getX() == invader.getX()) return;
            if (model.getLevel().getMainCharacter().getX() < invader.getX()) {
                invader.setCurrentDirection(Level.Direction.LEFT);
                invader.changeCharacterPath(Level.Direction.LEFT);
            } else {
                invader.setCurrentDirection(Level.Direction.RIGHT);
                invader.changeCharacterPath(Level.Direction.RIGHT);
            }
        }

        switch (invader.getCurrentDirection()) {
            case RIGHT -> {
                newX = invader.getX() + invader.getSpeed();

                if (model.getLevel().getBricks().stream().
                        anyMatch(brick -> brick.getY() == invader.getY() + invader.getHeight() && ((newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH) || (newX + invader.getWidth() >= brick.getX() && newX + invader.getWidth() < brick.getX() + Brick.WIDTH)))
                        && isValidPositionBrick(invader.getCurrentDirection(), newX, invader.getY()))
                    invader.move(invader.getSpeed());
                else {

                    invader.setCurrentDirection(Level.Direction.LEFT);
                    invader.changeCharacterPath(Level.Direction.LEFT);

                    invader.setSpeed(3);
                    invader.setFear(true);
                }
            }
            case LEFT -> {
                newX = invader.getX() - invader.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == invader.getY() + invader.getHeight() && ((newX + invader.getWidth() >= brick.getX() && newX + invader.getWidth() < brick.getX() + Brick.WIDTH) || newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH))
                        && isValidPositionBrick(invader.getCurrentDirection(), newX, invader.getY()))
                    invader.move(-invader.getSpeed());
                else {
                    invader.setCurrentDirection(Level.Direction.RIGHT);
                    invader.changeCharacterPath(Level.Direction.RIGHT);
                    invader.setSpeed(3);
                    invader.setFear(true);
                }
            }
        }
        if (invader.isFear())
            invader.decreseFearSpace();

        if (invader.getFearSpace() == 0) {
            invader.setFear(false);
            invader.resetFearSpace();
            invader.setSpeed(1);
        }

    }

    private void mightaMove(Mighta mighta) {
        int x;
        int y;
        if (model.getLevel().getMainCharacter().getX() < mighta.getX()) {
            x = -1;
            mighta.changeCharacterPath(Level.Direction.LEFT);
        } else if (model.getLevel().getMainCharacter().getX() == mighta.getX())
            x = 0;
        else {
            x = 1;
            mighta.changeCharacterPath(Level.Direction.RIGHT);
        }

        if (model.getLevel().getMainCharacter().getY() < mighta.getY())
            y = -1;
        else
            y = 1;

        mighta.fly(x, y);
    }

    private void pulpulMove(Pulpul pulpul) {

        if (pulpul.isJump()) {
            pulpulMoveHeightEquals(pulpul);
            pulpulMoveHeightUp(pulpul);
            return;
        }

        boolean valid = enemiesFall(pulpul);

        if (valid) {
            pulpul.fall();
            return;
        }

        pulpul.setPossibilityOfJumping(true);

        if (model.getLevel().getMainCharacter().getY() == pulpul.getY())
            pulpulMoveHeightEquals(pulpul);
        if (model.getLevel().getMainCharacter().getY() > pulpul.getY())

            pulpulMoveHeightDown(pulpul);
        if (pulpul.isPossibilityOfJumping() && model.getLevel().getMainCharacter().getY() < pulpul.getY())
            pulpulMoveHeightUp(pulpul);


    }



    //TODO: pulpul è da rifare
    private void pulpulMoveHeightUp(Pulpul pulpul) {
        pulpul.setPossibilityOfJumping(false);
        pulpul.setJump(true);
        pulpul.decreaseSizeJump();
        if (pulpul.getSizeJump() == 0) {
            pulpul.setJump(false);
            pulpul.resetJump();
        }
        pulpul.jump();

    }

    private void pulpulMoveHeightDown(Pulpul pulpul) {
        int newX;
        switch (pulpul.getCurrentDirection()) {
            case LEFT -> {
                newX = pulpul.getX() - pulpul.getSpeed();
                if (model.getLevel().getBricks().
                        stream().filter(brick -> brick.getX() <= pulpul.getX() && ((brick.getY() == pulpul.getY()) || (brick.getY() == pulpul.getY() + 16)))
                        .allMatch(brick -> brick.getX() + 16 <= newX))

                    pulpul.move(-pulpul.getSpeed());
                else {
                    pulpul.setCurrentDirection(Level.Direction.RIGHT);
                    pulpul.changeCharacterPath(Level.Direction.RIGHT);
                }
            }

            case RIGHT -> {
                newX = pulpul.getX() + pulpul.getSpeed();
                if (model.getLevel().getBricks().
                        stream().filter(brick -> brick.getX() >= pulpul.getX() && ((brick.getY() == pulpul.getY()) || (brick.getY() == pulpul.getY() + 16)))
                        .allMatch(brick -> brick.getX() >= newX + 32))

                    pulpul.move(pulpul.getSpeed());
                else {
                    pulpul.setCurrentDirection(Level.Direction.LEFT);
                    pulpul.changeCharacterPath(Level.Direction.LEFT);
                }
            }
        }
    }

    private void pulpulMoveHeightEquals(Pulpul pulpul) {
        int newX;
        pulpul.setSpeed(2);
        if (model.getLevel().getMainCharacter().getX() <= pulpul.getX()) {
            pulpul.setCurrentDirection(Level.Direction.LEFT);
            pulpul.changeCharacterPath(Level.Direction.LEFT);

            newX = pulpul.getX() - pulpul.getSpeed();
            if (model.getLevel().getBricks().parallelStream().
                    anyMatch(brick -> brick.getY() == pulpul.getY() + pulpul.getHeight() && ((newX + pulpul.getWidth() >= brick.getX() && newX + pulpul.getWidth() < brick.getX() + Brick.WIDTH) || newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH))
                    && isValidPositionBrick(pulpul.getCurrentDirection(), newX, pulpul.getY()))

                pulpul.move(-pulpul.getSpeed());
        } else {
            pulpul.setJump(true);
            pulpul.setCurrentDirection(Level.Direction.RIGHT);
            pulpul.changeCharacterPath(Level.Direction.RIGHT);
            newX = pulpul.getX() + pulpul.getSpeed();
            if (model.getLevel().getBricks().stream().
                    anyMatch(brick -> brick.getY() == pulpul.getY() + pulpul.getHeight() && ((newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH) || (newX + pulpul.getWidth() >= brick.getX() && newX + pulpul.getWidth() < brick.getX() + Brick.WIDTH)))
                    && isValidPositionBrick(pulpul.getCurrentDirection(), newX, pulpul.getY()))
                pulpul.move(pulpul.getSpeed());
        }
    }

    //-------------------------

    private boolean enemiesFall(Monster monster) {
        boolean isFall = false;
        int x = monster.getX();
        int y = monster.getY();
        isFall = model.getLevel().getBricks().stream().
                filter(brick -> brick.getY() >= y && ((x >= brick.getX() && x < brick.getX() + 16) || (x + 16 >= brick.getX() && x + 16 < brick.getX() + 16) || (x + 32 >= brick.getX() && x + 32 < brick.getX() + 16))).
                anyMatch(brick -> brick.getY() == y + Character.HEIGHT || brick.getY()+1 == y + Character.HEIGHT);


        return !isFall;


    }

    private void drunkMove(Drunk drunk) {
        switch (drunk.getCurrentDirection()) {
            case LEFT:
                int newX = drunk.getX() - drunk.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == drunk.getY() + drunk.getHeight() && (newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH))
                        && isValidPositionBrick(drunk.getCurrentDirection(), newX, drunk.getY()))

                    drunk.move(-drunk.getSpeed());

                else {
                    drunk.setCurrentDirection(Level.Direction.RIGHT);
                    drunk.changeCharacterPath(Level.Direction.RIGHT);
                }

                break;

            case RIGHT:
                newX = drunk.getX() + drunk.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == drunk.getY() + drunk.getHeight() && (newX + drunk.getWidth() >= brick.getX() && newX + drunk.getWidth() < brick.getX() + Brick.WIDTH))
                        && isValidPositionBrick(drunk.getCurrentDirection(), newX, drunk.getY()))
                    drunk.move(drunk.getSpeed());
                else {
                    drunk.setCurrentDirection(Level.Direction.LEFT);
                    drunk.changeCharacterPath(Level.Direction.LEFT);
                }
                break;
        }
    }

    private void zenChanMove(ZenChan zenChan) {
        switch (zenChan.getCurrentDirection()) {
            case LEFT:
                int newX = zenChan.getX() - zenChan.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == zenChan.getY() + zenChan.getHeight() && (newX >= brick.getX() && newX < brick.getX() + Brick.WIDTH))
                        && isValidPositionBrick(zenChan.getCurrentDirection(), newX, zenChan.getY()))

                    zenChan.move(-zenChan.getSpeed());

                else {
                    zenChan.setCurrentDirection(Level.Direction.RIGHT);
                    zenChan.changeCharacterPath(Level.Direction.RIGHT);
                }

                break;

            case RIGHT:
                newX = zenChan.getX() + zenChan.getSpeed();
                if (model.getLevel().getBricks().parallelStream().
                        anyMatch(brick -> brick.getY() == zenChan.getY() + zenChan.getHeight() && (newX + zenChan.getWidth() >= brick.getX() && newX + zenChan.getWidth() < brick.getX() + Brick.WIDTH))
                        && isValidPositionBrick(zenChan.getCurrentDirection(), newX, zenChan.getY()))
                    zenChan.move(zenChan.getSpeed());
                else {
                    zenChan.setCurrentDirection(Level.Direction.LEFT);
                    zenChan.changeCharacterPath(Level.Direction.LEFT);
                }
                break;
        }
    }



//--------------------------------------------------------------------------------------------//

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A -> {
                currentSpeed = 0;
                model.getLevel().getMainCharacter().setCurrentDirection(Level.Direction.LEFT);
            }
            case KeyEvent.VK_D -> {
                currentSpeed = 0;
                model.getLevel().getMainCharacter().setCurrentDirection(Level.Direction.RIGHT);
            }
            case KeyEvent.VK_ENTER -> {
                if (!isFire) {
                    isFire = true;
                    Bubble bubble =model.getLevel().getMainCharacter().fire();
                    if (!(bubble.getFireDelay() != 0 && !isValidPositionBrickBubble(bubble)))
                        addBubble.add(bubble);

                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_A -> {
                model.getLevel().getMainCharacter().setCurrentDirection(Level.Direction.LEFT);
                currentSpeed = -2;
            }

            case KeyEvent.VK_D -> {
                model.getLevel().getMainCharacter().setCurrentDirection(Level.Direction.RIGHT);
                currentSpeed = 2;
            }

            case KeyEvent.VK_SPACE -> {
                if (possibilityOfJumping) {
                    isJumping = true;
                    possibilityOfJumping=false;
                    counterBluCandy--;
                }
            }

            case KeyEvent.VK_ESCAPE -> {
                view.changePanel(View.Screen.PUASE);
                //TODO: Block game loop
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    //--------------------------------------------------------------------------------------------//

}
