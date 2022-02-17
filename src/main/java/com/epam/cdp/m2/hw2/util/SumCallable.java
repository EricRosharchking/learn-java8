package com.epam.cdp.m2.hw2.util;

import java.util.List;
import java.util.concurrent.*;

public class SumCallable<V> implements Callable {
    private List<Integer> numbers;

    public SumCallable(List<Integer> numbers){
        this.numbers = numbers;
    }

    @Override
    public Integer call() throws Exception {
        int res = 0;
        if (numbers == null)
            throw new Exception("Shard is not initialised!");
        for (int i: numbers) {
            res += i;
        }
        return res;
    }
}
