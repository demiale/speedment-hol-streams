package com.speedment.example.solution;

import com.speedment.example.unit.Unit3Terminal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Unit3MyTerminal implements Unit3Terminal {

    @Override
    public void addToSet(Stream<String> stream, Set<String> set) {
        set.addAll(stream.collect(Collectors.toSet()));

    }

    @Override
    public void addToListInOrder(Stream<String> stream, List<String> list) {
        list.addAll(stream.collect(Collectors.toList()));

    }

    @Override
    public Set<String> collectToSet(Stream<String> stream) {
        return stream.collect(Collectors.toSet());
    }

    @Override
    public List<String> collectToList(Stream<String> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public String collectJoining(Stream<String> stream) {
        return stream.collect(Collectors.joining("->"));
    }

    @Override
    public String[] toArray(Stream<String> stream) {
        return stream.toArray(String[]::new);
    }

    @Override
    public Map<String, Integer> collectToMap(Stream<String> stream) {
        return stream.collect(Collectors.toMap(str -> str, String::length));
    }

    @Override
    public int countCharacters(Stream<String> stream) {
        return stream.mapToInt(String::length).sum();
    }

    @Override
    public int maxWordLen(Stream<String> stream) {
        return stream.mapToInt(String::length).max().orElse(0);
    }

    @Override
    public IntSummaryStatistics statistics(Stream<String> stream) {
        return stream.collect(Collectors.summarizingInt(String::length));
    }

    @Override
    public Optional<String> findLongestString(Stream<String> stream) {
        return stream.max(Comparator.comparing(String::length));
    }

    @Override
    public Map<Integer, List<String>> wordsGroupedByLength(Stream<String> stream) {
        return stream.collect(Collectors.groupingBy(String::length));
    }

    @Override
    public Map<Integer, Long> wordsGroupedByLengthCounted(Stream<String> stream) {
        return stream.collect(Collectors.groupingBy(String::length, Collectors.counting()));
    }

}
