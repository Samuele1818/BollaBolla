package view.menu;

import view.View;
import view.components.Image;
import view.components.Logo;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public static final String BOB_BUB_PATH = "./resources/static_image/bob_bub.gif";
    public static final int BOB_BUB_WIDTH = 165, BOB_BUB_HEIGHT = 105;

    private SelectorPanel selectorPanel;

    /**
     * MenuPanel constructor
     * Show selector panel
     * Show animated gif of bob and bub
     */
    public MenuPanel() {
        // background setting
        setBackground(Color.BLACK);

        // set Layout
        setLayout(new BorderLayout());

        setPreferredSize(new Dimension(View.WINDOWS_WIDTH, View.WINDOWS_HEIGHT));

        Logo logo = new Logo();
        add(logo, BorderLayout.NORTH);

        selectorPanel = new SelectorPanel();
        add(selectorPanel, BorderLayout.CENTER);

        Image bobBubImage = new Image(BOB_BUB_WIDTH, BOB_BUB_HEIGHT, BOB_BUB_PATH, JLabel.CENTER);
        add(bobBubImage, BorderLayout.SOUTH);
    }

    /**
     * Get selector panel
     *
     * @return selector panel
     */
    public SelectorPanel getSelectorPanel() {
        return selectorPanel;
    }
}
