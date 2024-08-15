package view.components;

import javax.swing.*;
import java.awt.*;

public class EditableTextField extends JTextField {
    public static final int TEXT_FIELD_WIDTH = 200;
    public static final int TEXT_FIELD_HEIGHT = 30;
    public static final int TEXT_COLUMN = 9;

    /**
     * EditableTextField constructor
     *
     * @param width  text field width
     * @param height text field height
     */
    public EditableTextField(int width, int height) {
        setBackground(Color.BLACK);
        setColumns(TEXT_COLUMN);

        setSize(width, height);
    }

    /**
     * EditableTextField constructor with default size
     */
    public EditableTextField() {
        this(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
    }

    /**
     * Repaint component using UIManger values
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setForeground(UIManager.getColor("TextField.foreground"));
        setBorder(UIManager.getBorder("TextField.border"));
    }

    /**
     * Set input verifier
     *
     * @param verifier input verifier to use
     */
    public void setVerifier(InputVerifier verifier) {
        setInputVerifier(verifier);
    }
}
