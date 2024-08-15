package view.profile;

import javax.swing.*;
import java.util.regex.Pattern;

public class UsernameVerifier extends InputVerifier {
    /**
     * Check if username is valid using regex
     * Username has to be between 1 and 12 characters
     * Has to contains only alphabetic characters (lowercase or uppercase) or numbers
     * -, _ and . characters are allowed
     *
     * @param input the JComponent to verify
     * @return username validity
     */
    @Override
    public boolean verify(JComponent input) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9-_.]{1,12}$");

        String inputText = ((JTextField) input).getText();

        return pattern.matcher(inputText).matches() && !inputText.isEmpty();
    }

    /**
     * Display an error message if username is invalid
     *
     * @param input the JComponent to verify
     * @return username validity
     */
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
