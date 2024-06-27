package view.profile;

import view.View;
import view.components.title.TitlePanel;
import view.profile.profile_panel.GameRecordsPanel;
import view.profile.profile_parts.ProfileCenterPanel;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private final TitlePanel profileTitlePanel;
    private final ProfileCenterPanel profileCenterPanel;
    private GameRecordsPanel gameRecordsPanel;

    public ProfilePanel() {
        setBackground(Color.BLACK);

        // set Layout

        setPreferredSize(new Dimension(View.WINDOWS_WIDTH, View.WINDOWS_HEIGHT));
        setLayout(new GridLayout(3, 1));

        profileTitlePanel = new TitlePanel();
        add(profileTitlePanel);

        profileCenterPanel = new ProfileCenterPanel();
        add(profileCenterPanel);

        gameRecordsPanel = new GameRecordsPanel();
        add(gameRecordsPanel);
    }

    public TitlePanel getProfileTitlePanel() {
        return profileTitlePanel;
    }

    public ProfileCenterPanel getProfileCenterPanel() {
        return profileCenterPanel;
    }

    public GameRecordsPanel getGameRecordsPanel() {
        return gameRecordsPanel;
    }
}