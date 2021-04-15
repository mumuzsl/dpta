package com.cqjtu.dpta.common.lang;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * author: mumu
 * date: 2021/4/14
 */
public class Range {
    int min;
    int max;

    private Range(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean in(Integer target) {
        return target >= min && target <= max;

    }

    public boolean notIn(Integer target) {
        return !in(target);
    }

    public static Range build(int min, int max) {
        return new Range(min, max);
    }
}
