package controller;

import model.Model;
import view.View;
import view.play.PausePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PauseController implements ActionListener, KeyListener {
    public static PausePanel instance;
    private Model model;
    private View view;

    public PauseController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    public static PausePanel getInstance() {
        if (instance == null) instance = new PausePanel();
        return instance;
    }

    public void setActionListener() {
        view.getPausePanel().resumeButtonPanel().getResumeButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Resume")) restartGameLoop();
        else quitGame();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) restartGameLoop();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void quitGame() {
        view.changePanel(View.Screen.MENU);
        // TODO: Usare delle costanti invece che 3, 1 o fare un reset level che ha le costanti integrate
        model.getLevel().resetLevel(3, 1);
    }

    public void restartGameLoop() {
        view.changePanel(View.Screen.PLAY);
        // TODO: Resume game loo
    }
}
