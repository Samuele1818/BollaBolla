package model.play.leaderboard;

import model.play.Player;
import model.utils.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Leaderboard extends Observable {
    private ArrayList<LeaderboardRecord> leaderboardRecords;

    /**
     * Leaderboard constructor
     */
    public Leaderboard() {
        leaderboardRecords = new ArrayList<>(20);
    }

    /**
     * Scan players folder and collect all games from the players order various games based on score of the game
     */
    public void init() {
        try (Stream<Path> files = Files.list(Path.of("store", "players"))) {
            leaderboardRecords = (ArrayList<LeaderboardRecord>) files
                    .map(path -> ((Player) FileManager.deserialize(String.valueOf(path).substring(6, String.valueOf(path).length() - 4))))
                    .flatMap(player -> player.getRegister().getGames()
                            .stream()
                            .map(game -> new LeaderboardRecord(game.getScore(), game.getLastLevel(), player.getName()))
                    )
                    .sorted(Comparator.comparingInt(LeaderboardRecord::getScore).reversed())
                    .collect(Collectors.toList());

            setChanged();
            notifyObservers(leaderboardRecords);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
