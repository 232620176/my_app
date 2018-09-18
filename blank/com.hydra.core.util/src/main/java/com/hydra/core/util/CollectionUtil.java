package com.hydra.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class CollectionUtil {
    public static int computeArrayListCapacity(int arraySize) {
        Preconditions.checkArgument(arraySize >= 0);
        return NumberUtil.long2Int(5L + arraySize + arraySize / 10);
    }

    public static <T> T get(Collection<T> collection, int index) {
        int max = collection.size();
        if (index > max || index < 0) {
            index = 0;
        }
        Iterator<T> it = collection.iterator();
        if (0 == index) {
            return it.next();
        }
        int idx = 0;
        while (idx++ < index - 1 && it.hasNext()) {
            it.next();
        }
        return it.next();
    }

    public static <E> List<E> newArrayList() {
        return new ArrayList<E>();
    }

    public static <E> List<E> newArrayList(int size) {
        return new ArrayList<E>(size);
    }

    public static <T> T getRandom(Collection<T> collection) {
        int len = collection.size();
        int index = RandomUtil.get().nextInt(len);
        return get(collection, index);
    }

    // 静态工具类，防误生成
    private CollectionUtil() {
        throw new UnsupportedOperationException();
    }
}
