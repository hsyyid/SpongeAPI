package org.spongepowered.api.util.weighted;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class WeightedTable<T, C> extends RandomObjectTable<T, C> {

    private double totalWeight = 0;

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
    @SuppressWarnings("unchecked")
    public List<T> get(Random rand, C object) {
        List<T> results = Lists.newArrayList();
        if (!validate(object)) {
            return results;
        }
        for (int i = 0; i < getRolls(); i++) {
            double roll = rand.nextDouble() * this.totalWeight;
            for (Iterator<WeightedTableEntry<T>> it = this.entries.iterator(); it.hasNext();) {
                WeightedTableEntry<T> next = it.next();
                roll -= next.getWeight();
                if (roll <= 0) {
                    if (next instanceof NestedTableEntry) {
                        results.addAll(((NestedTableEntry<T, C>) next).get(rand, object));
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
