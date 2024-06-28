package view.profile.profile_panel;

import view.components.Button;
import view.components.Image;

import javax.swing.*;
import java.awt.*;

public class BobPanel extends JPanel {
    public static final String BOB_GIF_PATH = "./resources/static_image/bob.gif";
    private final Button bobButton;

    public BobPanel() {
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

    public Button getBobButton() {
        return bobButton;
    }
}
