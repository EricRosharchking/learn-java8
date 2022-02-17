package com.epam.cdp.m2.hw2.aggregator;

import java.util.*;

import com.epam.cdp.m2.hw2.util.PairComparator;
import com.epam.cdp.m2.hw2.util.StringComparator;
import javafx.util.Pair;

public class Java7Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        int res = 0;
        for (int i: numbers) {
            res += i;
        }
        return res;
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        Map<String, Long> stringLongMap = new HashMap<>();
        for (String word: words) {
            if (stringLongMap.containsKey(word)) {
                stringLongMap.put(word, stringLongMap.get(word) + 1L);
            } else {
                stringLongMap.put(word, 1L);
            }
        }
        PairComparator<Pair<String, Long>> comparator = new PairComparator<>();
        List<Pair<String, Long>> sortedPairs = new ArrayList<>();
        for (String word: stringLongMap.keySet()) {
            sortedPairs.add(new Pair<>(word, stringLongMap.get(word)));
        }
        sortedPairs.sort(comparator);
        if (limit < sortedPairs.size()) {
            sortedPairs = sortedPairs.subList(0, (int)(limit));
        }
        return sortedPairs;
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        Set<String> hashSet = new HashSet<>();
        List<String> results = new ArrayList<>();
        for (String word: words) {
            if (hashSet.contains(word.toUpperCase())) {
                results.add(word.toUpperCase());
            } else {
                hashSet.add(word.toUpperCase());
            }
        }
        Comparator<String> comparator = new StringComparator<>();
        results.sort(comparator);
        if (limit < results.size()) {
            results = results.subList(0, (int)limit);
        }
        return results;
    }
}
