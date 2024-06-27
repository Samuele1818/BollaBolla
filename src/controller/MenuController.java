package controller;


import model.Model;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ActionListener {
    private static MenuController instance;

    private Model model;
    private View view;

    private MenuController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    public static MenuController getInstance() {
        if (instance == null) instance = new MenuController();
        return instance;
    }

    public void init() {
        setActionListener();

    }

    private void setActionListener() {
        view.getMenu().getSelectorPanel().getProfileButton().addActionListener(this);

        view.getMenu().getSelectorPanel().getPlayButton().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("PROFILE")) {
            view.changePanel(View.Screen.PROFILE);
        }

        if (e.getActionCommand().equals("PLAY")) {
            model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth(), 1);
            view.changePanel(View.Screen.PLAY);
            model.getLevel().getMainCharacter().changeCharacterType(model.getPlayer().getMainCharacter());

            Thread thread = new Thread() {
                @Override
                public void run() {
                    GameController.getInstance().GameLoop();
                }
            };
            thread.start();

        }
    }
}
