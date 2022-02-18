package com.epam.cdp.m2.hw2.aggregator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.epam.cdp.m2.hw2.util.PairComparator;
import com.epam.cdp.m2.hw2.util.StringComparator;
import javafx.util.Pair;

public class Java8Aggregator implements Aggregator {

    @Override
    public int sum(List<Integer> numbers) {
        return numbers.stream().mapToInt(x -> x).sum();
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        List<Pair<String, Long>> res = new ArrayList<>();
        Map<String, Long> stringCountMap = words.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (String word: stringCountMap.keySet()) {
            res.add(new Pair(word, stringCountMap.get(word)));
        }
        PairComparator<Pair<String, Long>> comparator = new PairComparator<>();
        res = res.stream().sorted(comparator).limit(limit).collect(Collectors.toList());
        return res;
    }

    @Override
    public List<String> getDuplicates(List<String> words, long limit) {
        List<Pair<String, Long>> tmp = new ArrayList<>();
        List<String> res = new ArrayList<>();
        Map<String, Long> map = words.stream().map(x -> x.toUpperCase()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (String word: map.keySet()) {
            tmp.add(new Pair(word, map.get(word)));
        }
        StringComparator<String> comparator = new StringComparator<>();
        res = tmp.stream().filter(x -> x.getValue() > 1).map(x -> x.getKey()).sorted(comparator).limit(limit).collect(Collectors.toList());
        return res;
    }
}