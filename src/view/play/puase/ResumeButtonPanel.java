package view.play.puase;

import javax.swing.*;
import java.awt.*;

public class ResumeButtonPanel extends JPanel {
    private final Button resumeButton;

    public ResumeButtonPanel() {
        resumeButton = new Button("Resume");
    }

    public Button getResumeButton() {
        return resumeButton;
    }
}
