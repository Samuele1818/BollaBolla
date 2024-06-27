package view.profile.profile_panel;

import model.utils.UsernameVerifier;
import view.components.EditableTextField;
import view.components.Text;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {
    private final EditableTextField userTextField;

    public UserPanel() {
        setBackground(Color.BLACK);

        setLayout(new GridLayout(3, 1));
        userTextField = new EditableTextField();
        Text nicknameText = new Text("Nickname", 11);

        userTextField.setVerifier(new UsernameVerifier());

        add(nicknameText);
        add(userTextField);

        // JLabel fill
        add(new JLabel());
    }

    public EditableTextField getUserTextField() {
        return userTextField;
    }
}
