package model.play.register;


import model.entity.monster.Character;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class GameRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = 7407247953449999050L;

    private Character.Type character;
    private boolean hasWin;
    private int score;
    private int lastLevel;
    private Date date;

    /**
     * Game constructor
     *
     * @param character character used
     * @param hasWin    if player has win the game
     * @param score     score reached
     * @param lastLevel last level reached
     * @param date      date of the game
     */
    public GameRecord(Character.Type character, boolean hasWin, int score, int lastLevel, Date date) {
        this.character = character;
        this.hasWin = hasWin;
        this.score = score;
        this.lastLevel = lastLevel;
        this.date = date;
    }

    /**
     * Get character used in the game
     *
     * @return character used
     */
    public Character.Type getCharacter() {
        return character;
    }

    /**
     * Check if player has win
     *
     * @return if player has win
     */
    public boolean isHasWin() {
        return hasWin;
    }

    /**
     * Score reached during the game
     *
     * @return score reached
     */
    public int getScore() {
        return score;
    }

    /**
     * Last level reached
     *
     * @return last level reached during the game
     */
    public int getLastLevel() {
        return lastLevel;
    }

    /**
     * Date of the game
     *
     * @return date of the game
     */
    public Date getDate() {
        return date;
    }
}
