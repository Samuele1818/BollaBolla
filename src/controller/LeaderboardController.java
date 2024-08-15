package controller;

import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderboardController implements ActionListener {
    private static LeaderboardController instance = null;

    private View view;

    /**
     * LeaderboardController constructor
     * Init View
     */
    private LeaderboardController() {
        view = View.getInstance();
    }

    /**
     * Get LeaderboardController instance
     *
     * @return LeaderboardController instance
     */
    public static LeaderboardController getInstance() {
        if (instance == null) instance = new LeaderboardController();
        return instance;
    }

    /**
     * Setup action listeners:
     * Menu button listener
     */
    public void init() {
        view.getLeaderboardPanel().getTitlePanel().getMenuButtonPanel().getMenuButton().addActionListener(this);
    }

    /**
     * Handle MENU button click
     * Menu -> Return to menu panel
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Menu")) {
            view.changePanel(View.Screen.MENU);
        }
    }
}
