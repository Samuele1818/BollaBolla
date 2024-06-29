package controller;

import model.Model;
import model.entity.Monster;
import model.entity.monster.Character;
import model.play.Game;
import model.play.Player;
import view.View;
import view.profile.profile_parts.ProfileCenterPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;

public class ProfileController implements ActionListener {
    private static ProfileController instance = null;
    private final Model model;
    private final View view;

    private ProfileController() {
        model = Model.getInstance();
        view = View.getInstance();
    }

    public static ProfileController getInstance() {
        if (instance == null) instance = new ProfileController();
        return instance;
    }

    public void init() {
        view.getProfilePanel().
                getProfileCenterPanel().
                getUserPanel().
                getUserTextField().
                setText(MainController.getInstance().getModel().getPlayer().getName());

        setActionListener();

        view.getProfilePanel().getGameRecordsPanel().init(model.getPlayer().getRegister());
    }

    private void setActionListener() {
        //TODO: Cercare un implementazione migliore
        //loadButton
        view.getProfilePanel().getProfileCenterPanel().getLoadPanel().getLoadButton().addActionListener(this);

        //bobButton
        view.getProfilePanel().getProfileCenterPanel().getBobPanel().getBobButton().addActionListener(this);

        //bubButton
        view.getProfilePanel().getProfileCenterPanel().getBubPanel().getBubButton().addActionListener(this);

        //backButton
        view.getProfilePanel().getProfileTitlePanel().getBackPanel().getBackButton().addActionListener(this);

        view.getProfilePanel().getProfileTitlePanel().getLeaderboardButtonPanel().getLeaderboardButton().addActionListener(this);

        view.getProfilePanel().getProfileCenterPanel().getAvatarPanel().
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

    @Override
    public void actionPerformed(ActionEvent e) {

        ProfileCenterPanel profileCenter = view.getProfilePanel().getProfileCenterPanel();

        switch (e.getActionCommand()) {
            case "Menu" -> view.changePanel(View.Screen.MENU);

            case "Leaderboard" -> {
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
                model.getLevel().getMainCharacter().changeCharacterType(Character.Type.BOB);
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
                model.getLevel().getMainCharacter().changeCharacterType(Character.Type.BUB);
            }
        }
    }
}
