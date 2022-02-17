package com.epam.cdp.m2.hw2.util;

import javafx.util.Pair;

import java.util.Comparator;

public class PairComparator<T> implements Comparator<T> {
    @Override
    public int compare(Object o1, Object o2) {
        Pair<String, Long> p1 = (Pair<String, Long>)o1;
        Pair<String, Long> p2 = (Pair<String, Long>)o2;
        if (p1.getValue() == p2.getValue()) {
            return p1.getKey().compareTo(p2.getKey());
        }
        return p2.getValue().compareTo(p1.getValue());
    }
}
