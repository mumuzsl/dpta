package com.cqjtu.dpta.common.util;


import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.cqjtu.dpta.common.exception.BadRequestException;
import com.cqjtu.dpta.common.exception.BaseException;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * author: mumu
 * date: 2021/4/14
 */
public final class DptaUtils {
    private static final String DEFAULT_ORDER_MAP_KEY = "order_id";

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

    public static <T extends BaseException> void exception(Supplier<T> supplier) {
        throw supplier.get();
    }

    private static final Snowflake DEFAUTL_SNOWFLAKE = IdUtil.createSnowflake(0, 0);

    public static String defautlNextIdStr() {
        return DEFAUTL_SNOWFLAKE.nextIdStr();
    }

    public static long defautlNextId() {
        return DEFAUTL_SNOWFLAKE.nextId();
    }

    public static long getOrderId(Map<String, ?> map) {
        return getOrderId(map, DEFAULT_ORDER_MAP_KEY);
    }

    public static long getOrderId(Map<String, ?> map, String key) {
        return Long.parseLong(map.get(key).toString());
    }

    public static String fillMonthStr(int month) {
        String s = String.valueOf(month);
        return s.length() < 2 ? StrUtil.fillBefore(s, '0', 2) : s;
    }


    public static LocalDate nowMinusDays(int i) {
        return LocalDate.now().minusDays(i);
    }

    public static String nowMinusDaysStr(int i) {
        return LocalDateTimeUtil.formatNormal(nowMinusDays(i));
    }

    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        return localDate.atTime(0, 0, 0, 0);
    }

    public static <R> R oneDay(LocalDate date, BiFunction<LocalDateTime, LocalDateTime, R> function) {
        LocalDate end = date.plusDays(1);
        return function.apply(toLocalDateTime(date), toLocalDateTime(end));
    }

    public static <R> R nowMinusDays(int i, BiFunction<LocalDateTime, LocalDateTime, R> function) {
        return oneDay(nowMinusDays(i), function);
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BadRequestException(message);
        }
    }
}

