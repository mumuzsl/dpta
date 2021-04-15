package com.cqjtu.dpta.common.util;

import java.util.function.Consumer;

/**
 * author: mumu
 * date: 2021/4/14
 */
public interface Option {
    <T> void run(T t, Consumer<T> consumer);
}
