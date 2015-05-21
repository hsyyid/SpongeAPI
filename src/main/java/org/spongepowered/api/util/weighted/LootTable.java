package org.spongepowered.api.util.weighted;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class LootTable<T, C> {

    private List<RandomObjectTable<T, C>> pools = new ArrayList<>();

    public LootTable() {

    }

    public void addPool(RandomObjectTable<T, C> pool) {
        this.pools.add(pool);
    }

    public List<T> get(Random rand, C object) {
        List<T> results = Lists.newArrayList();
        for (RandomObjectTable<T, C> pool : pools) {
            results.addAll(pool.get(rand, object));
        }
        return results;
    }
    
    //TODO Deamon add equals and hashcode

}