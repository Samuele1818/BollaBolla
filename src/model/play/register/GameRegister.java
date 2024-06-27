package model.play.register;

import model.play.Game;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class GameRegister extends Observable implements Serializable {
    @Serial
    private static final long serialVersionUID = 7407247953449993053L;

    private ArrayList<Game> games;

    public GameRegister() {
        this.games = new ArrayList<>();
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> newGames) {
        this.games = newGames;

        setChanged();
        notifyObservers(games);
    }

    public void init() {
        setChanged();
        notifyObservers(games);
    }

    public void insertGame(Game game) {
        games.add(game);

        setChanged();
        notifyObservers(game);
    }
}
