package controller;

import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderboardController implements ActionListener {
    private static LeaderboardController instance = null;

    View view;

    private LeaderboardController() {
        view = View.getInstance();
    }

    public static LeaderboardController getInstance() {
        if (instance == null) instance = new LeaderboardController();
        return instance;
    }

    public void init() {
        view.getLeaderboardPanel().getTitlePanel().getBackPanel().getBackButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Menu")) {
            view.changePanel(View.Screen.MENU);
        }
    }
}
