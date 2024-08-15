package view.components.title;


import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class MenuButtonPanel extends JPanel {
    private Button menuButton;

    /**
     * MenuButtonPanel constructor
     * Show menu button
     */
    public MenuButtonPanel() {
        menuButton = new Button.ButtonBuilder("Menu").setWidth(70).setHeight(20).build();
        setBackground(Color.BLACK);

        setLayout(new FlowLayout(FlowLayout.LEFT));

        add(menuButton);
    }

    /**
     * Get menu button
     *
     * @return menu button
     */
    public Button getMenuButton() {
        return menuButton;
    }
}
