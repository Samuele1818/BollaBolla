package view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Logo extends view.components.Image {

    private final static String PATH = "./resources/static_image/title.png";

    public Logo() {
        this(200, 120);
    }

    public Logo(int width, int height) {
        super(width, height, PATH, JLabel.CENTER);

        // background setting
        setBackground(Color.BLACK);

        // Set padding on top
        setBorder(new EmptyBorder(20, 0, 0, 0));
    }
}
