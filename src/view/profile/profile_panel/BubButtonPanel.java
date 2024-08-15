package view.profile.profile_panel;

import view.components.Button;
import view.components.Image;

import javax.swing.*;
import java.awt.*;

public class BubButtonPanel extends JPanel {
    public static final String BUB_GIF_PATH = "./resources/static_image/bub.gif";

    private Button bubButton;

    /**
     * BubPanel constructor
     */
    public BubButtonPanel() {
        setBackground(Color.BLACK);
        bubButton = new Button.ButtonBuilder("Bub: ").
                setCustomIcon(new Image(20, 20, BUB_GIF_PATH))
                .setWidth(90)
                .setHeight(35)
                .setAvoidRepaint(true)
                .build();
        bubButton.setHorizontalTextPosition(SwingConstants.LEFT);
        bubButton.setForeground(Color.CYAN);

        add(bubButton);
    }

    /**
     * Get bub button
     *
     * @return bub button
     */
    public Button getBubButton() {
        return bubButton;
    }
}
