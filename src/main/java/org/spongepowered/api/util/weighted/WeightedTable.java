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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class WeightedTable<T> extends RandomObjectTable<T> {

    private double totalWeight = 0;

    public WeightedTable() {
        super(1);
    }

    public WeightedTable(int rolls) {
        super(rolls);
    }

    @Override
    public boolean add(WeightedTableEntry<T> entry) {
        boolean added = super.add(entry);
        if (added) {
            recalculateWeight();
        }
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends WeightedTableEntry<T>> c) {
        boolean added = super.retainAll(c);
        if (added) {
            recalculateWeight();
        }
        return added;
    }

    @Override
    public boolean remove(Object entry) {
        boolean removed = super.remove(entry);
        if (removed) {
            recalculateWeight();
        }
        return removed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = super.removeAll(c);
        if (removed) {
            recalculateWeight();
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean removed = super.retainAll(c);
        if (removed) {
            recalculateWeight();
        }
        return removed;
    }

    @Override
    public void clear() {
        super.clear();
        recalculateWeight();
    }

    protected void recalculateWeight() {
        this.totalWeight = 0;
        for (WeightedTableEntry<T> entry : getEntries()) {
            this.totalWeight += entry.getWeight();
        }
    }

    @Override
    public List<T> get(Random rand) {
        List<T> results = Lists.newArrayList();
        for (int i = 0; i < getRolls(); i++) {
            double roll = rand.nextDouble() * this.totalWeight;
            for (Iterator<WeightedTableEntry<T>> it = this.entries.iterator(); it.hasNext();) {
                WeightedTableEntry<T> next = it.next();
                roll -= next.getWeight();
                if (roll <= 0) {
                    if (next instanceof NestedTableEntry) {
                        results.addAll(((NestedTableEntry<T>) next).get(rand));
                    } else if (next instanceof WeightedObject) {
                        results.add(((WeightedObject<T>) next).get());
                    }
                    break;
                }
            }
        }
        return results;
    }

    @Override
    public Iterator<WeightedTableEntry<T>> iterator() {
        return new Itr();
    }
    
    //TODO Deamon add equals and hashcode

    /**
     * An iterator which will properly trigger a rebuild of the total weight on
     * removal.
     */
    private class Itr implements Iterator<WeightedTableEntry<T>> {

        private final Iterator<WeightedTableEntry<T>> iter;

        protected Itr() {
            this.iter = WeightedTable.super.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public WeightedTableEntry<T> next() {
            return this.iter.next();
        }

        @Override
        public void remove() {
            this.iter.remove();
            WeightedTable.this.recalculateWeight();
        }

    }
}
