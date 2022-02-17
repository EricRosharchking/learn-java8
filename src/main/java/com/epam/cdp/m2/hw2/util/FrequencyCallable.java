package com.epam.cdp.m2.hw2.util;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class FrequencyCallable<V> implements Callable {
    private List<String> words;

    public FrequencyCallable(List<String> words){
        this.words = words;
    }

    @Override
    public Map<String, Long> call() throws Exception {
        Map<String, Long> stringLongMap = new HashMap<>();
        for (String word: words) {
            if (stringLongMap.containsKey(word)) {
                stringLongMap.put(word, stringLongMap.get(word) + 1L);
            } else {
                stringLongMap.put(word, 1L);
            }
        }
        return stringLongMap;
    }
}
