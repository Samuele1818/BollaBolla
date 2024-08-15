package controller;

import model.Model;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultController implements ActionListener {

    private static ResultController instance = null;

    private Model model;
    private View view;

    /**
     * ResultController constructor
     * Init View and Model
     */
    private ResultController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    /**
     * Get ResultController instance
     *
     * @return ResultController instance
     */
    public static ResultController getInstance() {
        if (instance == null) instance = new ResultController();
        return instance;
    }

    /**
     * Setup action listener
     */
    public void init() {
        setActionListener();
    }

    /**
     * Register listeners:
     * Menu button listener  of win and lose panel
     * Leaderboard button listener of win and lose panel
     */
    private void setActionListener() {
        view.getLosePanel().getTitlePanel().getMenuButtonPanel().getMenuButton().addActionListener(this);
        view.getLosePanel().getTitlePanel().getLeaderboardButtonPanel().getLeaderboardButton().addActionListener(this);
        view.getWinPanel().getTitlePanel().getMenuButtonPanel().getMenuButton().addActionListener(this);
        view.getWinPanel().getTitlePanel().getLeaderboardButtonPanel().getLeaderboardButton().addActionListener(this);
    }

    /**
     * Handle MENU and LEADERBOARD buttons click
     * Menu -> Return to menu
     * Leaderboard -> Switch to leaderboard panel and init the leaderboard
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Menu"))
            view.changePanel(View.Screen.MENU);

        if (e.getActionCommand().equals("Leader")) {
            view.changePanel(View.Screen.LEADERBOARD);

            model.getLeaderboard().init();
        }
    }

}
