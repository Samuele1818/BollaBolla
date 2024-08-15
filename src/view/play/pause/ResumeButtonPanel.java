package view.play.pause;

import view.components.Button;

import javax.swing.*;
import java.awt.*;

public class ResumeButtonPanel extends JPanel {
    private Button resumeButton;

    /**
     * ResumeButtonPanel constructor
     */
    public ResumeButtonPanel() {
        resumeButton = new Button.ButtonBuilder("Resume").build();
        add(resumeButton);
        setBackground(Color.BLACK);
    }

    /**
     * Get resume button
     *
     * @return resume button
     */
    public Button getResumeButton() {
        return resumeButton;
    }
}
