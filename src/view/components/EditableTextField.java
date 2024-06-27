package view.components;

import javax.swing.*;
import java.awt.*;

public class EditableTextField extends JTextField {
    public static final int TEXT_FIELD_WIDTH = 200;
    public static final int TEXT_FIELD_HEIGHT = 30;
    public static final int TEXT_COLUMN = 9;

    public EditableTextField(int width, int height) {
        setBackground(Color.BLACK);
        setColumns(TEXT_COLUMN);

        setSize(width, height);
    }

    public EditableTextField() {
        this(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setForeground(UIManager.getColor("TextField.foreground"));
        setBorder(UIManager.getBorder("TextField.border"));
    }

    public void setVerifier(InputVerifier verifier) {
        setInputVerifier(verifier);
    }
}
