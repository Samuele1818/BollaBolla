package model.utils;

import javax.swing.*;
import java.util.regex.Pattern;

public class UsernameVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-_.]{1,12}$");

        String inputText = ((JTextField) input).getText();

        return pattern.matcher(inputText).matches() && !inputText.isEmpty();
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        JTextField field = (JTextField) input;
        if (verify(input)) {
            return true;

        } else {
            field.selectAll();
            JOptionPane.showMessageDialog(null, "Invalid NickName", "invalid Nickname", JOptionPane.WARNING_MESSAGE);
            return false;
        }

    }
}
