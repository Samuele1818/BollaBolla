package view.profile.games;

import model.entity.monster.Monster;
import model.play.register.GameRecord;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameTableModel extends AbstractTableModel {
    private final String[] COLUMNS_NAME = {"Character", "Win", "Score", "Last Level", "Date"};
    private List<GameRecord> games;

    /**
     * GameTableModel constructor
     * Init games array
     */
    public GameTableModel() {
        this.games = new ArrayList<>();
    }

    /**
     * Get class of the queried column
     *
     * @param column the column being queried
     * @return class of the queried column
     */
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

    /**
     * Return column value based on row (cell value)
     *
     * @param row    the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return value of the cell
     */
    @Override
    public Object getValueAt(int row, int column) {
        GameRecord game = games.get(row);
        return switch (column) {
            case 0 -> game.getCharacter();
            case 1 -> game.isHasWin() ? "Win" : "Lose";
            case 2 -> game.getScore();
            case 3 -> game.getLastLevel();
            case 4 -> game.getDate();
            default -> null;
        };
    }

    /**
     * Get number of rows
     *
     * @return number of rows
     */
    @Override
    public int getRowCount() {
        return games.size();
    }

    /**
     * Get number of columns
     *
     * @return number of columns
     */
    @Override
    public int getColumnCount() {
        return COLUMNS_NAME.length;
    }

    /**
     * Get queried column name
     *
     * @param column the column being queried
     * @return queried column name
     */
    @Override
    public String getColumnName(int column) {
        return COLUMNS_NAME[column];
    }

    /**
     * Set games in the list and refresh table data
     *
     * @param newGames new list of games
     */
    public void setGames(List<GameRecord> newGames) {
        this.games = newGames;
        fireTableDataChanged();
    }
}