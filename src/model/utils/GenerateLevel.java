package model.utils;

import model.Level;
import model.entity.monster.enemies.*;
import model.entity.objects.Brick;

import java.nio.file.Path;
import java.util.Random;

import static model.Level.*;

public class GenerateLevel {
    private Level level;

    /**
     * GenerateLevel constructor
     *
     * @param level level instance
     */
    public GenerateLevel(Level level) {
        this.level = level;
    }

    /**
     * Generate the level based on level number
     *
     * @param level level number
     */
    public void regenerateLevel(int level) {
        drawLevelBorder();

        // Set brick image based on level number
        this.level.setBricksImage(FileManager.getResource("blocks", "normal_blocks", "block_" + level + ".png"));

        switch (level) {
            case 1 -> drawOne();
            case 2 -> drawTwo();
            case 3 -> drawThree();
            case 4 -> drawFour();

            case 5 -> drawFive();
            case 6 -> drawSix();
            case 7 -> drawSeven();
            case 8 -> drawEight();

            case 9 -> drawNine();
            case 10 -> drawTen();
            case 11 -> drawEleven();
            case 12 -> drawTwelve();

            case 13 -> drawThirteen();
            case 14 -> drawFourteen();
            case 15 -> drawFifteen();
            case 16 -> drawSixteen();
        }

        FileManager.serialize(this.level, String.valueOf(Path.of(LEVEL_FOLDER, String.valueOf(level))));
    }

    /**
     * Draw border of the level
     */
    private void drawLevelBorder() {
        for (int j = PADDING; j < COLUMNS - PADDING; j++) {
            if (j * Brick.WIDTH <= 13 * Brick.HEIGHT || j * Brick.WIDTH > 17 * Brick.HEIGHT)
                level.addBrick(new Brick(j * Brick.WIDTH, Brick.HEIGHT * PADDING));

            level.addBrick(new Brick(j * Brick.WIDTH, (ROWS - PADDING - 1) * Brick.HEIGHT));
        }

        for (int i = PADDING; i < ROWS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * PADDING, i * Brick.HEIGHT));
            level.addBrick(new Brick((COLUMNS - PADDING - 1) * Brick.WIDTH, i * Brick.HEIGHT));
        }
    }

    /**
     * Draw level one by adding bricks and add enemies
     */
    private void drawOne() {

        for (int i = 4; i < 12; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 336));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 336));
        }

        for (int i = 4; i < 12; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 240));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 240));
        }

        for (int i = 4; i < 12; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 144));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 144));
        }

        level.getEnemies().add(new Zenchan((PADDING + 3) * Brick.WIDTH, 200));
        level.getEnemies().add(new Zenchan((PADDING + 3) * Brick.WIDTH, 70));
        level.getEnemies().add(new Zenchan((COLUMNS - PADDING - 5) * Brick.WIDTH, 70));
        level.getEnemies().add(new Zenchan((COLUMNS - PADDING - 5) * Brick.WIDTH, 200));
    }

    /**
     * Draw level two by adding bricks and add enemies
     */
    private void drawTwo() {
        for (int i = PADDING + 3; i < COLUMNS - PADDING - 3; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 10 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 14 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 18 - PADDING)));
        }

        level.getEnemies().add(new Pulpul((COLUMNS - PADDING - 5) * Brick.WIDTH + 10, 200));
        level.getEnemies().add(new Zenchan((COLUMNS - PADDING - 5) * Brick.WIDTH + 10, 150));
    }

    /**
     * Draw level three by adding bricks and add enemies
     */
    private void drawThree() {
        for (int i = PADDING + 1; i < COLUMNS - PADDING - 3; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 10 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 18 - PADDING)));
        }

        for (int i = PADDING + 4; i < COLUMNS - PADDING - 1; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 14 - PADDING)));

        level.getEnemies().add(new Pulpul((COLUMNS - PADDING - 12) * Brick.WIDTH, 250));
        level.getEnemies().add(new Zenchan((COLUMNS - PADDING - 5) * Brick.WIDTH, 150));
        level.getEnemies().add(new Zenchan((COLUMNS - PADDING - 5) * Brick.WIDTH, 200));
    }

    /**
     * Draw level four by adding bricks and add enemies
     */
    private void drawFour() {
        for (int i = PADDING + 3; i < COLUMNS - PADDING - 3; i++) {
            if (i == (PADDING + 3) || i == (COLUMNS - PADDING - 4)) {
                level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 11 - PADDING)));
                level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
                level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 21 - PADDING)));
            }

            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 10 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 15 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        level.getEnemies().add(new Drunk((COLUMNS - PADDING - 14) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        level.getEnemies().add(new Drunk((COLUMNS - PADDING - 14) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 17 - PADDING)));
        level.getEnemies().add(new Drunk((COLUMNS - PADDING - 14) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 22 - PADDING)));
    }

    /**
     * Draw level five by adding bricks and add enemies
     */
    private void drawFive() {
        // Bottom line
        for (int i = 4; i < 12; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 336));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 336));
        }

        // First separated lines
        for (int i = PADDING + 1; i < COLUMNS - PADDING - 22; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        for (int i = COLUMNS - PADDING - 14; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        // Second separated lines
        for (int i = PADDING + 1; i < COLUMNS - PADDING - 18; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        for (int i = COLUMNS - PADDING - 10; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        // Third separated lines
        for (int i = PADDING + 1; i < COLUMNS - PADDING - 14; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }

        for (int i = COLUMNS - PADDING - 8; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }

        level.getEnemies().add(new Invader((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        level.getEnemies().add(new Invader((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 17 - PADDING)));
        level.getEnemies().add(new Invader((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 22 - PADDING)));
        level.getEnemies().add(new Zenchan((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 22 - PADDING)));
    }

    /**
     * Draw level six by adding bricks and add enemies
     */
    private void drawSix() {
        for (int i = 4; i < 24; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 336));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 336));
        }

        for (int i = 4; i < 18; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 240));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 240));
        }

        for (int i = 4; i < 12; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 144));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 144));
        }

        level.getEnemies().add(new Mighta((PADDING + 3) * Brick.WIDTH, 200));
        level.getEnemies().add(new Monsta((PADDING + 3) * Brick.WIDTH, 70));
        level.getEnemies().add(new Monsta((COLUMNS - PADDING - 5) * Brick.WIDTH, 70));
        level.getEnemies().add(new Monsta((COLUMNS - PADDING - 5) * Brick.WIDTH, 200));
    }

    /**
     * Draw level seven by adding bricks and add enemies
     */
    private void drawSeven() {
        for (int i = COLUMNS - PADDING - 21; i < COLUMNS - PADDING - 11; i++)
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), (PADDING + ROWS - 24) * Brick.HEIGHT));

        for (int i = COLUMNS - PADDING - 25; i < COLUMNS - PADDING - 7; i++)
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), (PADDING + ROWS - 10) * Brick.HEIGHT));

        for (int i = COLUMNS - PADDING - 18; i < COLUMNS - PADDING - 13; i++)
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), (PADDING + ROWS - 18) * Brick.HEIGHT));

        for (int i = 4; i < 12; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), (PADDING + ROWS - 14) * Brick.HEIGHT));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, (PADDING + ROWS - 14) * Brick.HEIGHT));
        }

        level.getEnemies().add(new Zenchan((PADDING + 3) * Brick.WIDTH, (PADDING + ROWS - 26) * Brick.HEIGHT));
        level.getEnemies().add(new Zenchan((PADDING + 23) * Brick.WIDTH, (PADDING + ROWS - 26) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 3) * Brick.WIDTH, (PADDING + ROWS - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul((PADDING + 6) * Brick.WIDTH, (PADDING + ROWS - 16) * Brick.HEIGHT));
    }

    /**
     * Draw level eight by adding bricks and add enemies
     */
    private void drawEight() {
        // First separated lines
        for (int i = PADDING + 1; i < COLUMNS - PADDING - 22; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        for (int i = COLUMNS - PADDING - 6; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        // Second separated lines
        for (int i = PADDING + 1; i < COLUMNS - PADDING - 18; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        for (int i = COLUMNS - PADDING - 8; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        // Third separated lines
        for (int i = PADDING + 1; i < COLUMNS - PADDING - 14; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }

        for (int i = COLUMNS - PADDING - 10; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }

        for (int i = COLUMNS - PADDING - 10; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 8 - PADDING)));
        }


        level.getEnemies().add(new Zenchan((PADDING + 3) * Brick.WIDTH, 200));
        level.getEnemies().add(new Zenchan((PADDING + 3) * Brick.WIDTH, 100));
        level.getEnemies().add(new Mighta((PADDING + 3) * Brick.WIDTH, 70));
    }

    /**
     * Draw level nine by adding bricks and add enemies
     */
    private void drawNine() {
        for (int i = ROWS - PADDING - 17; i < ROWS - PADDING - 9; i++)
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 24), Brick.HEIGHT * (ROWS - PADDING - i)));


        for (int i = COLUMNS - PADDING - 24; i < COLUMNS - PADDING - 14; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (ROWS - PADDING - 9)));


        for (int i = COLUMNS - PADDING - 24; i < COLUMNS - PADDING - 16; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (ROWS - PADDING - 17)));


        level.addBrick(new Brick((COLUMNS - PADDING - 10) * Brick.WIDTH, Brick.HEIGHT * (ROWS - PADDING - 4)));
        level.addBrick(new Brick((COLUMNS - PADDING - 10) * Brick.WIDTH, Brick.HEIGHT * (ROWS - PADDING - 14)));


        level.getEnemies().add(new Zenchan((PADDING + 3) * Brick.WIDTH, (PADDING + 6) * Brick.HEIGHT));
        level.getEnemies().add(new Zenchan((PADDING + 12) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 6) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
    }

    /**
     * Draw level ten by adding bricks and add enemies
     */
    private void drawTen() {
        for (int i = ROWS - PADDING - 20; i < ROWS - PADDING - 8; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 24), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 20), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 16), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 12), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 8), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 4), Brick.HEIGHT * (ROWS - PADDING - i)));
        }

        level.addBrick(new Brick(Brick.WIDTH * (PADDING + 3), Brick.HEIGHT * (PADDING + 13)));
        level.addBrick(new Brick(Brick.WIDTH * (PADDING + 3), Brick.HEIGHT * (PADDING + 18)));

        level.getEnemies().add(new Pulpul(Brick.WIDTH * (COLUMNS - PADDING - 23), (PADDING + ROWS - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul(Brick.WIDTH * (COLUMNS - PADDING - 19), (PADDING + ROWS - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul(Brick.WIDTH * (COLUMNS - PADDING - 15), (PADDING + ROWS - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul(Brick.WIDTH * (COLUMNS - PADDING - 11), (PADDING + ROWS - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul(Brick.WIDTH * (COLUMNS - PADDING - 7), (PADDING + ROWS - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul(Brick.WIDTH * (COLUMNS - PADDING - 3), (PADDING + ROWS - 16) * Brick.HEIGHT));
    }

    /**
     * Draw level eleven by adding bricks and add enemies
     */
    private void drawEleven() {
        Random random = new Random();

        for (int i = 0; i < 30; i++)
            level.addBrick(
                    new Brick(
                            Brick.WIDTH * random.nextInt(PADDING, COLUMNS - PADDING),
                            Brick.HEIGHT * random.nextInt(PADDING + 10, ROWS - PADDING - 4)
                    )
            );

        level.getEnemies().add(new Mighta((PADDING + 13) * Brick.WIDTH, (PADDING + ROWS - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 14) * Brick.WIDTH, (PADDING + ROWS - 14) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 15) * Brick.WIDTH, (PADDING + ROWS - 12) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 16) * Brick.WIDTH, (PADDING + ROWS - 10) * Brick.HEIGHT));
    }

    /**
     * Draw level twelve by adding bricks and add enemies
     */
    private void drawTwelve() {
        for (int i = ROWS - PADDING - 16; i < ROWS - PADDING - 9; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 24), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 4), Brick.HEIGHT * (ROWS - PADDING - i)));
        }


        for (int i = COLUMNS - PADDING - 20; i < COLUMNS - PADDING - 8; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (ROWS - PADDING - 6)));


        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 22), Brick.HEIGHT * (ROWS - PADDING - 10)));
        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 21), Brick.HEIGHT * (ROWS - PADDING - 10)));


        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 22), Brick.HEIGHT * (ROWS - PADDING - 14)));
        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 21), Brick.HEIGHT * (ROWS - PADDING - 14)));

        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 6), Brick.HEIGHT * (ROWS - PADDING - 10)));
        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 7), Brick.HEIGHT * (ROWS - PADDING - 10)));

        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 6), Brick.HEIGHT * (ROWS - PADDING - 14)));
        level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 7), Brick.HEIGHT * (ROWS - PADDING - 14)));


        level.getEnemies().add(new Monsta((PADDING + 6) * Brick.WIDTH, (ROWS - PADDING - 12) * Brick.HEIGHT));
        level.getEnemies().add(new Invader((PADDING + 6) * Brick.WIDTH, (ROWS - PADDING - 16) * Brick.HEIGHT));
        level.getEnemies().add(new Monsta((PADDING + 22) * Brick.WIDTH, (ROWS - PADDING - 12) * Brick.HEIGHT));
        level.getEnemies().add(new Monsta((PADDING + 22) * Brick.WIDTH, (ROWS - PADDING - 16) * Brick.HEIGHT));


        level.getEnemies().add(new Zenchan((PADDING + 12) * Brick.WIDTH, (PADDING + ROWS - 16) * Brick.HEIGHT));
    }

    /**
     * Draw level thirteen by adding bricks and add enemies
     */
    private void drawThirteen() {
        level.getEnemies().add(new Drunk((COLUMNS - PADDING - 6) * Brick.WIDTH, (PADDING + 4) * Brick.HEIGHT));
        level.getEnemies().add(new Drunk((COLUMNS - PADDING - 8) * Brick.WIDTH, (PADDING + 4) * Brick.HEIGHT));
        level.getEnemies().add(new Zenchan((COLUMNS - PADDING - 6) * Brick.WIDTH, (PADDING + 4) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 4) * Brick.WIDTH, (PADDING + 4) * Brick.HEIGHT));

    }

    /**
     * Draw level fourteen by adding bricks and add enemies
     */
    private void drawFourteen() {
        for (int i = ROWS - PADDING - 16; i < ROWS - PADDING - 11; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 24), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 18), Brick.HEIGHT * (ROWS - PADDING - i)));
        }

        for (int i = COLUMNS - PADDING - 24; i < COLUMNS - PADDING - 17; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (ROWS - PADDING - 10)));


        for (int i = ROWS - PADDING - 16; i < ROWS - PADDING - 11; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 12), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 6), Brick.HEIGHT * (ROWS - PADDING - i)));
        }


        for (int i = COLUMNS - PADDING - 12; i < COLUMNS - PADDING - 5; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (ROWS - PADDING - 10)));


        // Bottom line
        for (int i = COLUMNS - PADDING - 20; i < COLUMNS - PADDING - 8; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (ROWS - PADDING - 6)));


        level.getEnemies().add(new Drunk((PADDING + 6) * Brick.WIDTH, (ROWS - PADDING - 4) * Brick.HEIGHT));
        level.getEnemies().add(new Drunk((PADDING + 16) * Brick.WIDTH, (ROWS - PADDING - 4) * Brick.HEIGHT));
        level.getEnemies().add(new Zenchan((PADDING + 8) * Brick.WIDTH, (ROWS - PADDING - 12) * Brick.HEIGHT));
        level.getEnemies().add(new Zenchan((PADDING + 18) * Brick.WIDTH, (ROWS - PADDING - 12) * Brick.HEIGHT));
    }

    /**
     * Draw level fifteen by adding bricks and add enemies
     */
    private void drawFifteen() {
        for (int i = PADDING + 2; i < COLUMNS - PADDING - 2; i += 3) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (PADDING + 10)));
        }

        for (int i = PADDING + 3; i < COLUMNS - PADDING - 3; i += 3) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (PADDING + 14)));
        }

        for (int i = PADDING + 2; i < COLUMNS - PADDING - 2; i += 3) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (PADDING + 18)));
        }

        level.getEnemies().add(new Monsta((PADDING + 18) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
        level.getEnemies().add(new Monsta((PADDING + 16) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
        level.getEnemies().add(new Monsta((PADDING + 14) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
        level.getEnemies().add(new Monsta((PADDING + 12) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul((PADDING + 10) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul((PADDING + 8) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
        level.getEnemies().add(new Pulpul((PADDING + 6) * Brick.WIDTH, (PADDING + 12) * Brick.HEIGHT));
    }

    /**
     * Draw level sixteen by adding bricks and add enemies
     */
    private void drawSixteen() {
        for (int i = ROWS - PADDING - 20; i < ROWS - PADDING - 13; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 24), Brick.HEIGHT * (ROWS - PADDING - i)));
            level.addBrick(new Brick(Brick.WIDTH * (COLUMNS - PADDING - 6), Brick.HEIGHT * (ROWS - PADDING - i)));
        }

        for (int i = COLUMNS - PADDING - 24; i < COLUMNS - PADDING - 5; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (ROWS - PADDING - 7)));

        level.getEnemies().add(new Mighta((COLUMNS - PADDING - 4) * Brick.WIDTH, (PADDING + 4) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 4) * Brick.WIDTH, (PADDING + 8) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((COLUMNS - PADDING - 4) * Brick.WIDTH, (PADDING + 8) * Brick.HEIGHT));
        level.getEnemies().add(new Mighta((PADDING + 4) * Brick.WIDTH, (PADDING + 4) * Brick.HEIGHT));
        level.getEnemies().add(new Drunk((PADDING + 12) * Brick.WIDTH, (PADDING + 10) * Brick.HEIGHT));
    }
}
