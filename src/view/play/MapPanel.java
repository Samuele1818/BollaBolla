package view.play;

import model.Level;
import model.entity.Monster;
import model.entity.objects.Brick;
import model.entity.objects.bubble.Bubble;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import model.files.FileManager;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapPanel extends JPanel implements Observer {
    private Level level;

    public MapPanel() {
        setFocusable(true);
        requestFocus();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(View.WINDOWS_WIDTH, View.WINDOWS_HEIGHT));
        setDoubleBuffered(true);
    }

    public void init(Level level) {
        level.addObserver(this);

        // Set cursor transparent in this panel
        setCursor(getToolkit().createCustomCursor(getToolkit().getImage(""), new Point(), "transparent"));
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawPlayer(g);
        drawEnemies(g);
        drawNumber(g);
        drawBubble(g);
        drawKilledEnemies(g);
        drawLoot(g);
        drawPowerUp(g);
    }

    private void drawPowerUp(Graphics g) {
        CopyOnWriteArrayList<PowerUp> powerUps = new CopyOnWriteArrayList<>(level.getPowerUps());
        for (PowerUp powerUp : powerUps) {
            java.awt.Image image = new ImageIcon(powerUp.getImagesFolder() + File.separator + powerUp.getCharacterPath()).getImage();
            g.drawImage(image, powerUp.getX(), powerUp.getY(), Monster.WIDTH, Monster.HEIGHT, this);
        }
    }

    private void drawLoot(Graphics g) {
        CopyOnWriteArrayList<Loot> loots = new CopyOnWriteArrayList<>(level.getLoots());
        for (Loot loot : loots) {
            java.awt.Image image = new ImageIcon(loot.getImagesFolder() + File.separator + loot.getCharacterPath()).getImage();
            g.drawImage(image, loot.getX(), loot.getY(), Monster.WIDTH, Monster.HEIGHT, this);
        }
    }

    private void drawKilledEnemies(Graphics g) {
        CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<>(level.getKilledEnemies());

        for (Monster monster : monsters) {
            java.awt.Image image = new ImageIcon(monster.getImagesFolder() + File.separator + monster.getCharacterPath()).getImage();

            g.drawImage(image, monster.getX(), monster.getY(), Monster.WIDTH, Monster.HEIGHT, this);
        }
    }

    private void drawNumber(Graphics g) {
        int l = level.getMainCharacter().getHealth();
        java.awt.Image image = new ImageIcon("./resources" + File.separator + "static_image" + File.separator + "number" + File.separator + l + ".png").getImage();

        g.drawImage(image, 96, (level.ROWS * 16) - 25, 16, 16, this);


        int score = level.getScore();
        StringBuilder s = new StringBuilder(Integer.toString(score));
        while (s.length() < 9) {
            s.insert(0, "-");
        }
        while (s.length() > 9) {
            s = new StringBuilder(s.substring(0, s.length() - 1));
        }

        for (int i = 0; i < s.length(); i++) {
            image = new ImageIcon("./resources" + File.separator + "static_image" + File.separator + "number" + File.separator + s.charAt(i) + ".png").getImage();
            g.drawImage(image, 48 + 16 * i, 16, 16, 16, this);
        }
    }

    private void drawEnemies(Graphics g) {
        CopyOnWriteArrayList<Monster> enemies = new CopyOnWriteArrayList<>(level.getEnemies());
        for (Monster monster : enemies) {
            java.awt.Image image = new ImageIcon(
                    monster.getImagesFolder()
                            + File.separator
                            + monster.getCharacterPath()
            ).getImage();

            g.drawImage(image, monster.getX(), monster.getY(), Monster.WIDTH, Monster.HEIGHT, this);
        }
    }

    private void drawGrid(Graphics g) {
        String imagePath = level.getBricksImage();
        for (Brick brick : level.getBricks()) {
            java.awt.Image image = new ImageIcon(imagePath).getImage();
            g.drawImage(
                    image,
                    brick.getX(),
                    brick.getY(),
                    this
            );
        }
    }

    private void drawPlayer(Graphics g) {
        String folderImage = level.getMainCharacter().getImagesFolder() + File.separator + level.getMainCharacter().getCharacterPath();

        java.awt.Image image = new ImageIcon(folderImage).getImage();

        g.drawImage(
                image,
                level.getMainCharacter().getX(),
                level.getMainCharacter().getY(),
                level.getMainCharacter().getWidth(),
                level.getMainCharacter().getHeight(),
                this
        );
    }

    private void drawBubble(Graphics g) {


        String folder = switch (level.getMainCharacter().getType()) {
            case BOB -> FileManager.getResource("animation", "bob", "normal_bubble");
            case BUB -> FileManager.getResource("animation", "bub", "normal_bubble");
            default -> "resources\\animation\\normal_bub";
        };


        CopyOnWriteArrayList<Bubble> bubbles = new CopyOnWriteArrayList<>(level.getBubbles());

        for (Bubble bubble : bubbles) {
            java.awt.Image bubbleImage = new ImageIcon(folder + File.separator + bubble.getCharacterPath()).getImage();

            g.drawImage(bubbleImage, bubble.getX(), bubble.getY(), bubble.getWidth(), bubble.getHeight(), this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.level = (Level) arg;

        invalidate();
        revalidate();
        repaint();
    }
}
