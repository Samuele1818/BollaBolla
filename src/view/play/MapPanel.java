package view.play;

import model.Level;
import model.entity.monster.Monster;
import model.entity.objects.Brick;
import model.entity.objects.consumable.Loot;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.EffectBubble;
import model.entity.objects.consumable.bubble.NormalBubble;
import model.utils.FileManager;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public class MapPanel extends JPanel implements Observer {
    private java.awt.Image mainCharacterImage;
    private Level level;

    /**
     * MapPanel constructor
     */
    public MapPanel() {
        setFocusable(true);
        requestFocus();

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(View.WINDOWS_WIDTH, View.WINDOWS_HEIGHT));
        setDoubleBuffered(true);
    }

    /**
     * Subscribe to observable
     *
     * @param level observable class to subscribe to
     */
    public void init(Level level) {
        level.addObserver(this);

        // Set cursor transparent in this panel
        setCursor(getToolkit().createCustomCursor(getToolkit().getImage(""), new Point(), "transparent"));
    }

    /**
     * Draw grid (bricks)
     * Draw enemies, main character, score, bubbles (normal and effect), loots, powerUps and show enemies dead
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawMainCharacter(g);
        drawEnemies(g);
        drawStatistics(g);
        drawNormalBubble(g);
        drawKilledEnemies(g);
        drawLoot(g);
        drawPowerUp(g);
        drawEffectBubble(g);
    }

    /**
     * Draw every effect bubble in the list
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawEffectBubble(Graphics g) {
        CopyOnWriteArrayList<EffectBubble> bubbles = new CopyOnWriteArrayList<>(level.getEffectBubbles());
        for (EffectBubble bubble : bubbles) {
            java.awt.Image image = new ImageIcon(bubble.getImagesFolder() + File.separator + bubble.getCharacterPath()).getImage();
            g.drawImage(image, bubble.getX(), bubble.getY(), PowerUp.WIDTH, PowerUp.HEIGHT, this);
        }
    }

    /**
     * Draw every poweUps in the list
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawPowerUp(Graphics g) {
        CopyOnWriteArrayList<PowerUp> powerUps = new CopyOnWriteArrayList<>(level.getPowerUps());
        for (PowerUp powerUp : powerUps) {

            java.awt.Image image = new ImageIcon(powerUp.getImagesFolder() + File.separator + powerUp.getCharacterPath()).getImage();
            g.drawImage(image, powerUp.getX(), powerUp.getY(), PowerUp.WIDTH, PowerUp.HEIGHT, this);
        }
    }

    /**
     * Draw every loot in the array
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawLoot(Graphics g) {
        CopyOnWriteArrayList<Loot> loots = new CopyOnWriteArrayList<>(level.getLoots());
        for (Loot loot : loots) {
            java.awt.Image image = new ImageIcon(loot.getImagesFolder() + File.separator + loot.getCharacterPath()).getImage();
            g.drawImage(image, loot.getX(), loot.getY(), Loot.WIDTH, Loot.HEIGHT, this);
        }
    }

    /**
     * Draw enemies on dead
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawKilledEnemies(Graphics g) {
        CopyOnWriteArrayList<Monster> monsters = new CopyOnWriteArrayList<>(level.getKilledEnemies());

        for (Monster monster : monsters) {
            java.awt.Image image = new ImageIcon(monster.getImagesFolder() + File.separator + monster.getCharacterPath()).getImage();

            g.drawImage(image, monster.getX(), monster.getY(), Monster.WIDTH, Monster.HEIGHT, this);
        }
    }

    /**
     * Draw score and health numbers
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawStatistics(Graphics g) {
        int l = level.getMainCharacter().getHealth();
        java.awt.Image image = new ImageIcon(FileManager.getResource("static_image", "number", l + ".png")).getImage();

        g.drawImage(image, 96, (Level.ROWS * 16) - 25, 16, 16, this);


        int score = level.getScore();
        StringBuilder s = new StringBuilder(Integer.toString(score));
        while (s.length() < 9) {
            s.insert(0, "-");
        }
        while (s.length() > 9) {
            s = new StringBuilder(s.substring(0, s.length() - 1));
        }

        for (int i = 0; i < s.length(); i++) {
            image = new ImageIcon(FileManager.getResource("static_image", "number", s.charAt(i) + ".png")).getImage();
            g.drawImage(image, 48 + 16 * i, 16, 16, 16, this);
        }
    }

    /**
     * Draw every enemy in the list
     *
     * @param g the <code>Graphics</code> object to protect
     */
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

    /**
     * Draw grid (bricks)
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawGrid(Graphics g) {
        String imagePath = level.getBricksImage();
        CopyOnWriteArrayList<Brick> bricks = new CopyOnWriteArrayList<>(level.getBricks());
        for (Brick brick : bricks) {
            java.awt.Image image = new ImageIcon(imagePath).getImage();
            g.drawImage(
                    image,
                    brick.getX(),
                    brick.getY(),
                    this
            );
        }
    }

    /**
     * Draw main character
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawMainCharacter(Graphics g) {
        String folderImage = level.getMainCharacter().getImagesFolder() + File.separator + level.getMainCharacter().getCharacterPath();

        mainCharacterImage = new ImageIcon(folderImage).getImage();

        g.drawImage(
                mainCharacterImage,
                level.getMainCharacter().getX(),
                level.getMainCharacter().getY(),
                level.getMainCharacter().getWidth(),
                level.getMainCharacter().getHeight(),
                this
        );
    }

    /**
     * Draw every normal bubble in the list
     *
     * @param g the <code>Graphics</code> object to protect
     */
    private void drawNormalBubble(Graphics g) {
        String folder = switch (level.getMainCharacter().getType()) {
            case BOB -> FileManager.getResource("animation", "bob", "normal_bubble");
            case BUB -> FileManager.getResource("animation", "bub", "normal_bubble");
            default -> "resources\\animation\\normal_bub";
        };

        CopyOnWriteArrayList<NormalBubble> bubbles = new CopyOnWriteArrayList<>(level.getBubbles());

        for (NormalBubble bubble : bubbles) {
            java.awt.Image bubbleImage = new ImageIcon(folder + File.separator + bubble.getCharacterPath()).getImage();
            g.drawImage(bubbleImage, bubble.getX(), bubble.getY(), bubble.getWidth(), bubble.getHeight(), this);
        }
    }

    /**
     * Repaint the map when level change
     *
     * @param o   the observable object.
     * @param arg an argument passed to the {@code notifyObservers}
     *            method.
     */
    @Override
    public void update(Observable o, Object arg) {
        this.level = (Level) arg;

        invalidate();
        revalidate();
        repaint();
    }

    /**
     * Return mainCharacter image
     *
     * @return mainCharacter image
     */
    public Image getMainCharacterImage() {
        return mainCharacterImage;
    }
}
