package view.profile.profile_panel;

import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class LoadPanel extends JPanel {

    private final Button loadButton;


    public LoadPanel() {
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


    public Button getLoadButton() {
        return loadButton;
    }
}
