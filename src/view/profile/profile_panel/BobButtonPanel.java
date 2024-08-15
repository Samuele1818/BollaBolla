package view.profile.profile_panel;

import view.components.Button;
import view.components.Image;

import javax.swing.*;
import java.awt.*;

public class BobButtonPanel extends JPanel {
    public static final String BOB_GIF_PATH = "./resources/static_image/bob.gif";
    private Button bobButton;

    /**
     * AvatarPanel constructor
     */
    public BobButtonPanel() {
        setBackground(Color.BLACK);

        bobButton = new Button.ButtonBuilder("Bob: ")
                .setCustomIcon(new Image(20, 20, BOB_GIF_PATH))
                .setWidth(90)
                .setHeight(35)
                .setAvoidRepaint(true)
                .build();

        //  new Button("Bob: ", , 90, 35, true)
        bobButton.setHorizontalTextPosition(SwingConstants.LEFT);
        bobButton.setForeground(Color.GREEN);


        add(bobButton);
    }

    /**
     * Get bob button
     *
     * @return bob button
     */
    public Button getBobButton() {
        return bobButton;
    }
}
