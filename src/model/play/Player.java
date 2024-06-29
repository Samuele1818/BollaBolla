package model.play;

import model.entity.monster.Character;
import model.files.FileManager;
import model.play.register.GameRegister;

import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Observable;

public class Player extends Observable implements Serializable {
    public static final String PLAYER_FOLDER = "players";

    @Serial
    private static final long serialVersionUID = 4988872441489519317L;

    // Singleton pattern
    private static Player instance;
    private String name;
    private GameRegister register;
    private String avatar;

    private Character.Type mainCharacter;

    private Player() {
        this("");
    }

    private Player(String name) {
        this(name, Character.Type.BOB);
    }

    private Player(String name, Character.Type mainCharacter) {
        this(name, FileManager.getResource("static_image", "bob.gif"), mainCharacter);
    }

    private Player(String name, String avatar, Character.Type mainCharacter) {
        this(name, new GameRegister(), avatar, mainCharacter);
    }

    private Player(String name, GameRegister register, String avatar, Character.Type mainCharacter) {
        this.name = name;
        this.register = register;
        this.avatar = avatar;
        this.mainCharacter = mainCharacter;

        FileManager.createDirectory(PLAYER_FOLDER);
    }

    public static Player getInstance() {
        if (instance == null) instance = new Player();
        return instance;
    }

    public void changePlayer(Player player) {
        changePlayer(player.getName(), player.getMainCharacter(), player.getAvatar(), player.getRegister());
    }

    public void changePlayer(String name, Character.Type mainCharacter, String avatar, GameRegister gameRegister) {
        // Change parameters
        if (name != null) this.name = name;
        if (mainCharacter != null) {
            this.mainCharacter = mainCharacter;
        }
        if (avatar != null) this.avatar = avatar;
        if (gameRegister != null) this.register.setGames(gameRegister.getGames());

        // Save changes on file
        save();

        // Notify observers
        setChanged();
        notifyObservers(this);
    }


    public void save() {
        String fileName = String.valueOf(Path.of(PLAYER_FOLDER, name));
        FileManager.serialize(this, fileName);
    }


    public Player load(String name) {
        if (name == null) return null;
        String fileName = String.valueOf(Path.of(PLAYER_FOLDER, name));

        if (!FileManager.checkExists(fileName)) {
            Player newPlayer = new Player(name);
            newPlayer.save();
            return newPlayer;
        }

        return FileManager.deserialize(fileName);
    }

    public Player getLastPlayer() {
        Player p = load(FileManager.getLatestModifiedFile(PLAYER_FOLDER));
        if (p != null) return p;

        return load("player0");
    }

    public void insertGame(Game game) {
        register.insertGame(game);
        save();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameRegister getRegister() {
        return register;
    }

    public void setRegister(GameRegister register) {
        this.register = register;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Character.Type getMainCharacter() {
        return mainCharacter;
    }

    public void setMainCharacter(Character.Type mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    @Override
    public String toString() {
        return name + " " + mainCharacter + " " + avatar;
    }

}
