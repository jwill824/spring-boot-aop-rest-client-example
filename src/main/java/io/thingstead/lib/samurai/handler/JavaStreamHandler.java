package io.thingstead.lib.samurai.handler;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaStreamHandler {
    static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        Iterator<K> keysIterator = keys.iterator();
        Iterator<V> valsIterator = values.iterator();
        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(index -> keysIterator.next(), index -> valsIterator.next()));
    }

    public static String[] convertCollectionToStringArray(Collection<List<String>> values) {
        return values.stream().map(String::valueOf).toArray(String[]::new);
    }

    public static String[] convertSetToStringArray(Set<String> values) {
        return values.stream().map(String::valueOf).toArray(String[]::new);
    }
}
