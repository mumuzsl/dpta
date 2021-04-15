package com.cqjtu.dpta.common.util;


import java.util.function.Predicate;

/**
 * author: mumu
 * date: 2021/4/14
 */
public final class DptaUtils {
    private DptaUtils() {}

    public static boolean in01(int t) {
        return t >= 0 && t <= 1;
    }

    public static boolean in02(int t) {
        return t >= 0 && t <= 2;
    }

    public static boolean in03(int t) {
        return t >= 0 && t <= 3;
    }

    public static boolean in(int t, int min, int max) {
        return t >= min && t <= max;
    }
}
