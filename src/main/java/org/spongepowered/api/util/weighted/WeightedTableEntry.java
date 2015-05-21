package org.spongepowered.api.util.weighted;

public abstract class WeightedTableEntry<T> {
    private final double weight;

    public WeightedTableEntry(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }
    
    //TODO Deamon add equals and hashcode
}