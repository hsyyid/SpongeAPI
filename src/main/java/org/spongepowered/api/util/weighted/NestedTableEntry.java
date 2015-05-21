package org.spongepowered.api.util.weighted;

import java.util.List;
import java.util.Random;

public class NestedTableEntry<T, C> extends WeightedTableEntry<T> {

    private final RandomObjectTable<T, C> table;
    
    public NestedTableEntry(double weight, RandomObjectTable<T, C> table) {
        super(weight);
        this.table = table;
    }

    public List<T> get(Random rand, C object) {
        return this.table.get(rand, object);
    }
    
    //TODO Deamon add equals and hashcode

}
