package controller;

import model.Level;
import model.Model;
import model.entity.monster.Character;
import view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PauseController implements ActionListener, KeyListener {
    public static PauseController instance = null;
    private Model model;
    private View view;

    /**
     * MenuController constructor
     * Init View and Model
     */
    public PauseController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    /**
     * Get PauseController instance
     *
     * @return PauseController instance
     */
    public static PauseController getInstance() {
        if (instance == null) instance = new PauseController();
        return instance;
    }

    /**
     * Setup action listener and key listener
     */
    public void init() {
        setActionListener();
        view.getPausePanel().addKeyListener(this);
    }

    /**
     * Register listeners:
     * Menu listener
     * Resume listener
     */
    public void setActionListener() {
        view.getPausePanel().resumeButtonPanel().getResumeButton().addActionListener(this);
        view.getPausePanel().getTitlePanel().getMenuButtonPanel().getMenuButton().addActionListener(this);
    }

    /**
     * Handle Resume and Menu buttons click
     * Resume -> Restart gameLoop and switch to play panel
     * Menu -> Return to Menu and stop the play
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Resume")) restartGameLoop();
        if (e.getActionCommand().equals("Menu")) {
            quitGame();
            view.changePanel(View.Screen.MENU);
        }
    }

    /**
     * Nothing
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Nothing
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * ENTER click -> Restart gameLoop and switch to play panel
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) restartGameLoop();
    }

    /**
     * Return to menu panel, end game and restart level
     */
    public void quitGame() {
        view.changePanel(View.Screen.MENU);
        GameController.getInstance().setGameEnded(true);

        model.getLevel().setScore(0);
        model.getLevel().resetLevel(Character.HEALTH, Level.START_LEVEL, null);
    }

    /**
     * Restart gameLoop and switch to play panel
     */
    public void restartGameLoop() {
        GameController.getInstance().setPause(false);
        view.changePanel(View.Screen.PLAY);
    }
}
