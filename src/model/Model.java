package model;

import model.files.FileManager;
import model.play.Player;
import model.play.leaderboard.Leaderboard;

public class Model {
    private static Model instance;
    private Level level;
    private Player player;
    private GlobalTheme globalTheme;
    private Leaderboard leaderboard;

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

    public static Model getInstance() {
        if (instance == null) instance = new Model();
        return instance;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public Player setPlayer(Player player) {
        this.player = player;
        return player;
    }

    public GlobalTheme getGlobalTheme() {
        return globalTheme;
    }

    public void setGlobalTheme(GlobalTheme globalTheme) {
        this.globalTheme = globalTheme;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }
}
