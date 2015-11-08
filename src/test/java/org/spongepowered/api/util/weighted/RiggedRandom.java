package org.spongepowered.api.util.weighted;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class RiggedRandom extends Random {

    private Deque<Double> queue = new ArrayDeque<>();
    
    public RiggedRandom() {
        
    }
    
    public void enqueue(double next) {
        this.queue.add(next);
    }
    
    @Override
    public double nextDouble() {
        if(!this.queue.isEmpty()) {
            return this.queue.pop();
        }
        return super.nextDouble();
    }
    
}
