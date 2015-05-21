package org.spongepowered.api.util.weighted;

public class WeightedObject<T> extends WeightedTableEntry<T> {

    private final T object;

    public WeightedObject(T obj, double weight) {
        super(weight);
        this.object = obj;
    }

    public T get() {
        return this.object;
    }

    
    //TODO Deamon add equals and hashcode
}