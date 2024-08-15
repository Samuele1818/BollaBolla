package view.components.title;

import view.View;
import view.components.Logo;

import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {
    private MenuButtonPanel menuButtonPanel;
    private LeaderboardButtonPanel leaderboardButtonPanel;

    /**
     * TitlePanel constructor
     *
     * @param leaderboard display leaderboard button or not
     */
    public TitlePanel(boolean leaderboard) {
        menuButtonPanel = new MenuButtonPanel();
        Logo LOGO = new Logo();

        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        menuButtonPanel.setPreferredSize(new Dimension(View.WINDOWS_WIDTH * 20 / 100, View.WINDOWS_HEIGHT));

        if (leaderboard) {
            leaderboardButtonPanel = new LeaderboardButtonPanel();
            leaderboardButtonPanel.setPreferredSize(new Dimension(View.WINDOWS_WIDTH * 20 / 100, View.WINDOWS_HEIGHT));
            add(leaderboardButtonPanel, BorderLayout.EAST);

        } else {
            JLabel fill = new JLabel();
            fill.setPreferredSize(new Dimension(View.WINDOWS_WIDTH * 20 / 100, View.WINDOWS_HEIGHT));
            add(fill, BorderLayout.EAST);
        }

        add(menuButtonPanel, BorderLayout.WEST);
        add(LOGO, BorderLayout.CENTER);
    }

    /**
     * TitlePanel constructor with visible leader button
     */
    public TitlePanel() {
        this(true);
    }

    /**
     * Get menu button panel
     *
     * @return menu button panel
     */
    public MenuButtonPanel getMenuButtonPanel() {
        return menuButtonPanel;
    }

    /**
     * Get leaderboard button panel
     *
     * @return leaderboard button panel
     */
    public LeaderboardButtonPanel getLeaderboardButtonPanel() {
        return leaderboardButtonPanel;
    }
}
