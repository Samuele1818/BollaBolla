package view.profile.profile_panel;

import model.play.Player;

import javax.swing.*;
import java.awt.*;

public class AvatarPanel extends JPanel {
    private Image userImage;

    /**
     * AvatarPanel constructor
     */
    public AvatarPanel() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        setUserImage(Player.getInstance().getAvatar());

    }

    /**
     * Change user image and repaint component
     *
     * @param imageIcon path to the new user image
     */
    public void setUserImage(String imageIcon) {
        userImage = new ImageIcon(imageIcon).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);

        invalidate();
        validate();
        repaint();
    }

    /**
     * Draw user image correctly
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the image in the center of the panel
        int x = (getWidth() - userImage.getWidth(this)) / 2;
        int y = (getHeight() - userImage.getHeight(this)) / 2;
        g.drawImage(userImage, x, y, this);
    }
}
