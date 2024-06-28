package view.profile.games;

import model.entity.Monster;
import model.files.FileManager;
import view.components.Image;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTableCell extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        cellComponent.setForeground(Color.WHITE);
        cellComponent.setBackground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.LEFT);

        if (column == 0 && value instanceof Monster.Type type) {

            setIcon(switch (type) {
                case Monster.Type.BOB ->
                        new Image(12, 12, FileManager.getResource("static_image", "bob.png")).getImageIcon();
                case Monster.Type.BUB ->
                        new Image(12, 12, FileManager.getResource("static_image", "bub.png")).getImageIcon();
                default -> null;
            });

        } else if (column == 4 && value instanceof Date) {
            Date dateValue = (Date) value;
            setText(new SimpleDateFormat().format(dateValue)); // Format date using SimpleDateFormat
            setIcon(null); // Clear icon for date column

        } else setIcon(null);

        return cellComponent;
    }
}