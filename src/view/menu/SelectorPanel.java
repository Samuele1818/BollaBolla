package view.menu;

import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class SelectorPanel extends JLabel {
    private Button playButton;
    private Button profileButton;
    private JPanel[][] panels;

    public SelectorPanel() {
        //setLayout
        setLayout(new GridLayout(3, 3));

        init();

        //TODO: Rivedere
        panels[1][1].add(playButton);
        panels[2][1].add(profileButton);
    }

    private void init() {
        // Add play Button
        playButton = new Button.ButtonBuilder("PLAY").build();

        // Add Profile Button
        profileButton = new Button.ButtonBuilder("PROFILE").build();

        // JPanel setup and then set the buttons to the appropriate size
        panels = new JPanel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panels[i][j] = new JPanel();
                panels[i][j].setBackground(Color.BLACK);
                add(panels[i][j]);
            }
        }
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getProfileButton() {
        return profileButton;
    }
}
