package org.spongepowered.api.ai.creature;

public interface WanderCreatureGoal extends CreatureGoal {

    double getSpeed();

    void setSpeed(double speed);

    int getExecutionChance();

    void setExecutionChance(int executionChance);
}
