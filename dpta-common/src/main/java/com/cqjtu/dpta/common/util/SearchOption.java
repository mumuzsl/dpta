package com.cqjtu.dpta.common.util;

import org.springframework.http.HttpStatus;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * author: mumu
 * date: 2021/4/14
 */
public enum SearchOption implements Option {
    NAME(0, "按名称搜索"){
        @Override
        public <T> void run(T t, Consumer<T> consumer) {
            super.run(t, consumer);
        }
    },
    STATUS(1, "按状态搜索"),
    TYPE(2, "按类型搜索");

    int value;
    String message;

    SearchOption(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public String message() {
        return message;
    }

    public int value() {
        return value;
    }

    public <T> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    public static SearchOption valueOf(int optionCode) {
        SearchOption option = resolve(optionCode);
        if (option == null) {
            throw new IllegalArgumentException("No matching constant for [" + optionCode + "]");
        }
        return option;
    }

    public static SearchOption resolve(int optionCode) {
        for (SearchOption option : values()) {
            if (option.value == optionCode) {
                return option;
            }
        }
        return null;
    }
}
