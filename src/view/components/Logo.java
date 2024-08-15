package view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Logo extends view.components.Image {
    private final static int WIDTH = 200, HEIGHT = 120;
    private final static String PATH = "./resources/static_image/title.png";

    /**
     * Logo constructor with default size
     */
    public Logo() {
        this(WIDTH, HEIGHT);
    }

    /**
     * Logo constructor
     *
     * @param width  logo width
     * @param height logo height
     */
    public Logo(int width, int height) {
        super(width, height, PATH, JLabel.CENTER);

        // background setting
        setBackground(Color.BLACK);

        // Set padding on top
        setBorder(new EmptyBorder(20, 0, 0, 0));
    }
}
