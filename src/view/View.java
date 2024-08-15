package view;

import model.GlobalTheme;
import view.leaderboard.LeaderboardPanel;
import view.menu.MenuPanel;
import view.play.LosePanel;
import view.play.MapPanel;
import view.play.WinPanel;
import view.play.pause.PausePanel;
import view.profile.ProfilePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {
    public static final int WINDOWS_WIDTH = 512;
    public static final int WINDOWS_HEIGHT = 464;
    private static View instance = null;
    private MenuPanel menuPanel;
    private ProfilePanel profilePanel;
    private MapPanel mapPanel;
    private CardLayout cardLayout;
    private LosePanel losePanel;
    private WinPanel winPanel;
    private PausePanel pausePanel;
    private LeaderboardPanel leaderboardPanel;
    /**
     * View constructor
     */
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
        add(pausePanel, Screen.PAUSE.name());
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

    /**
     * Get View instance
     *
     * @return view instance
     */
    public static View getInstance() {
        if (instance == null) instance = new View();
        return instance;
    }

    /**
     * Subscribe to observable
     *
     * @param globalTheme observable class to subscribe to
     */
    public void init(GlobalTheme globalTheme) {
        globalTheme.addObserver(this);
    }

    /**
     * Change current panel using card layout
     *
     * @param panel new panel to show
     */
    public void changePanel(Screen panel) {
        cardLayout.show(this.getContentPane(), panel.name());
        switch (panel) {
            case MENU -> menuPanel.requestFocus();
            case PLAY -> mapPanel.requestFocus();
            case PROFILE -> profilePanel.requestFocus();
            case LOSE -> losePanel.requestFocus();
            case LEADERBOARD -> leaderboardPanel.requestFocus();
            case WIN -> winPanel.requestFocus();
            case PAUSE -> pausePanel.requestFocus();
        }
    }

    /**
     * Update the whole UI when global theme change to show new colors
     *
     * @param o   the observable object.
     * @param arg an argument passed to the {@code notifyObservers}
     *            method.
     */
    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.updateComponentTreeUI(this);
    }

    /**
     * Get menu panel
     *
     * @return menu panel
     */
    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    /**
     * Get profile panel
     *
     * @return profile panel
     */
    public ProfilePanel getProfilePanel() {
        return profilePanel;
    }

    /**
     * Get map panel
     *
     * @return map panel
     */
    public MapPanel getMapPanel() {
        return mapPanel;
    }

    /**
     * Get lose panel
     *
     * @return lose panel
     */
    public LosePanel getLosePanel() {
        return losePanel;
    }

    /**
     * Get leaderboard panel
     *
     * @return leaderboard panel
     */
    public LeaderboardPanel getLeaderboardPanel() {
        return leaderboardPanel;
    }

    /**
     * Get pause panel
     *
     * @return leaderboard panel
     */
    public PausePanel getPausePanel() {
        return pausePanel;
    }

    /**
     * Get win panel
     *
     * @return win panel
     */
    public WinPanel getWinPanel() {
        return winPanel;
    }

    /**
     * List of panels present in the game
     */
    public enum Screen {
        MENU, PROFILE, PLAY, LOSE, WIN, PAUSE, LEADERBOARD
    }
}
