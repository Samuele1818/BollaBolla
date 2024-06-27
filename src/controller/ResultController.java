package controller;

import model.Model;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultController implements ActionListener {

    private static ResultController instance = null;

    private Model model;
    private View view;

    private ResultController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    public static ResultController getInstance() {
        if (instance == null) instance = new ResultController();
        return instance;
    }

    public void init() {
        setActionListener();
    }

    private void setActionListener() {
        view.getLosePanel().getTitlePanel().getBackPanel().getBackButton().addActionListener(this);
        view.getLosePanel().getTitlePanel().getLeaderboardButtonPanel().getLeaderboardButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Menu"))
            view.changePanel(View.Screen.MENU);
        if (e.getActionCommand().equals("Leaderboard")) {
            view.changePanel(View.Screen.LEADERBOARD);
        }
    }

}
