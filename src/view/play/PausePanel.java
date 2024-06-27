package view.play;

import view.components.Text;
import view.components.title.TitlePanel;
import view.play.puase.ResumeButtonPanel;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    private final TitlePanel titlePanel;
    private final Text text;
    private final ResumeButtonPanel resumeButtonPanel;

    public PausePanel() {
        setBackground(Color.BLACK);

        titlePanel = new TitlePanel();

        text = new Text("Pause", true);

        resumeButtonPanel = new ResumeButtonPanel();

        setLayout(new GridLayout(3, 1));

        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setForeground(Color.YELLOW);

        add(titlePanel);
        add(text);
        add(resumeButtonPanel);
    }

    public ResumeButtonPanel resumeButtonPanel() {
        return resumeButtonPanel;
    }
}
