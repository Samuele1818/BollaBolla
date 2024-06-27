package view.profile.games;

import model.entity.Monster;
import model.play.Game;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameTableModel extends AbstractTableModel {
    private final String[] columnsNames = {"Character", "Win", "Score", "Last Level", "Date"};
    private List<Game> games;

    public GameTableModel() {
        this.games = new ArrayList<>();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return switch (column) {
            case 0 -> Monster.Type.class;
            case 1 -> String.class;
            case 2, 3 -> Integer.class;
            case 4 -> Date.class;
            default -> Object.class;
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        Game game = games.get(row);
        return switch (column) {
            case 0 -> game.getCharacter();
            case 1 -> game.isHasWin() ? "Win" : "Lose";
            case 2 -> game.getScore();
            case 3 -> game.getLastLevel();
            case 4 -> game.getDate();
            default -> null;
        };
    }

    public void addGame(Game newGame) {
        this.games.add(newGame);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return games.size();
    }

    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnsNames[column];
    }

    public void setGames(List<Game> newGames) {
        this.games = newGames;
        fireTableDataChanged();
    }
}