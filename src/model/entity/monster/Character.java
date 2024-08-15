package model.entity.monster;

import model.Level;
import model.behaviour.Fire;
import model.behaviour.Jump;
import model.entity.objects.Brick;
import model.entity.objects.consumable.PowerUp;
import model.entity.objects.consumable.bubble.NormalBubble;

import java.io.Serial;
import java.util.HashMap;

public class Character extends Monster implements Jump, Fire {
    // Spawn at fourth brick at height 0 with 3 healths
    public static final int SPAWN_X = Brick.WIDTH * 4, SPAWN_Y = Brick.HEIGHT * (Level.ROWS - 5), HEALTH = 3;
    @Serial
    private static final long serialVersionUID = 886140675884436361L;
    private static final int DEFAULT_JUMP = 50;

    private Type type;
    private int health;
    private int jumpHeight;
    private boolean isJumping;
    private boolean canJump;

    private HashMap<PowerUp.Type, Boolean> powerUps;

    /**
     * Character constructor
     *
     * @param type Character type (Bob or Bub)
     */
    public Character(Type type) {
        super(Character.SPAWN_X, Character.SPAWN_Y, 2, type, Level.Animation.RIGHT.getAnimationFileName()); // Character spawn with RIGHT direction so gif is right direction
        this.health = HEALTH;
        this.jumpHeight = DEFAULT_JUMP;

        setType(type);

        changeType(type);

        // Init powerUps map
        powerUps = new HashMap<>();
        powerUps.put(PowerUp.Type.RED_SHOE, false);
        powerUps.put(PowerUp.Type.YELLOW_CANDY, false);
        powerUps.put(PowerUp.Type.PINK_CANDY, false);
        powerUps.put(PowerUp.Type.BLUE_CANDY, false);
        powerUps.put(PowerUp.Type.PINK_RING, false);
        powerUps.put(PowerUp.Type.BLUE_RING, false);
        powerUps.put(PowerUp.Type.RED_RING, false);

        isJumping = false;
        canJump = false;
    }

    /**
     * Check if character can jump
     *
     * @return character can jump
     */
    public boolean canJump() {
        return canJump;
    }

    /**
     * Set if character can jump
     *
     * @param possibilityOfJumping player can jump
     */
    public void setCanJump(boolean possibilityOfJumping) {
        this.canJump = possibilityOfJumping;
    }

    /**
     * Get powerUps hashmap
     *
     * @return hashmap of powerUps
     */
    public HashMap<PowerUp.Type, Boolean> getPowerUps() {
        return powerUps;
    }

    /**
     * Set powerUps hashmap
     *
     * @param powerUps hashmap with powerUps
     */
    public void setPowerUps(HashMap<PowerUp.Type, Boolean> powerUps) {
        this.powerUps = powerUps;
    }

    /**
     * Check if character is jumping
     *
     * @return if character is jumping
     */
    public boolean isJumping() {
        return isJumping;
    }

    /**
     * Set if character is jumping
     *
     * @param isJumping if character is jumping
     */
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    /**
     * Reset jump height to default
     */
    public void resetHumpHeight() {
        jumpHeight = DEFAULT_JUMP;
    }

    /**
     * Get current character health
     *
     * @return character health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set character current health
     *
     * @param health current health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Get current jump height
     *
     * @return current jump height
     */
    public int getJumpHeight() {
        return jumpHeight;
    }

    /**
     * Set current jump height
     *
     * @param jumpHeight new jump height
     */
    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    /**
     * Get character type
     *
     * @return character type
     */
    public Type getType() {
        return type;
    }

    /**
     * Set character type
     *
     * @param type new character type
     */
    public void setType(Type type) {
        // Check if Type is valid for character (Bob or Bub) else set BOB as default to prevent bugs
        this.type = switch (type) {
            case BOB, BUB -> type;
            default -> Type.BOB;
        };
    }

    /**
     * Character move behaviour
     *
     * @param currentSpeed current speed of movement
     */
    @Override
    public void move(int currentSpeed) {
        setX(getX() + currentSpeed);
    }

    /**
     * Character jump behaviour
     */
    @Override
    public void jump() {
        setY(getY() - 2);
    }

    /**
     * Character fire behaviour
     *
     * @return the fired NormalBubble object
     */
    @Override
    public NormalBubble fire() {
        int bubbleX = 0;
        int bubbleY = 0;
        bubbleX = switch (getCurrentAnimation()) {
            case LEFT -> {
                bubbleY = getY() + 5;
                yield getX() - 24;
            }
            case RIGHT -> {
                bubbleY = getY() + 5;
                yield getX() + HEIGHT;
            }
            default -> bubbleX;
        };

        return new NormalBubble(bubbleX, bubbleY, getCurrentAnimation());
    }

    /**
     * Character fall behaviour
     */
    @Override
    public void fall() {
        this.setY(getY() + 2);
    }
}
