package view.components;

import javax.swing.*;
import java.awt.*;

public class Text extends JLabel {
    public static final int DEFAULT_SIZE = 16;
    public static final String FONT_FAMILY = "Futura";
    public static final int DEFAULT_STYLE = Font.PLAIN;

    // avoid repaint ui when global theme change
    private boolean avoidRepaint;

    /**
     * Text constructor with font size and text content
     *
     * @param text text content
     * @param size font size
     */
    public Text(String text, int size) {
        setFont(new Font(FONT_FAMILY, DEFAULT_STYLE, size));
        setText(text);
    }

    /**
     * Text constructor with text content and avoid repaint
     *
     * @param text         text content
     * @param avoidRepaint avoid repaint ui when global theme change
     */
    public Text(String text, boolean avoidRepaint) {
        this(text, DEFAULT_SIZE);
        this.avoidRepaint = avoidRepaint;
    }

    /**
     * Repaint component using UIManger values
     * Avoid repaint the ui if <code>avoidRepaint</code>
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (avoidRepaint) return;

        setForeground(UIManager.getColor("Label.foreground"));
    }
}
