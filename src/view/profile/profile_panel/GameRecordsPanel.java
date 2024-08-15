package view.profile.profile_panel;

import model.play.register.GameRecord;
import model.play.register.GameRegister;
import view.components.ScrollPane;
import view.profile.games.CustomHeader;
import view.profile.games.CustomTableCell;
import view.profile.games.GameTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameRecordsPanel extends JPanel implements Observer {
    private GameTableModel model;

    /**
     * GameRecordsPanel constructor
     * Show custom table
     */
    public GameRecordsPanel() {
        model = new GameTableModel();
        JTable table = new JTable(model);

        // Set the custom renderer for all columns
        CustomTableCell customCell = new CustomTableCell();
        for (int i = 0; i < table.getColumnCount(); i++)
            table.getColumnModel().getColumn(i).setCellRenderer(customCell);

        // Set the custom header renderer for the table header
        CustomHeader customHeaderCell = new CustomHeader();
        table.getTableHeader().setDefaultRenderer(customHeaderCell);

        // Set the preferred width for each column
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Remove selection from cells and columns
        table.setEnabled(false);

        ScrollPane scrollPaneWrapper = new ScrollPane(table);

        setLayout(new GridLayout());
        add(scrollPaneWrapper);
    }

    /**
     * Subscribe to observable
     *
     * @param gameRegister observable class to subscribe to
     */
    public void init(GameRegister gameRegister) {
        gameRegister.addObserver(this);
    }

    /**
     * Update table when game register is modified
     *
     * @param o   the observable object.
     * @param arg an argument passed to the {@code notifyObservers}
     *            method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ArrayList<?> arrayList) {
            if (arrayList.isEmpty()) {
                model.setGames(new ArrayList<>());
                return;
            }

            if (arrayList.getFirst() instanceof GameRecord) {
                ArrayList<GameRecord> newGames = (ArrayList<GameRecord>) arg;
                model.setGames(newGames);
            }
        }
    }
}
