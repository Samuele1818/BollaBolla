package view.profile;

import view.profile.profile_panel.*;

import javax.swing.*;
import java.awt.*;

public class ProfileMainPanel extends JPanel {
    private AvatarPanel avatarPanel;
    private UserPanel userPanel;
    private LoadButtonPanel loadButtonPanel;
    private BobButtonPanel bobButtonPanel;
    private BubButtonPanel bubButtonPanel;

    /**
     * ProfileMainPanel constructor
     * Show user avatar, username text field, bob and bub panel and load button
     */
    public ProfileMainPanel() {
        setBackground(Color.BLACK);
        setLayout(new GridLayout(2, 1));

        JPanel firstRowPanel = new JPanel();
        JPanel secondRowPanel = new JPanel();

        firstRowPanel.setBackground(Color.BLACK);
        secondRowPanel.setBackground(Color.BLACK);

        firstRowPanel.setLayout(new GridLayout(1, 5));

        avatarPanel = new AvatarPanel();
        userPanel = new UserPanel();
        loadButtonPanel = new LoadButtonPanel();

        //set panelFirst
        firstRowPanel.add(new JLabel());
        firstRowPanel.add(avatarPanel);
        firstRowPanel.add(userPanel);
        firstRowPanel.add(loadButtonPanel);
        firstRowPanel.add(new JLabel());

        //set panelSecond
        secondRowPanel.setLayout(new GridLayout(1, 4));
        bobButtonPanel = new BobButtonPanel();
        bubButtonPanel = new BubButtonPanel();

        secondRowPanel.add(new JLabel());
        secondRowPanel.add(bobButtonPanel);

        secondRowPanel.add(bubButtonPanel);
        secondRowPanel.add(new JLabel());

        add(firstRowPanel);
        add(secondRowPanel);
    }

    /**
     * Get user image (avatar) panel
     *
     * @return user image (avatar) panel
     */
    public AvatarPanel getAvatarPanel() {
        return avatarPanel;
    }

    /**
     * Get username (username text field) panel
     *
     * @return username (username text field) panel
     */
    public UserPanel getUserPanel() {
        return userPanel;
    }

    /**
     * Get load button panel
     *
     * @return load button panel
     */
    public LoadButtonPanel getLoadButtonPanel() {
        return loadButtonPanel;
    }

    /**
     * Get bob button panel
     *
     * @return bob button panel
     */
    public BobButtonPanel getBobButtonPanel() {
        return bobButtonPanel;
    }

    /**
     * Get bub button panel
     *
     * @return bub button panel
     */
    public BubButtonPanel getBubButtonPanel() {
        return bubButtonPanel;
    }
}
