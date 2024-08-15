package model.play;

import model.entity.Entity;
import model.entity.monster.Character;
import model.play.register.GameRecord;
import model.play.register.GameRegister;
import model.utils.FileManager;

import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Observable;

public class Player extends Observable implements Serializable {
    public static final String PLAYER_FOLDER = "players";

    @Serial
    private static final long serialVersionUID = 4988872441489519317L;

    // Singleton pattern
    private static Player instance = null;
    private GameRegister register;
    private String name;
    private String avatar;

    private Character.Type mainCharacter;

    /**
     * Player constructor
     */
    private Player() {
        this("");
    }

    /**
     * Player constructor
     *
     * @param name player name
     */
    private Player(String name) {
        this(name, FileManager.getResource("static_image", "bob.gif"));
    }

    /**
     * Player constructor
     *
     * @param name   player name
     * @param avatar player avatar image
     */
    private Player(String name, String avatar) {
        this(name, new GameRegister(), avatar, Entity.Type.BOB);
    }

    /**
     * Player constructor
     *
     * @param name          player name
     * @param register      game register of the player
     * @param avatar        player avatar image
     * @param mainCharacter main character chose (bob or bub)
     */
    private Player(String name, GameRegister register, String avatar, Character.Type mainCharacter) {
        this.name = name;
        this.register = register;
        this.avatar = avatar;
        this.mainCharacter = mainCharacter;

        FileManager.createDirectory(PLAYER_FOLDER);
    }

    /**
     * Get player instance
     *
     * @return player instance
     */
    public static Player getInstance() {
        if (instance == null) instance = new Player();
        return instance;
    }

    /**
     * Copy values of the new class in this class
     *
     * @param player player by which get new values
     */
    public void changePlayer(Player player) {
        changePlayer(player.getName(), player.getMainCharacter(), player.getAvatar(), player.getRegister());
    }

    /**
     * Change player parameters based on new values
     *
     * @param name          new player name
     * @param mainCharacter new main character chose
     * @param avatar        new user image
     * @param gameRegister  new game register of the player
     */
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

    /**
     * Store player information in a file
     */
    public void save() {
        String fileName = String.valueOf(Path.of(PLAYER_FOLDER, name));
        FileManager.serialize(this, fileName);
    }

    /**
     * Create the player file if not exists, load player from the file
     *
     * @param name player name
     * @return player object
     */
    public Player load(String name) {
        if (name == null) return null;
        String fileName = String.valueOf(Path.of(PLAYER_FOLDER, name));

        if (FileManager.checkExists(fileName)) {
            Player newPlayer = new Player(name);
            newPlayer.save();
            return newPlayer;
        }

        return FileManager.deserialize(fileName);
    }

    /**
     * Load last player, if "players" folder is empty, create a default player called "player0"
     *
     * @return player object
     */
    public Player getLastPlayer() {
        Player p = load(FileManager.getLatestModifiedFile(PLAYER_FOLDER));
        if (p != null) return p;

        return load("player0");
    }

    /**
     * Insert a new game in the game register
     *
     * @param game new game to insert
     */
    public void insertGame(GameRecord game) {
        register.insertGame(game);
        save();
    }

    /**
     * Get player name
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Get game register
     *
     * @return game register of the player
     */
    public GameRegister getRegister() {
        return register;
    }

    /**
     * Get player avatar
     *
     * @return player
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Get player main character
     *
     * @return player main character
     */
    public Character.Type getMainCharacter() {
        return mainCharacter;
    }
}
