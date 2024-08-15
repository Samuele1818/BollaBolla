package model;

import model.entity.monster.Character;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

public class GlobalTheme extends Observable {
    private Color globalTheme;

    /**
     * GlobalTheme constructor
     *
     * @param mainCharacter character choose
     */
    public GlobalTheme(Character.Type mainCharacter) {
        setTheme(mainCharacter);
    }

    /**
     * Set current global theme based on character chose
     *
     * @param theme character chose by user to determine the theme
     */
    public void setTheme(Character.Type theme) {
        switch (theme) {
            case BOB -> globalTheme = Color.GREEN;
            case BUB -> globalTheme = Color.CYAN;
        }

        setUIManger();

        // Communicate that theme has changed
        setChanged();

        // Notify to all observer the change
        notifyObservers();
    }

    /**
     * Set correct colors in UIManager
     */
    public void setUIManger() {
        UIManager.put("Button.foreground", getGlobalTheme());
        UIManager.put("Label.foreground", getGlobalTheme());
        UIManager.put("TextField.foreground", getGlobalTheme());
        UIManager.put("TextField.border", BorderFactory.createLineBorder(getGlobalTheme()));
    }

    /**
     * Get current global theme color
     *
     * @return current global theme color
     */
    public Color getGlobalTheme() {
        return globalTheme;
    }

}
