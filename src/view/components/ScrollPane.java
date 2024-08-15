package view.components;

import javax.swing.*;
import java.awt.*;

public class ScrollPane extends JScrollPane {
    /**
     * ScrollPane constructor
     * Custom JScrollPane without vertical and horizontal scrollbar and with top white border
     *
     * @param component component contained by the ScrollPane
     */
    public ScrollPane(JComponent component) {
        super(component);

        // Remove x scroll axis
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);

        getViewport().setBackground(Color.BLACK);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));
    }
}
