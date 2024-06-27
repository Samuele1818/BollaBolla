package model.play.leaderboard;

public class LeaderboardRecord {
    private int score, level;
    private String playerName;

    public LeaderboardRecord(int score, int level, String playerName) {
        this.score = score;
        this.level = level;
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
