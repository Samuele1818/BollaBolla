package view.components;

import javax.swing.*;

public class Image extends JLabel {
    private ImageIcon imageIcon;

    /**
     * Image constructor
     *
     * @param width    image width
     * @param height   image height
     * @param fileName filename of the image
     */
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

    /**
     * Image constructor
     *
     * @param width               image width
     * @param height              image height
     * @param fileName            filename of the image
     * @param horizontalAlignment horizontal alignment
     */
    public Image(int width, int height, String fileName, int horizontalAlignment) {
        this(width, height, fileName);

        setHorizontalAlignment(horizontalAlignment);
    }

    /**
     * Get image icon used
     *
     * @return image icon
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }
}
