package model;

import model.play.Player;
import model.play.leaderboard.Leaderboard;
import model.utils.FileManager;

public class Model {
    private static Model instance = null;
    private Level level;
    private Player player;
    private GlobalTheme globalTheme;
    private Leaderboard leaderboard;

    /**
     * Model constructor
     */
    private Model() {
        // Create directories
        FileManager.init();

        this.player = Player.getInstance();
        Player lastPlayer = Player.getInstance().getLastPlayer();
        player.changePlayer(lastPlayer.getName(), lastPlayer.getMainCharacter(), lastPlayer.getAvatar(), lastPlayer.getRegister());

        globalTheme = new GlobalTheme(player.getMainCharacter());

        level = new Level(FileManager.getResource("blocks", "normal_blocks", "block_2.png"), this.player.getMainCharacter());

        leaderboard = new Leaderboard();

    }

    /**
     * Get instance of the class
     *
     * @return instance of the class
     */
    public static Model getInstance() {
        if (instance == null) instance = new Model();
        return instance;
    }

    public Level getLevel() {
        return level;
    }


    /**
     * Get current player instance
     *
     * @return current player
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * get current GlobalTheme instance
     *
     * @return current GlobalTheme
     */
    public GlobalTheme getGlobalTheme() {
        return globalTheme;
    }


    /**
     * get Leaderboard instance
     *
     * @return current leaderboard
     */
    public Leaderboard getLeaderboard() {
        return leaderboard;
    }
}
