package view.components.title;

import view.View;
import view.components.Logo;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    private BackPanel backPanel;

    private Logo logo;
    private LeaderboardButtonPanel leaderboardButtonPanel;

    public TitlePanel(boolean leaderboard) {
        backPanel = new BackPanel();
        logo = new Logo();

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        backPanel.setPreferredSize(new Dimension(View.WINDOWS_WIDTH * 20 / 100, View.WINDOWS_HEIGHT));

        if (leaderboard) {
            leaderboardButtonPanel = new LeaderboardButtonPanel();
            leaderboardButtonPanel.setPreferredSize(new Dimension(View.WINDOWS_WIDTH * 20 / 100, View.WINDOWS_HEIGHT));
            add(leaderboardButtonPanel, BorderLayout.EAST);

        } else {
            JLabel fill = new JLabel();
            fill.setPreferredSize(new Dimension(View.WINDOWS_WIDTH * 20 / 100, View.WINDOWS_HEIGHT));
            add(fill, BorderLayout.EAST);
        }

        add(backPanel, BorderLayout.WEST);
        add(logo, BorderLayout.CENTER);
    }

    public TitlePanel() {
        this(true);
    }

    public BackPanel getBackPanel() {
        return backPanel;
    }

    public LeaderboardButtonPanel getLeaderboardButtonPanel() {
        return leaderboardButtonPanel;
    }

    public JLabel getLogo() {
        return logo;
    }

}
