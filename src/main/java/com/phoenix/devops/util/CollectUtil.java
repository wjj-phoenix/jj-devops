package com.phoenix.devops.util;

import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author wjj-phoenix
 * @since 2025-03-10
 */
public final class CollectUtil {
    public static <E> ArrayList<E> newArrayList(@NonNull E... elements) {
        int capacity = computeArrayListCapacity(elements.length);
        ArrayList<E> list = new ArrayList<>(capacity);
        Collections.addAll(list, elements);
        return list;
    }

    static int computeArrayListCapacity(int arraySize) {
        if (arraySize < 0) {
            throw new IllegalArgumentException("arraySize cannot be negative but was: " + arraySize);
        }
        return saturatedCast(5L + (long)arraySize + (long)(arraySize / 10));
    }


    public static int saturatedCast(long value) {
        if (value > 2147483647L) {
            return Integer.MAX_VALUE;
        } else {
            return value < -2147483648L ? Integer.MIN_VALUE : (int)value;
        }
    }
}
