package view.profile.profile_panel;

import view.components.EditableTextField;
import view.components.Text;
import view.profile.UsernameVerifier;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {
    private EditableTextField userTextField;

    /**
     * LoadButtonPanel constructor
     * Show username text field
     */
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

    /**
     * get username TextField
     *
     * @return username TextField
     */
    public EditableTextField getUserTextField() {
        return userTextField;
    }
}
