package view.profile.games;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomHeader extends DefaultTableCellRenderer {
    /**
     * Make table cell header with Black background and white foreground
     * set also bold font
     *
     * @param table      the <code>JTable</code>
     * @param value      the value to assign to the cell at
     *                   <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus   true if cell has focus
     * @param row        the row of the cell to render
     * @param column     the column of the cell to render
     * @return custom header component
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component headerComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        headerComponent.setForeground(Color.WHITE);
        headerComponent.setBackground(Color.BLACK);
        headerComponent.setFont(headerComponent.getFont().deriveFont(Font.BOLD));
        return headerComponent;
    }
}