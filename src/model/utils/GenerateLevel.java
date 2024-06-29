package model.utils;

import model.Level;
import model.entity.monster.enemies.*;
import model.entity.objects.Brick;
import model.files.FileManager;

import java.nio.file.Path;

import static model.Level.*;

public class GenerateLevel {

    private final Level level;

    public GenerateLevel(Level level) {
        this.level = level;
    }

    public void regenerateLevel(int level) {
        drawLevelBorder();

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

            case 9 -> drawFive();
            case 10 -> drawFive();
            case 11 -> drawFive();
            case 12 -> drawFive();

            case 13 -> drawFive();
            case 14 -> drawFive();
            case 15 -> drawFive();
            case 16 -> drawFive();
        }

        FileManager.serialize(this.level, String.valueOf(Path.of(LEVEL_FOLDER, String.valueOf(level))));
    }


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

        level.getEnemies().add(new Drunk((PADDING + 3) * Brick.WIDTH, 200));
        level.getEnemies().add(new ZenChan((PADDING + 3) * Brick.WIDTH, 70));
        level.getEnemies().add(new ZenChan((COLUMNS - PADDING - 5) * Brick.WIDTH, 70));
        level.getEnemies().add(new ZenChan((COLUMNS - PADDING - 5) * Brick.WIDTH, 200));
    }


    private void drawTwo() {
        for(int i = PADDING + 3; i < COLUMNS - PADDING - 3; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 10 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 14 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 18 - PADDING)));
        }

        level.getEnemies().add(new Pulpul((COLUMNS - PADDING - 5) * Brick.WIDTH +10, 200));
        level.getEnemies().add(new ZenChan((COLUMNS - PADDING - 5) * Brick.WIDTH +10, 150));
    }

    private void drawThree() {
        for(int i = PADDING + 1; i < COLUMNS - PADDING - 3; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 10 - PADDING)));
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 18 - PADDING)));
        }

        for(int i = PADDING + 4; i < COLUMNS - PADDING - 1; i++)
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 14 - PADDING)));

        level.getEnemies().add(new Pulpul((COLUMNS - PADDING - 12) * Brick.WIDTH, 250));
        level.getEnemies().add(new ZenChan((COLUMNS - PADDING - 5) * Brick.WIDTH, 150));
        level.getEnemies().add(new ZenChan((COLUMNS - PADDING - 5) * Brick.WIDTH, 200));
    }

    private void drawFour() {
        for(int i = PADDING + 3; i < COLUMNS - PADDING - 3; i++) {
            if(i == (PADDING + 3) || i == (COLUMNS - PADDING - 4)) {
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

    private void drawFive() {
        // Bottom line
        for (int i = 4; i < 12; i++) {
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), 336));
            level.addBrick(new Brick((COLUMNS - PADDING - i - 1) * Brick.WIDTH, 336));
        }

        // First separated lines
        for(int i = PADDING + 1; i < COLUMNS - PADDING - 22; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        for(int i = COLUMNS - PADDING - 22; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        // Second separated lines
        for(int i = PADDING + 1; i < COLUMNS - PADDING - 18; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        for(int i = COLUMNS - PADDING - 18; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        // Third separated lines
        for(int i = PADDING + 1; i < COLUMNS - PADDING - 14; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }

        for(int i = COLUMNS - PADDING - 14; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }

        level.getEnemies().add(new Invader((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        level.getEnemies().add(new Invader((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 17 - PADDING)));
        level.getEnemies().add(new Invader((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 22 - PADDING)));
        level.getEnemies().add(new ZenChan((COLUMNS - PADDING - 8) * Brick.WIDTH, Brick.HEIGHT * (COLUMNS - 22 - PADDING)));
    }

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

    private void drawSeven() {
        for(int i = COLUMNS + PADDING; i < COLUMNS + PADDING + 4; i++)
            level.addBrick(new Brick(Brick.WIDTH * (PADDING + i), Brick.HEIGHT * (COLUMNS - 12 - PADDING)));

        for(int i = ROWS + PADDING; i < ROWS + PADDING + 4; i++)
            level.addBrick(new Brick(Brick.WIDTH * PADDING + 5, Brick.HEIGHT * i));

        level.getEnemies().add(new ZenChan((PADDING + 3) * Brick.WIDTH, 200));
        level.getEnemies().add(new ZenChan((PADDING + 3) * Brick.WIDTH, 100));
        level.getEnemies().add(new Mighta((PADDING + 3) * Brick.WIDTH, 70));
    }

    private void drawEight() {
        // First separated lines
        for(int i = PADDING + 1; i < COLUMNS - PADDING - 22; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        for(int i = COLUMNS - PADDING - 22; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 20 - PADDING)));
        }

        // Second separated lines
        for(int i = PADDING + 1; i < COLUMNS - PADDING - 18; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        for(int i = COLUMNS - PADDING - 18; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 16 - PADDING)));
        }

        // Third separated lines
        for(int i = PADDING + 1; i < COLUMNS - PADDING - 14; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }

        for(int i = COLUMNS - PADDING - 14; i < COLUMNS - PADDING; i++) {
            level.addBrick(new Brick(Brick.WIDTH * i, Brick.HEIGHT * (COLUMNS - 12 - PADDING)));
        }
    }
}
