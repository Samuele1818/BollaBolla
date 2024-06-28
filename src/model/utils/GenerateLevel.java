package model.utils;

import model.Level;
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
        switch (level) {
            case 1 -> drawOne();
            case 2 -> drawTwo();
            case 3 -> drawThree();
            case 4 -> drawFour();
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
    }

    private void drawTwo() {
        int h_first = 4 * Brick.HEIGHT;

        level.addBrick(new Brick(Brick.WIDTH, h_first));
        level.addBrick(new Brick((COLUMNS - 1) * Brick.WIDTH, h_first));

        for (int i = 0; i < 2; i++) {

        }
    }

    private void drawThree() {
        // Implement the brick pattern for level 3
        for (int j = 2; j < 30; j++) {
            level.addBrick(new Brick(j * Brick.WIDTH, 2 * Brick.HEIGHT));
            level.addBrick(new Brick(j * Brick.WIDTH, 26 * Brick.HEIGHT));
        }
        for (int i = 4; i < 25; i++) {
            level.addBrick(new Brick(2 * Brick.WIDTH, i * Brick.HEIGHT));
            level.addBrick(new Brick(29 * Brick.WIDTH, i * Brick.HEIGHT));
        }
    }

    private void drawFour() {
        // Implement the brick pattern for level 4
        for (int j = 0; j < 32; j++) {
            level.addBrick(new Brick(j * Brick.WIDTH, 0));
            level.addBrick(new Brick(j * Brick.WIDTH, 28 * Brick.HEIGHT));
        }
        for (int i = 2; i < 27; i++) {
            level.addBrick(new Brick(0, i * Brick.HEIGHT));
            level.addBrick(new Brick(31 * Brick.WIDTH, i * Brick.HEIGHT));
        }
    }
}
