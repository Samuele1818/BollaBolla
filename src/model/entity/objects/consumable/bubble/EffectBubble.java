package model.entity.objects.consumable.bubble;

import model.Level;
import model.entity.Entity;

public class EffectBubble extends Bubble {
    private final Type TYPE;

    /**
     * Effect bubble constructor
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param type effect bubble type
     */
    public EffectBubble(int x, int y, Type type) {
        super(x, y, Entity.Type.EFFECT_BUBBLE, type.getFilename());

        setCurrentAnimation(Level.Animation.RIGHT);
        this.TYPE = type;
    }

    /**
     * Get type of the effect bubble
     *
     * @return type of the effect bubble
     */
    public Type getType() {
        return TYPE;
    }

    /**
     * Bubble type associated with icon filename
     */
    public enum Type {
        BUBBLE_LIGHTNING("lightning.png"),
        BUBBLE_HEALTH("health.png");
        private final String FILENAME;

        Type(String filename) {
            this.FILENAME = filename;
        }

        public String getFilename() {
            return FILENAME;
        }
    }
}
