package controller;

import model.Model;
import model.entity.monster.Character;
import model.entity.monster.Monster;
import model.play.Player;
import view.View;
import view.profile.ProfileMainPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ProfileController implements ActionListener {
    private static ProfileController instance = null;
    private Model model;
    private View view;

    /**
     * ProfileController constructor
     * Init Model and View
     */
    private ProfileController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    /**
     * Get ProfileController instance
     *
     * @return ProfileController instance
     */
    public static ProfileController getInstance() {
        if (instance == null) instance = new ProfileController();
        return instance;
    }

    /**
     * Setup action listener
     */
    public void init() {
        view.getProfilePanel().
                getProfileMainPanel().
                getUserPanel().
                getUserTextField().
                setText(
                        MainController.
                                getInstance().
                                getModel().
                                getPlayer().
                                getName()
                );

        setActionListener();

    }

    /**
     * Register listeners:
     * Load button
     * Bob button
     * Bub button
     * Menu button
     * Leaderboard button
     * Avatar panel
     */
    private void setActionListener() {
        //loadButton
        view.getProfilePanel().getProfileMainPanel().getLoadButtonPanel().getLoadButton().addActionListener(this);

        //bobButton
        view.getProfilePanel().getProfileMainPanel().getBobButtonPanel().getBobButton().addActionListener(this);

        //bubButton
        view.getProfilePanel().getProfileMainPanel().getBubButtonPanel().getBubButton().addActionListener(this);

        //backButton
        view.getProfilePanel().getProfileTitlePanel().getMenuButtonPanel().getMenuButton().addActionListener(this);

        view.getProfilePanel().getProfileTitlePanel().getLeaderboardButtonPanel().getLeaderboardButton().addActionListener(this);

        view.getProfilePanel().getProfileMainPanel().getAvatarPanel().
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        int option = fileChooser.showOpenDialog(MainController.getInstance().getView());

                        FileNameExtensionFilter filter = new FileNameExtensionFilter("png jpeg gif", "png", "gif", "jpeg");
                        fileChooser.addChoosableFileFilter(filter);

                        if (option == JFileChooser.APPROVE_OPTION) {
                            File file = fileChooser.getSelectedFile();
                            MainController
                                    .getInstance()
                                    .getModel()
                                    .getPlayer()
                                    .changePlayer(null, null, file.getAbsolutePath(), null);
                        }
                    }
                });
    }

    /**
     * Handle MENU, LEADERBOARD, LOAD, BOB and BUB buttons click
     * Menu -> Return to MENU screen
     * Leaderboard -> Switch to Leaderboard screen and init leaderboard
     * Load -> Change player and save settings of the old
     * Bob ->  Set Bob as main character and change global theme
     * Bub ->  Set Bub as main character and change global theme
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        ProfileMainPanel profileCenter = view.getProfilePanel().getProfileMainPanel();

        switch (e.getActionCommand()) {
            case "Menu" -> view.changePanel(View.Screen.MENU);

            case "Leader" -> {
                view.changePanel(View.Screen.LEADERBOARD);

                model.getLeaderboard().init();
            }

            case "Load" -> {

                String filePath = profileCenter.getUserPanel().getUserTextField().getText();
                // Save current player data
                model.getPlayer().save();
                // Load new player and change it
                Player newPlayer = model.getPlayer().load(filePath);
                model.getPlayer().changePlayer(newPlayer);

                // Change global theme
                model.getGlobalTheme().setTheme(newPlayer.getMainCharacter());

            }

            case "Bob: " -> {
                // Change player with new theme and new nickname (save the new theme to the new user if nickname changed)
                model
                        .getPlayer()
                        .changePlayer(
                                profileCenter.getUserPanel().getUserTextField().getText(),
                                Monster.Type.BOB,
                                null,
                                null
                        );

                model.getGlobalTheme().setTheme(Character.Type.BOB);
                model.getLevel().getMainCharacter().changeType(Character.Type.BOB);
            }

            case "Bub: " -> {
                // Change player with new theme and new nickname (save the new theme to the new user if nickname changed)
                Player.getInstance().changePlayer(
                        profileCenter.getUserPanel().getUserTextField().getText(),
                        Monster.Type.BUB,
                        null,
                        null);

                // Change global theme
                model.getGlobalTheme().setTheme(Character.Type.BUB);

                // Change skin of character
                model.getLevel().getMainCharacter().changeType(Character.Type.BUB);
            }
        }
    }
}
