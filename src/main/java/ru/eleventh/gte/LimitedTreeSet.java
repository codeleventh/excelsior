package ru.eleventh.gte;

import lombok.AllArgsConstructor;

import java.util.TreeSet;

@AllArgsConstructor
public class LimitedTreeSet<T extends Comparable<T>> extends TreeSet<T> {

    private final int maxSize;

    @Override
    public boolean add(T element) {
        if (size() < maxSize) {
            return super.add(element);
        } else if (element.compareTo(first()) > 0) {
            super.add(element);
            remove(first());
        }
        return true; // We don't care about this value actually
    }
}
