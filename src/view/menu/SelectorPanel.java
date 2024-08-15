package view.menu;

import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class SelectorPanel extends JPanel {
    private Button playButton;
    private Button profileButton;

    /**
     * SelectorPanel constructor
     * Show play and profile buttons
     */
    public SelectorPanel() {
        //setLayout
        setLayout(new GridLayout(3, 3));

        setBackground(Color.BLACK);

        // Add play Button
        playButton = new Button.ButtonBuilder("PLAY").build();

        // Add Profile Button
        profileButton = new Button.ButtonBuilder("PROFILE").build();

        // JPanel setup and then set the buttons to the appropriate size
        JPanel[][] panels = new JPanel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                panels[i][j] = new JPanel();
                panels[i][j].setBackground(Color.BLACK);
                add(panels[i][j]);
            }
        }

        panels[1][1].add(playButton);
        panels[2][1].add(profileButton);
    }

    /**
     * Get play button
     *
     * @return play button
     */
    public Button getPlayButton() {
        return playButton;
    }

    /**
     * Get profile button
     *
     * @return profile button
     */
    public Button getProfileButton() {
        return profileButton;
    }
}
