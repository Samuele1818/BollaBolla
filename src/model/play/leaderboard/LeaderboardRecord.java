package model.play.leaderboard;

public class LeaderboardRecord {
    private int score;
    private int level;
    private String playerName;

    /**
     * LeaderboardRecord constructor
     *
     * @param score      score of the game
     * @param level      last level reached
     * @param playerName name of player that has played the game
     */
    public LeaderboardRecord(int score, int level, String playerName) {
        this.score = score;
        this.level = level;
        this.playerName = playerName;
    }

    /**
     * Get score reached
     *
     * @return score reached
     */
    public int getScore() {
        return score;
    }

    /**
     * Get last level reached
     *
     * @return last level reached
     */
    public int getLevel() {
        return level;
    }

    /**
     * Get name of the player who has played the game
     *
     * @return name of the player who has played the game
     */
    public String getPlayerName() {
        return playerName;
    }


}
