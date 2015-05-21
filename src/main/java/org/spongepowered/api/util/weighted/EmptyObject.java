package org.spongepowered.api.util.weighted;


public class EmptyObject<T> extends WeightedTableEntry<T> {

    public EmptyObject(double weight) {
        super(weight);
    }
    
    //TODO Deamon add equals and hashcode
}
