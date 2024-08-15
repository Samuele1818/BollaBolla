package view.components.title;


import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class LeaderboardButtonPanel extends JPanel {
    private Button leaderboardButton;

    /**
     * LeaderboardButtonPanel constructor
     * Show leaderboard button
     */
    public LeaderboardButtonPanel() {
        leaderboardButton = new Button.ButtonBuilder("Leader").setWidth(100).setHeight(20).build();
        setBackground(Color.BLACK);

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        add(leaderboardButton);
    }

    /**
     * Get leader button panel
     *
     * @return leader button panel
     */
    public Button getLeaderboardButton() {
        return leaderboardButton;
    }
}