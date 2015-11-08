/*
 * This file is part of SpongeAPI, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.api.entity.living;

import org.spongepowered.api.entity.ai.AITask;
import org.spongepowered.api.data.manipulator.mutable.entity.AgentData;

import java.util.Set;

/**
 * An Agent represents a {@link Living} that has AI. In the future Sponge will
 * allow for custom AIs, but for now vanilla behavior can only be disabled.
 */
public interface Agent extends Living {

    /**
     * Gets a copy of the {@link AgentData} associated with this {@link Agent}.
     *
     * @return A copy of the agent data
     */
    default AgentData getAgentData() {
        return get(AgentData.class).get();
    }

    /**
     * Adds a {@link AITask} for this agent with a provided priority.
     *
     * @param priority The priority
     * @param AITask The AITask
     * @return This agent, for chaining
     */
    Agent addTask(int priority, AITask AITask);

    Agent removeTask(AITask task);

    // TODO Types, replace classes
    Agent removeTasks(Class<? extends AITask> clazz);

    <T extends AITask> Set<T> getTasks(Class<T> clazz);
}
