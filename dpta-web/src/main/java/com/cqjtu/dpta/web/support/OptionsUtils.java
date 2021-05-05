package com.cqjtu.dpta.web.support;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * author: mumu
 * date: 2021/5/1
 */
public class OptionsUtils {

    public static <E> Options<E> of(E value, String label) {
        return new Options<E>(value, label);
    }

    public static <T, E> List<Options<T>> list(List<E> list,
                                               Function<E, T> etFunction,
                                               Function<E, String> eStringFunction) {
        ArrayList<Options<T>> rList = new ArrayList<>(list.size());
        for (E e : list) {
            rList.add(of(etFunction.apply(e), eStringFunction.apply(e)));
        }
        return rList;
    }

    public static <T, E> List<Options<T>> list(Supplier<List<E>> listSupplier,
                                               Function<E, T> etFunction,
                                               Function<E, String> eStringFunction) {
        List<E> list = listSupplier.get();
        ArrayList<Options<T>> rList = new ArrayList<>(list.size());
        for (E e : list) {
            rList.add(of(etFunction.apply(e), eStringFunction.apply(e)));
        }
        return rList;
    }
}
