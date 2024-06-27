package model;

import model.entity.monster.Character;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

// TODO: Singleton?
public class GlobalTheme extends Observable {
    public Color globalTheme;

    public GlobalTheme(Character.Type mainCharacter) {
        setTheme(mainCharacter);
    }

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

    // TODO: Implementare solo su componenti custom
    public void setUIManger() {
        UIManager.put("Button.foreground", getGlobalTheme());
        UIManager.put("Label.foreground", getGlobalTheme());
        UIManager.put("TextField.foreground", getGlobalTheme());
        UIManager.put("TextField.border", BorderFactory.createLineBorder(getGlobalTheme()));
    }

    public Color getGlobalTheme() {
        return globalTheme;
    }

}
