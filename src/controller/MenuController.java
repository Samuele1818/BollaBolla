package controller;

import model.Level;
import model.Model;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ActionListener {
    private static MenuController instance = null;

    private Model model;
    private View view;

    /**
     * MenuController constructor
     * Init View and Model
     */
    private MenuController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    /**
     * Get MenuController instance
     *
     * @return MenuController instance
     */
    public static MenuController getInstance() {
        if (instance == null) instance = new MenuController();
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
     * Profile button listener
     * Play button listener
     */
    private void setActionListener() {
        view.getMenuPanel().getSelectorPanel().getProfileButton().addActionListener(this);
        view.getMenuPanel().getSelectorPanel().getPlayButton().addActionListener(this);
    }

    /**
     * Handle PROFILE and PLAY buttons click
     * Profile -> Open profile panel and init game register
     * Play -> Open game panel and init level, start gameLoop thread
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("PROFILE")) {
            view.changePanel(View.Screen.PROFILE);
            model.getPlayer().getRegister().init();
        }

        if (e.getActionCommand().equals("PLAY")) {
            model.getLevel().resetLevel(model.getLevel().getMainCharacter().getHealth(), Level.START_LEVEL, null);
            view.changePanel(View.Screen.PLAY);


            model.getLevel().getMainCharacter().changeType(model.getPlayer().getMainCharacter());

            Thread gameLoopThread = new Thread(() -> GameController.getInstance().gameLoop());
            gameLoopThread.start();
        }
    }
}
