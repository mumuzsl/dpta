package com.cqjtu.dpta.common.util;

import java.util.function.Consumer;

/**
 * author: mumu
 * date: 2021/4/14
 */
public enum Status {

    INSELL(1),
    OUTSELL(0),
    DISABLE(0),
    ENABLE(1);

    int value;

    Status(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public void set(Consumer<Integer> consumer) {
        consumer.accept(value);
    }
}
