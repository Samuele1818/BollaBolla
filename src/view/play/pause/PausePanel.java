package view.play.pause;

import view.components.Text;
import view.components.title.TitlePanel;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    private TitlePanel titlePanel;
    private ResumeButtonPanel resumeButtonPanel;

    /**
     * PausePanel constructor
     * Draw pause text and show resume button
     */
    public PausePanel() {
        setBackground(Color.BLACK);

        titlePanel = new TitlePanel(false);

        Text text = new Text("Pause", true);

        resumeButtonPanel = new ResumeButtonPanel();

        setLayout(new GridLayout(3, 1));

        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setForeground(Color.YELLOW);

        add(titlePanel);
        add(text);
        add(resumeButtonPanel);
    }

    /**
     * Get title panel
     *
     * @return title panel
     */
    public TitlePanel getTitlePanel() {
        return titlePanel;
    }

    /**
     * Get resume button panel
     *
     * @return resume button panel
     */
    public ResumeButtonPanel resumeButtonPanel() {
        return resumeButtonPanel;
    }
}
