package view;

import model.GlobalTheme;
import model.play.Player;
import view.leaderboard.LeaderboardPanel;
import view.menu.MenuPanel;
import view.play.LosePanel;
import view.play.MapPanel;
import view.play.PausePanel;
import view.play.WinPanel;
import view.profile.ProfilePanel;
import view.profile.profile_parts.ProfileCenterPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    public static final int WINDOWS_WIDTH = 512; // 32*16
    public static final int WINDOWS_HEIGHT = 464; // 25 centrali + 2 a destra e a sinistra tutti neri
    private static View instance;
    private final MenuPanel menuPanel;
    private final ProfilePanel profilePanel;
    private final MapPanel mapPanel;
    private final CardLayout cardLayout;
    private final LosePanel losePanel;
    private final WinPanel winPanel;
    private final PausePanel pausePanel;
    private final LeaderboardPanel leaderboardPanel;

    private View() {
        super("Double Bobble");

        // Set the size of the panel
        getContentPane().setPreferredSize(new Dimension(WINDOWS_WIDTH, WINDOWS_HEIGHT));

        // Make the panel not resizable
        setResizable(false);

        // background setting
        getContentPane().setBackground(Color.BLACK);

        // Stop the process when user close the panel
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        menuPanel = new MenuPanel();
        profilePanel = new ProfilePanel();
        mapPanel = new MapPanel();
        losePanel = new LosePanel();
        winPanel = new WinPanel();
        pausePanel = new PausePanel();
        leaderboardPanel = new LeaderboardPanel();

        setLayout(cardLayout);

        add(menuPanel, Screen.MENU.name());
        add(profilePanel, Screen.PROFILE.name());
        add(mapPanel, Screen.PLAY.name());
        add(losePanel, Screen.LOSE.name());
        add(winPanel, Screen.WIN.name());
        add(pausePanel, Screen.PUASE.name());
        add(leaderboardPanel, Screen.LEADERBOARD.name());

        // Set MENU panel to default
        changePanel(Screen.MENU);

        // Make the frame dimension equal or above the maximum preferred size of components contained
        pack();

        // Set frame to the center of the screen on start
        setLocationRelativeTo(null);

        // Show the frame
        setVisible(true);
    }

    public static View getInstance() {
        if (instance == null) instance = new View();
        return instance;
    }

    public void init(GlobalTheme globalTheme, Player player) {
        globalTheme.addObserver(this);
        player.addObserver(this);
    }

    public void changePanel(Screen panel) {
        cardLayout.show(this.getContentPane(), panel.name());
        switch (panel) {
            case MENU -> menuPanel.requestFocus();
            case PLAY -> mapPanel.requestFocus();
            case PROFILE -> profilePanel.requestFocus();
            case LOSE -> losePanel.requestFocus();
            case LEADERBOARD -> leaderboardPanel.requestFocus();
        }
    }

    public void updateUIC() {
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Player) {
            Player p = (Player) arg;

            ProfileCenterPanel profileCenter = getProfilePanel().getProfileCenterPanel();
            profileCenter.getAvatarPanel().setUserImage(p.getAvatar());
            profileCenter.getUserPanel().getUserTextField().setText(p.getName());
        }

        updateUIC();
    }

    public MenuPanel getMenu() {
        return menuPanel;
    }

    public ProfilePanel getProfilePanel() {
        return profilePanel;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public LosePanel getLosePanel() {
        return losePanel;
    }

    public LeaderboardPanel getLeaderboardPanel() {
        return leaderboardPanel;
    }

    public PausePanel getPausePanel() {
        return pausePanel;
    }

    public enum Screen {
        MENU, PROFILE, PLAY, LOSE, WIN, PUASE, LEADERBOARD
    }
}
