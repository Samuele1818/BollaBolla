package view.components.title;


import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class BackPanel extends JPanel {
    private final Button backButton;

    public BackPanel() {
        backButton = new Button.ButtonBuilder("Menu").setWidth(70).setHeight(20).build();
        setBackground(Color.BLACK);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(backButton);
    }

    public Button getBackButton() {
        return backButton;
    }
}
