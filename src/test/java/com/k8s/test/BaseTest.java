package com.k8s.test;

import com.google.common.collect.Maps;

import com.kubernetes.client.util.Serialization;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseTest {

    @Test
    public void test1() {
        log.info("{}", 111);
    }

    @Test
    public void test2() {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("HELLO", "111");

        String content = map.entrySet().stream().flatMap(entry -> {
            final String key = entry.getKey();
            final String value = entry.getValue();
            return Stream.of(
                    new AbstractMap.SimpleEntry<>("${" + key + "}", value),
                    new AbstractMap.SimpleEntry<>("\"${{" + key + "}}\"", value),
                    new AbstractMap.SimpleEntry<>("${{" + key + "}}", value)
            );
        }).map(explodedParam -> {
            return (Function<String, String>) s -> s.replace(explodedParam.getKey(), explodedParam.getValue());
        }).reduce(Function.identity(), Function::andThen).apply("${HELLO} 222");

        log.info("{}", content);
    }

    @Test
    public void test3() {
        Map<String, String> map = Maps.newConcurrentMap();
        map.put("HELLO", "111");

        log.info("{}", Serialization.asYaml(map));
    }

    @Test
    public void test4() {
        Map<String, Object> map = Maps.newConcurrentMap();

        Map<String, Object> tmap = Maps.newHashMap();
        tmap.put("Hello", 1);
        tmap.put("Word", "222");

        map.put("T", tmap);

        log.info("{}", Serialization.asYaml(map));
    }
}
