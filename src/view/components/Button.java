package view.components;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    private String text;
    private Color bgcolor;
    private int width;
    private int height;
    private Image customIcon;
    private boolean avoidRepaint;

    private Button(ButtonBuilder buttonBuilder) {
        this.text = buttonBuilder.text;
        this.bgcolor = buttonBuilder.bgcolor;
        this.width = buttonBuilder.width;
        this.height = buttonBuilder.height;
        this.customIcon = buttonBuilder.customIcon;
        this.avoidRepaint = buttonBuilder.avoidRepaint;

        setText(text);
        setSize(this.width, this.height);
        setPreferredSize(new Dimension(width, height));

        setBackground(bgcolor);
        if (customIcon != null)
            setIcon(customIcon.getImageIcon());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (avoidRepaint) return;

        setForeground(UIManager.getColor("Button.foreground"));
        setCustomIcon(customIcon);
    }

    public Image getCustomIcon() {
        return customIcon;
    }

    /*
        Allow icon modification to avoid create a new button every time user change player name
     */
    private void setCustomIcon(Image customIcon) {
        this.customIcon = customIcon;
    }


    //Builder Pattern
    public static class ButtonBuilder extends JButton {
        private static final int BUTTON_WIDTH = 200;
        private static final int BUTTON_HEIGHT = 30;
        private static final Color DEFAULT_BG_COLOR = new Color(60, 66, 74);
        private String text;
        private int width;
        private int height;
        private Color bgcolor;

        private Image customIcon;
        private boolean avoidRepaint;

        public ButtonBuilder(String text) {
            this.text = text;
            this.width = BUTTON_WIDTH;
            this.height = BUTTON_HEIGHT;
            this.bgcolor = DEFAULT_BG_COLOR;

            this.customIcon = null;
            this.avoidRepaint = false;
        }

        public ButtonBuilder setWidth(int width) {
            this.width = width;
            return this;
        }

        public ButtonBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        public ButtonBuilder setBackgroundColor(Color backgroundColor) {
            this.bgcolor = backgroundColor;
            return this;
        }

        public ButtonBuilder setCustomIcon(Image customIcon) {
            this.customIcon = customIcon;
            return this;
        }

        public ButtonBuilder setAvoidRepaint(boolean avoidRepaint) {
            this.avoidRepaint = avoidRepaint;
            return this;
        }

        public Button build() {
            return new Button(this);
        }
    }
}
