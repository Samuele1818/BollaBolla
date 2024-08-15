package view.profile;

import model.play.Player;
import view.View;
import view.components.title.TitlePanel;
import view.profile.profile_panel.GameRecordsPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ProfilePanel extends JPanel implements Observer {
    private TitlePanel profileTitlePanel;
    private ProfileMainPanel profileMainPanel;
    private GameRecordsPanel gameRecordsPanel;

    /**
     * ProfilePanel constructor
     */
    public ProfilePanel() {
        setBackground(Color.BLACK);

        // set Layout
        setPreferredSize(new Dimension(View.WINDOWS_WIDTH, View.WINDOWS_HEIGHT));
        setLayout(new GridLayout(3, 1));

        profileTitlePanel = new TitlePanel();
        add(profileTitlePanel);

        profileMainPanel = new ProfileMainPanel();
        add(profileMainPanel);

        gameRecordsPanel = new GameRecordsPanel();
        add(gameRecordsPanel);
    }

    /**
     * Subscribe to observable
     *
     * @param player observable class to subscribe to
     */
    public void init(Player player) {
        player.addObserver(this);
    }

    /**
     * Update avatar and username when player change
     *
     * @param o   the observable object.
     * @param arg an argument passed to the {@code notifyObservers}
     *            method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Player p) {
            getProfileMainPanel().getAvatarPanel().setUserImage(p.getAvatar());
            getProfileMainPanel().getUserPanel().getUserTextField().setText(p.getName());
        }
    }

    /**
     * Get profile title panel
     *
     * @return profile title panel
     */
    public TitlePanel getProfileTitlePanel() {
        return profileTitlePanel;
    }

    /**
     * Get profile main panel
     *
     * @return profile main panel
     */
    public ProfileMainPanel getProfileMainPanel() {
        return profileMainPanel;
    }

    /**
     * Get game records panel
     *
     * @return game records panel
     */
    public GameRecordsPanel getGameRecordsPanel() {
        return gameRecordsPanel;
    }
}