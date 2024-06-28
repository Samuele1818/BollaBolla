package view.profile.profile_panel;

import model.play.Game;
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
    private final GameTableModel model;
    private final JTable table;
    private final ScrollPane scrollPaneWrapper;

    public GameRecordsPanel() {
        model = new GameTableModel();
        table = new JTable(model);

        // Set the custom renderer for all columns
        CustomTableCell renderer = new CustomTableCell();
        for (int i = 0; i < table.getColumnCount(); i++) table.getColumnModel().getColumn(i).setCellRenderer(renderer);


        // Set the custom header renderer for the table header
        CustomHeader headerRenderer = new CustomHeader();
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        // Set the preferred width for each column
        table.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Remove selection from cells and columns
        table.setEnabled(false);

        scrollPaneWrapper = new ScrollPane(table);

        setLayout(new GridLayout());
        add(scrollPaneWrapper);
    }

    public void init(GameRegister gameRegister) {
        gameRegister.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ArrayList<?> arrayList) {

            if (arrayList.isEmpty()) {
                model.setGames(new ArrayList<>());
                return;
            }

            if (arrayList.getFirst() instanceof Game) {
                ArrayList<Game> newGames = (ArrayList<Game>) arg;
                model.setGames(newGames);
            }
        }
    }
}
