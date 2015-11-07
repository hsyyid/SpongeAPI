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

public class ChanceTable<T> extends RandomObjectTable<T> {

    public ChanceTable() {
        super(1);
    }

    public ChanceTable(int rolls) {
        super(rolls);
    }

    @Override
    public List<T> get(Random rand) {
        List<T> results = Lists.newArrayList();
        for (int i = 0; i < getRolls(); i++) {
            for (Iterator<WeightedTableEntry<T>> it = this.entries.iterator(); it.hasNext();) {
                WeightedTableEntry<T> next = it.next();
                if (rand.nextDouble() < next.getWeight()) {
                    if (next instanceof NestedTableEntry) {
                        results.addAll(((NestedTableEntry<T>) next).get(rand));
                    } else if (next instanceof WeightedObject) {
                        results.add(((WeightedObject<T>) next).get());
                    }
                }
            }
        }
        return results;
    }
    
    //TODO Deamon add equals and hashcode
}
