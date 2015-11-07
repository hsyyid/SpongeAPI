package org.spongepowered.api.ai.creature;

import org.spongepowered.api.ai.Goal;
import org.spongepowered.api.entity.living.Creature;

public interface CreatureGoal extends Goal {
    Creature getOwner();
}
