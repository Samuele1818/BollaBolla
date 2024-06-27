package model.play;


import model.entity.monster.Character;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Game implements Serializable {
    @Serial
    private static final long serialVersionUID = 7407247953449999050L;

    private Character.Type character;
    private boolean hasWin;
    private int score, lastLevel;
    private Date date;


    public Game(Character.Type character, boolean hasWin, int score, int lastLevel, Date date) {
        this.character = character;
        this.hasWin = hasWin;
        this.score = score;
        this.lastLevel = lastLevel;
        this.date = date;
    }

    public Character.Type getCharacter() {
        return character;
    }

    public void setCharacter(Character.Type character) {
        this.character = character;
    }

    public boolean isHasWin() {
        return hasWin;
    }

    public void setHasWin(boolean hasWin) {
        this.hasWin = hasWin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
