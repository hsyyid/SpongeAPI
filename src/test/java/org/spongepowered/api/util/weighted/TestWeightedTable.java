package org.spongepowered.api.util.weighted;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestWeightedTable {

    @Test
    public void test() {
        RiggedRandom rand = new RiggedRandom();
        WeightedTable<Object> table = new WeightedTable<>();
        table.add(new WeightedObject<Object>(this, 4));
        rand.enqueue(0);
        List<Object> results = table.get(rand);
        Assert.assertEquals(this, results.get(0));
    }

}
