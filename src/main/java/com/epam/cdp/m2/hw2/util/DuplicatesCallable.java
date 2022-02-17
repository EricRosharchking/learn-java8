package com.epam.cdp.m2.hw2.util;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DuplicatesCallable<V> implements Callable {
    private List<Pair<String, Long>> list;

    public DuplicatesCallable(List<Pair<String, Long>> list){
        this.list = list;
    }

    @Override
    public List<String> call() throws Exception {
        List<String> res = new ArrayList<>();
        for (Pair<String, Long> p: list) {
            if (p.getValue() > 1) {
                res.add(p.getKey());
            }
        }
        return res;
    }
}
