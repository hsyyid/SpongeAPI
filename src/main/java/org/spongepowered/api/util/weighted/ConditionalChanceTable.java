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
package org.spongepowered.api.util.weighted;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ConditionalChanceTable<T, C> extends ConditionalRandomObjectTable<T, C> {

    public ConditionalChanceTable(int rolls) {
        super(rolls);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> get(Random rand, C object) {
        List<T> results = Lists.newArrayList();
        if (!validate(object)) {
            return results;
        }
        for (int i = 0; i < getRolls(); i++) {
            for (Iterator<TableEntry<T>> it = this.entries.iterator(); it.hasNext();) {
                TableEntry<T> next = it.next();
                if (rand.nextDouble() < next.getWeight()) {
                    if (next instanceof NestedTableEntry) {
                        results.addAll(((ConditionalNestedTableEntry<T, C>) next).get(rand, object));
                    } else if (next instanceof WeightedObject) {
                        results.add(((WeightedObject<T>) next).get());
                    }
                }
            }
        }
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConditionalChanceTable)) {
            return false;
        }
        ConditionalChanceTable<?, ?> c = (ConditionalChanceTable<?, ?>) o;
        if (getRolls() != c.getRolls()) {
            return false;
        }
        if (this.entries.size() != c.entries.size()) {
            return false;
        }
        for (int i = 0; i < this.entries.size(); i++) {
            if (!this.entries.get(i).equals(c.entries.get(i))) {
                return false;
            }
        }
        if (this.conditions.size() != c.conditions.size()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int r = 1;
        r = r * 37 + getRolls();
        for (TableEntry<T> entry : this.entries) {
            r = r * 37 + entry.hashCode();
        }
        r = r * 37 + this.conditions.size();
        return r;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append("ConditionalChanceTable (rolls=").append(getRolls());
        r.append(",entries=").append(this.entries.size()).append(") {\n");
        for (TableEntry<T> entry : this.entries) {
            r.append("\t").append(entry.toString()).append("\n");
        }
        r.append("}");
        return r.toString();
    }
}
