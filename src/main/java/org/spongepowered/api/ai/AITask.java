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
package org.spongepowered.api.ai;

import org.spongepowered.api.entity.living.Agent;

public interface AITask {

    /**
     * Gets the mutex that determines if this task can run, concurrently, with another task at the same time.
     *
     * How this is handled is up to the implementation but for Minecraft it performs a bitwise AND between
     * the two AITask objects such that <code>taskA.getMutex() & taskB.getMutex()</code> equaling '0' warrants
     * the tasks running co-currently. If the bitwise operation does not return 0, the task with the
     * "lowest" priority given in {@link Agent#addTask(int, AITask)} will be ran first.
     *
     * @return The mutex
     */
    int getMutex();

    /**
     * Sets the mutex. See {@link AITask#getMutex()}.
     *
     * @param mutex The new mutex
     */
    void setMutex(int mutex);

    /**
     * Returns if this task is interruptible. Is true for all Minecraft standard tasks.
     *
     * @return True if interruptible, false if not
     */
    boolean isInterruptible();
}
