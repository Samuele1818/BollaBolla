package model.play.register;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class GameRegister extends Observable implements Serializable {
    @Serial
    private static final long serialVersionUID = 7407247953449993053L;

    private ArrayList<GameRecord> games;

    /**
     * GameRegister constructor
     */
    public GameRegister() {
        this.games = new ArrayList<>();
    }

    /**
     * Get list of played games
     *
     * @return list of played games
     */
    public ArrayList<GameRecord> getGames() {
        return games;
    }

    /**
     * Set list of played games
     *
     * @param newGames list of new games to set
     */
    public void setGames(ArrayList<GameRecord> newGames) {
        this.games = newGames;

        init();
    }

    /**
     * Notify observers that list of games has been updated and pass the updated list at the observers
     */
    public void init() {
        setChanged();
        notifyObservers(games);
    }

    /**
     * Insert new game in the list and notify observers, pass the new game inserted at the observers
     *
     * @param game game to be inserted
     */
    public void insertGame(GameRecord game) {
        games.add(game);

        setChanged();
        notifyObservers(game);
    }
}
