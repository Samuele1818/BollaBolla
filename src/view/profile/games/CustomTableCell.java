package view.profile.games;

import model.entity.monster.Monster;
import model.utils.FileManager;
import view.components.Image;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTableCell extends DefaultTableCellRenderer {
    private static final String PATTERN = "dd-MM-yyyy";

    /**
     * Make table cell with Black background, white text and left text alignment
     * Render character icon in the first column
     * Show formatted date in the last column
     *
     * @param table      the <code>JTable</code>
     * @param value      the value to assign to the cell at
     *                   <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus   true if cell has focus
     * @param row        the row of the cell to render
     * @param column     the column of the cell to render
     * @return custom cell component
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        cellComponent.setForeground(Color.WHITE);
        cellComponent.setBackground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.LEFT);

        // If we are in first column
        if (column == 0 && value instanceof Monster.Type type) {
            setIcon(switch (type) {
                case Monster.Type.BOB ->
                        new Image(12, 12, FileManager.getResource("static_image", "bob.png")).getImageIcon();
                case Monster.Type.BUB ->
                        new Image(12, 12, FileManager.getResource("static_image", "bub.png")).getImageIcon();
                default -> null;
            });

        } else if (column == 4 && value instanceof Date dateValue) { // if we are in last column
            setText(new SimpleDateFormat(PATTERN).format(dateValue)); // Format date using SimpleDateFormat
            setIcon(null); // Clear icon for date column

        } else setIcon(null);

        return cellComponent;
    }
}