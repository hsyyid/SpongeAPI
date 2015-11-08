package org.spongepowered.api.util.weighted;

@FunctionalInterface
public interface SeededValue<V, S> {
    
    public static <V, S> Constant<V, S> constant(V value) {
        return new Constant<V, S>(value);
    }

    V get(S seed);

    public static class Constant<V, S> implements SeededValue<V, S> {

        private final V value;

        private Constant(V v) {
            this.value = v;
        }

        @Override
        public V get(S seed) {
            return this.value;
        }
        
        //TODO Deamon hashCode / equals / toString

    }

}
