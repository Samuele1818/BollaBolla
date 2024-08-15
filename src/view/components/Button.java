package view.components;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    // Should repaint when global theme changes
    private boolean avoidRepaint;
    private Image customIcon;

    /**
     * Button constructor
     *
     * @param buttonBuilder builder class
     */
    private Button(ButtonBuilder buttonBuilder) {
        String text = buttonBuilder.text;
        Color bgcolor = buttonBuilder.bgcolor;
        int width = buttonBuilder.width;
        int height = buttonBuilder.height;
        this.customIcon = buttonBuilder.customIcon;
        this.avoidRepaint = buttonBuilder.avoidRepaint;

        setText(text);
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));

        setBackground(bgcolor);
        if (customIcon != null)
            setIcon(customIcon.getImageIcon());
    }

    /**
     * Repaint component using UIManger values and change icon
     * Avoid repaint the ui if <code>avoidRepaint</code>
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (avoidRepaint) return;

        setForeground(UIManager.getColor("Button.foreground"));
        setCustomIcon(customIcon);
    }


    /**
     * Allow icon modification to avoid create a new button every time user change player name
     *
     * @param customIcon new image icon
     */
    private void setCustomIcon(Image customIcon) {
        this.customIcon = customIcon;
    }

    /**
     * Builder class to do builder pattern
     */
    public static class ButtonBuilder extends JButton {
        private static final int BUTTON_WIDTH = 200;
        private static final int BUTTON_HEIGHT = 30;
        private static final Color DEFAULT_BG_COLOR = new Color(60, 66, 74);
        private final String text;
        private final Color bgcolor;
        private int width;
        private int height;
        private Image customIcon;
        private boolean avoidRepaint;

        /**
         * ButtonBuilder constructor
         *
         * @param text required text parameter
         */
        public ButtonBuilder(String text) {
            this.text = text;
            this.width = BUTTON_WIDTH;
            this.height = BUTTON_HEIGHT;
            this.bgcolor = DEFAULT_BG_COLOR;

            this.customIcon = null;
            this.avoidRepaint = false;
        }

        /**
         * Set width of the button
         *
         * @param width button width
         * @return ButtonBuilder with width set
         */
        public ButtonBuilder setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * Set height of the button
         *
         * @param height button height
         * @return ButtonBuilder with height set
         */
        public ButtonBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        /**
         * Set button icon
         *
         * @param customIcon button icon
         * @return ButtonBuilder with icon set
         */
        public ButtonBuilder setCustomIcon(Image customIcon) {
            this.customIcon = customIcon;
            return this;
        }

        /**
         * Set if button should avoid repaint when global theme change
         *
         * @param avoidRepaint button should repaint or not
         * @return ButtonBuilder with avoid repaint set
         */
        public ButtonBuilder setAvoidRepaint(boolean avoidRepaint) {
            this.avoidRepaint = avoidRepaint;
            return this;
        }

        /**
         * Build the button instance with parameters set on ButtonBuilder
         *
         * @return instance of button
         */
        public Button build() {
            return new Button(this);
        }
    }
}
