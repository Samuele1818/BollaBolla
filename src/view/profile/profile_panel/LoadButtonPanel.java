package view.profile.profile_panel;

import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class LoadButtonPanel extends JPanel {

    private Button loadButton;

    /**
     * LoadButtonPanel constructor
     */
    public LoadButtonPanel() {
        setBackground(Color.BLACK);
        setLayout(new GridLayout(3, 3));

        loadButton = new Button.ButtonBuilder("Load").setWidth(33).setHeight(33).build();
        loadButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(loadButton, BorderLayout.CENTER);
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
    }

    /**
     * Get load button
     *
     * @return load button
     */
    public Button getLoadButton() {
        return loadButton;
    }
}
