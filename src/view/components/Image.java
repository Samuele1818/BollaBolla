package view.components;

import javax.swing.*;

public class Image extends JLabel {
    private final ImageIcon imageIcon;

    public Image(int width, int height, String fileName) {
        imageIcon =
                new ImageIcon(
                        new ImageIcon(fileName)
                                .getImage()
                                .getScaledInstance(width, height,
                                        java.awt.Image.SCALE_DEFAULT)
                );
        setIcon(imageIcon);

    }

    public Image(int width, int height, String fileName, int horizontalAlignment) {
        this(width, height, fileName);

        setHorizontalAlignment(horizontalAlignment);
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public java.awt.Image getImage() {
        return imageIcon.getImage();
    }
}
