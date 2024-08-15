package view.play;

import view.components.Text;
import view.components.title.TitlePanel;

import javax.swing.*;
import java.awt.*;

public class WinPanel extends JPanel {
    private TitlePanel titlePanel;

    /**
     * WinPanel constructor
     * Set win text
     */
    public WinPanel() {
        setBackground(Color.BLACK);

        titlePanel = new TitlePanel();

        Text text = new Text("Win! Congratulations", true);

        setLayout(new GridLayout(3, 1));

        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setForeground(Color.YELLOW);

        add(titlePanel);
        add(text);
        add(new JLabel());
    }

    /**
     * Get title panel
     *
     * @return title panel
     */
    public TitlePanel getTitlePanel() {
        return titlePanel;
    }
}
