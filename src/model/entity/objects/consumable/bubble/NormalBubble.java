package model.entity.objects.consumable.bubble;

import model.Level;


public class NormalBubble extends Bubble {
    private static int horizontalDistanceSetting = 32;
    private boolean containsEnemy;
    private int horizontalDistance;

    /**
     * NormalBubble constructor
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param animation current normal bubble animation
     */
    public NormalBubble(int x, int y, Level.Animation animation) {
        super(x, y, Type.NORMAL_BUBBLE, "bubble.png");
        setCurrentAnimation(animation);
        this.horizontalDistance = horizontalDistanceSetting;
        containsEnemy = false;
    }

    /**
     * Check if bubble contains an enemy
     *
     * @return if bubble contains an enemy
     */
    public boolean isContainsEnemy() {
        return containsEnemy;
    }

    /**
     * Set if bubble contains an enemy
     *
     * @param containsEnemy if bubble contains an enemy
     */
    public void setContainsEnemy(boolean containsEnemy) {
        this.containsEnemy = containsEnemy;
    }

    /**
     * Decrease fire delay time
     */
    public void decreaseHorizontalDistanceSetting() {
        horizontalDistance--;
    }

    /**
     * Return fire delay time
     *
     * @return fire delay time
     */
    public int getHorizontalDistanceSetting() {
        return horizontalDistance;
    }

    /**
     * Set new fire delay
     *
     * @param horizontalDistance new fire delay
     */
    public static void setHorizontalDistanceSetting(int horizontalDistance) {
        NormalBubble.horizontalDistanceSetting = horizontalDistance;
    }

    /**
     * Set fireDelay to -1
     */
    public void clearHorizontalDistance() {
        this.horizontalDistance = -1;
    }

}
