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
package org.spongepowered.api.entity.ai;

import org.spongepowered.api.entity.living.Agent;

public abstract class AbstractAITask<T extends Agent> implements AITask {
    private int mutex;
    private boolean interruptible;

    public AbstractAITask(int mutex, boolean interruptible) {
        this.mutex = mutex;
        this.interruptible = interruptible;
    }

    @Override
    public int getMutex() {
        return mutex;
    }

    @Override
    public void setMutex(int mutex) {
        this.mutex = mutex;
    }

    @Override
    public boolean isInterruptible() {
        return interruptible;
    }

    public abstract void assignedTo(T owner);

    public abstract boolean shouldUpdate();

    public abstract boolean continueUpdating();

    public abstract void start();

    public abstract void update();

    public abstract void reset();
}