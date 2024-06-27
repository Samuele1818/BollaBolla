package view.components.title;


import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class LeaderboardButtonPanel extends JPanel {
    private final Button leaderboardButton;

    public LeaderboardButtonPanel() {
        leaderboardButton = new Button.ButtonBuilder("Leaderboard").setWidth(100).setHeight(20).build();
        setBackground(Color.BLACK);

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        add(leaderboardButton);
    }

    public Button getLeaderboardButton() {
        return leaderboardButton;
    }
}