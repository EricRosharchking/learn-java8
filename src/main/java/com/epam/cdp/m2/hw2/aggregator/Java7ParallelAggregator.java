package com.epam.cdp.m2.hw2.aggregator;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.epam.cdp.m2.hw2.util.*;
import javafx.util.Pair;

public class Java7ParallelAggregator implements Aggregator {

    private final int SHARD_SIZE = 100;

    @Override
    public int sum(List<Integer> numbers) {
        int lot = 0;
        int res = 0;
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futureList = new ArrayList<>();
        while(lot * SHARD_SIZE < numbers.size()) {
            SumCallable<Integer> callable;
            if (lot * SHARD_SIZE + SHARD_SIZE >= numbers.size()) {
                callable = new SumCallable<>(numbers.subList(lot++ * SHARD_SIZE, numbers.size()));
            } else {
                callable = new SumCallable<>(numbers.subList(lot * SHARD_SIZE, ++lot * SHARD_SIZE));
            }
            futureList.add((Future<Integer>) executor.submit(callable));
        }
        try {
            for (Future<Integer> f: futureList) {
                res += f.get();
            }
        } catch (Exception ie) {
            ie.printStackTrace();
        } finally {
            if (!executor.isShutdown())
                executor.shutdown();
        }
        return res;
    }

    @Override
    public List<Pair<String, Long>> getMostFrequentWords(List<String> words, long limit) {
        int lot = 0;
        Map<String, Long> stringLongMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<Map<String, Long>>> futureList = new ArrayList<>();
        while(lot * SHARD_SIZE < words.size()) {
            FrequencyCallable<List<Pair<String, Long>>> callable;
            if (lot * SHARD_SIZE + SHARD_SIZE >= words.size()) {
                callable = new FrequencyCallable<>(words.subList(lot++ * SHARD_SIZE, words.size()));
            } else {
                callable = new FrequencyCallable<>(words.subList(lot * SHARD_SIZE, ++lot * SHARD_SIZE));
            }
            futureList.add((Future<Map<String, Long>>) executor.submit(callable));
        }
        try {
            for (Future<Map<String, Long>> f: futureList) {
                Map<String, Long> m = f.get();
                for(String word: m.keySet()) {
                    if(stringLongMap.containsKey(word)) {
                        stringLongMap.put(word, stringLongMap.get(word) + m.get(word));
                    } else {
                        stringLongMap.put(word, m.get(word));
                    }
                }
            }
        } catch (Exception ie) {
            ie.printStackTrace();
        } finally {
            if (!executor.isShutdown())
                executor.shutdown();
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
        int lot = 0;
        List<String> capWords = new ArrayList<>();
        for (String word: words) {
            capWords.add(word.toUpperCase());
        }
        List<Pair<String, Long>> tempList = getMostFrequentWords(capWords, capWords.size());
        List<String> res = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<List<String>>> futureList = new ArrayList<>();
        while(lot * SHARD_SIZE < capWords.size()) {
            DuplicatesCallable<Integer> callable;
            if (lot * SHARD_SIZE + SHARD_SIZE >= tempList.size()) {
                callable = new DuplicatesCallable<>(tempList.subList(lot++ * SHARD_SIZE, tempList.size()));
            } else {
                callable = new DuplicatesCallable<>(tempList.subList(lot * SHARD_SIZE, ++lot * SHARD_SIZE));
            }
            futureList.add((Future<List<String>>) executor.submit(callable));
        }
        try {
            for (Future<List<String>> f: futureList) {
                res.addAll(f.get());
            }
        } catch (Exception ie) {
            ie.printStackTrace();
        } finally {
            if (!executor.isShutdown())
                executor.shutdown();
        }

        Comparator<String> comparator = new StringComparator<>();
        res.sort(comparator);
        if (limit < res.size()) {
            res = res.subList(0, (int)limit);
        }
        return res;
    }
}
