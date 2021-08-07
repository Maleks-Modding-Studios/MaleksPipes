package net.malek.pipes;

@FunctionalInterface
public interface TriPredicate<T, V, K> {
    boolean accept(T t, V v, K k);
}
