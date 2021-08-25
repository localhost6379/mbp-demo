package cn.king.demo01.util;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 公共转换类，提供通用转换能力
 *
 */
public class CommonConverterUtils {
    public static <K, E> List<K> toList(Collection<E> collection, Function<E, K> ekFunction) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream().map(ekFunction).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <K, E> List<K> toDistinctList(Collection<E> collection, Function<E, K> ekFunction) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return collection.stream().map(ekFunction).distinct().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public static <K, E> Map<K, E> toMap(Collection<E> collection, Function<E, K> keyFunction) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toMap(keyFunction, Function.identity(), (o, n) -> n));
    }

    public static <K, E, V> Map<K, V> toMap(Collection<E> collection, Function<E, K> keyFunction, Function<E, V> valueFunction) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toMap(keyFunction, valueFunction, (o, n) -> n));
    }

    public static <K, E> Map<K, List<E>> toMapList(Collection<E> collection, Function<E, K> groupFunction) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(groupFunction));
    }

    public static <K, E, V> Map<K, List<V>> toMapList(Collection<E> collection, Function<E, K> groupFunction, Function<E, V> listFunction) {
        if (CollectionUtils.isEmpty(collection)) {
            return Maps.newHashMap();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(groupFunction, Collectors.mapping(listFunction, Collectors.toList())));
    }

    public static List subList(Collection collection, int start, int end) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        end = Math.min(end, collection.size());
        return (List) collection.stream().filter(Objects::nonNull).skip(start).limit(end).collect(Collectors.toList());
    }
}