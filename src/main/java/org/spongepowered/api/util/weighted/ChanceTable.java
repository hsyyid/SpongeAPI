package org.spongepowered.api.util.weighted;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class ChanceTable<T, C> extends RandomObjectTable<T, C> {

    public ChanceTable(int rolls) {
        super(rolls);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> get(Random rand, C object) {
        List<T> results = Lists.newArrayList();
        if(!validate(object)) {
            return results;
        }
        for (int i = 0; i < getRolls(); i++) {
            for (Iterator<WeightedTableEntry<T>> it = this.entries.iterator(); it.hasNext();) {
                WeightedTableEntry<T> next = it.next();
                if (rand.nextDouble() < next.getWeight()) {
                    if (next instanceof NestedTableEntry) {
                        results.addAll(((NestedTableEntry<T, C>) next).get(rand, object));
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
