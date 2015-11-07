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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public abstract class ConditionalRandomObjectTable<T, C> implements Collection<WeightedTableEntry<T>> {

    protected final List<WeightedTableEntry<T>> entries = Lists.newArrayList();
    protected final List<Predicate<C>> conditions = Lists.newArrayList();
    private int rolls;

    public ConditionalRandomObjectTable(int rolls) {
        this.rolls = rolls;
    }

    public int getRolls() {
        return this.rolls;
    }

    public void setRolls(int r) {
        this.rolls = r;
    }

    @Override
    public boolean add(WeightedTableEntry<T> entry) {
        return this.entries.add(entry);
    }

    @Override
    public boolean addAll(Collection<? extends WeightedTableEntry<T>> c) {
        return this.entries.retainAll(c);
    }

    @Override
    public boolean contains(Object o) {
        return this.entries.contains(o);
    }

    public boolean containsObject(Object o) {
        boolean entry = this.entries.contains(o);
        if (entry) {
            return true;
        }
        for (WeightedTableEntry<T> e : this.entries) {
            if (e instanceof WeightedObject && ((WeightedObject<T>) e).get().equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.entries.containsAll(c);
    }
    
    public boolean containsAllObjects(Collection<?> c) {
        for(Object e: c) {
            if(!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    @Override
    public boolean remove(Object entry) {
        return this.entries.remove(entry);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.entries.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.entries.retainAll(c);
    }

    @Override
    public void clear() {
        this.entries.clear();
    }

    @Override
    public int size() {
        return this.entries.size();
    }

    public void addCondition(Predicate<C> condition) {
        this.conditions.add(condition);
    }

    public boolean validate(C object) {
        for (Predicate<C> condition : this.conditions) {
            if (!condition.test(object)) {
                return false;
            }
        }
        return true;
    }

    public abstract List<T> get(Random rand, C object);

    public List<WeightedTableEntry<T>> getEntries() {
        return ImmutableList.copyOf(this.entries);
    }

    @Override
    public Iterator<WeightedTableEntry<T>> iterator() {
        return this.entries.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.entries.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.entries.toArray(a);
    }
    
    //TODO Deamon add equals and hashcode

}
