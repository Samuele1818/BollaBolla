package view.profile.profile_parts;

import view.profile.profile_panel.*;

import javax.swing.*;
import java.awt.*;

public class ProfileCenterPanel extends JPanel {

    private final AvatarPanel avatarPanel;
    private final UserPanel userPanel;
    private final LoadPanel loadPanel;
    private final BobPanel bobPanel;
    private final BubPanel bubPanel;

    private final JPanel firstRowPanel;
    private final JPanel secondRowPanel;

    public ProfileCenterPanel() {
        setBackground(Color.BLACK);
        setLayout(new GridLayout(2, 1));

        firstRowPanel = new JPanel();
        secondRowPanel = new JPanel();

        firstRowPanel.setBackground(Color.BLACK);
        secondRowPanel.setBackground(Color.BLACK);

        firstRowPanel.setLayout(new GridLayout(1, 5));

        avatarPanel = new AvatarPanel();
        userPanel = new UserPanel();
        loadPanel = new LoadPanel();

        //set panelFirst
        firstRowPanel.add(new JLabel());
        firstRowPanel.add(avatarPanel);
        firstRowPanel.add(userPanel);
        firstRowPanel.add(loadPanel);
        firstRowPanel.add(new JLabel());

        //set panelSecond
        secondRowPanel.setLayout(new GridLayout(1, 4));
        bobPanel = new BobPanel();
        bubPanel = new BubPanel();

        secondRowPanel.add(new JLabel());
        secondRowPanel.add(bobPanel);

        secondRowPanel.add(bubPanel);
        secondRowPanel.add(new JLabel());

        add(firstRowPanel);
        add(secondRowPanel);
    }


    public AvatarPanel getAvatarPanel() {
        return avatarPanel;
    }

    public UserPanel getUserPanel() {
        return userPanel;
    }

    public LoadPanel getLoadPanel() {
        return loadPanel;
    }

    public BobPanel getBobPanel() {
        return bobPanel;
    }

    public BubPanel getBubPanel() {
        return bubPanel;
    }
}
