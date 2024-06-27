package view.components;

import javax.swing.*;
import java.awt.*;

public class Text extends JLabel {
    public static final int DEFAULT_SIZE = 16;
    public static String FONT_FAMILY = "Futura";
    public static int DEFAULT_STYLE = Font.PLAIN;
    private boolean avoidRepaint;

    public Text(String text) {
        this(text, DEFAULT_SIZE);
    }

    public Text(String text, int size) {
        setFont(new Font(FONT_FAMILY, DEFAULT_STYLE, size));
        setText(text);
    }

    public Text(String text, boolean avoidRepaint) {
        setText(text);
        this.avoidRepaint = avoidRepaint;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(avoidRepaint) return;

        setForeground(UIManager.getColor("Label.foreground"));
    }
}
