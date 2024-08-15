package model.behaviour;

import model.entity.objects.consumable.bubble.NormalBubble;

/**
 * Define fire behaviour
 */
@FunctionalInterface
public interface Fire {
    NormalBubble fire();
}
