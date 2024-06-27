package view.components;

import javax.swing.*;
import java.awt.*;

public class ScrollPane extends JScrollPane {
    public ScrollPane(JComponent component) {
        super(component);

        // Remove x scroll axis
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);

        getViewport().setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));
    }
}
