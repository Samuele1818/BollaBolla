package view.leaderboard;

import model.play.leaderboard.Leaderboard;
import model.play.leaderboard.LeaderboardRecord;
import view.components.ScrollPane;
import view.components.title.TitlePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class LeaderboardPanel extends JPanel implements Observer {
    private TitlePanel titlePanel;
    private JTable table;
    private DefaultTableModel model;

    public LeaderboardPanel() {
        titlePanel = new TitlePanel(false);

        // Create table model
        model = new DefaultTableModel();
        model.addColumn("Rank");
        model.addColumn("Score");
        model.addColumn("Last Level");
        model.addColumn("Name");

        setLayout(new GridLayout(2, 1));

        // Create table
        table = new JTable(model);
        table.setEnabled(false);

        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);

        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);

        ScrollPane scrollPaneWrapper = new ScrollPane(table);

        add(titlePanel);
        add(scrollPaneWrapper);
    }

    public void init(Leaderboard leaderboard) {
        leaderboard.addObserver(this);
    }

    public TitlePanel getTitlePanel() {
        return titlePanel;
    }

    public void setLeaderboard(ArrayList<LeaderboardRecord> newRecords) {
        // Clear old rows
        model.setRowCount(0);

        int rank = 1;
        for (LeaderboardRecord record : newRecords) {
            model.addRow(new Object[]{rank, record.getScore(), record.getLevel(), record.getPlayerName()});
            rank++;
        }

        table.setModel(model);
        model.fireTableDataChanged();
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof ArrayList<?> arrayList) {
            if (arrayList.isEmpty()) {
                setLeaderboard(new ArrayList<>());
                return;
            }

            if (arrayList.getFirst() instanceof LeaderboardRecord) {
                ArrayList<LeaderboardRecord> newRecords = (ArrayList<LeaderboardRecord>) arrayList;
                setLeaderboard(newRecords);
            }
        }
    }


}
