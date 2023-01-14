package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Mixing {

    class MixingEntry {
        int position;
        char name;
        int value;
        boolean same;
    }

    public String mixx(String s1, String s2) {

        // your code
        var map1 = new LinkedHashMap<Character,Integer>();
        var map2 = new LinkedHashMap<Character,Integer>();

        for (char c : s1.toCharArray()) {

            if (isValidChar(c)) {
                if (map1.containsKey(c)) {
                    map1.put(c, map1.get(c) + 1);
                }else {
                    map1.put(c, 1);
                }
            }
        }

        for (char c : s2.toCharArray()) {

            if (isValidChar(c)) {
                System.out.println(c);
                if (map2.containsKey(c)) {
                    System.out.println(String.format("%s char, current val: %s", c, map2.get(c)));
                    map2.put(c, map2.get(c) + 1);
                }else {
                    map2.put(c, 1);
                }
            }
        }

        Map<Character, MixingEntry> combined = new LinkedHashMap<>();
        map1.forEach((k,v) -> {
            var t = new MixingEntry();
            t.name = k;
            t.value = v;
            t.position = 1;
            t.same = false;

            combined.put(k, t);
        });
        map2.forEach((k,v) -> {

            if (combined.containsKey(k)) {
                var m = combined.get(k);
                if (m.value < v) {
                    m.value = v;
                    m.position = 2;
                }else if (m.value == v) {
                    m.same = true;
                }
                combined.put(k,m);
            }else {
                var t = new MixingEntry();
                t.name = k;
                t.value = v;
                t.position = 1;
                t.same = false;

                combined.put(k, t);
            }

        });

        var mainBuilder = new StringBuilder();


        System.out.println(combined.values());

        combined.values().stream().sorted((a,b) -> {
            if (a.value == b.value) {

                if (!a.same && !b.same && (a.position != b.position)) {
                    return Integer.compare(a.position, b.position);
                }

                if (!a.same || !b.same) {
                    return Boolean.compare(a.same, b.same);
                }

                return String.valueOf(a.name).compareTo(String.valueOf(b.name));
            }
            return a.value > b.value ? -1 : 1;
        }).forEach(v -> {
            if (v.value > 1) {
                var b = new StringBuilder();
                var prefix = v.same ? "=:" : ":";
                var number = v.same ? "" : String.valueOf(v.position);
                b.append(String.format("%s%s", number, prefix));

                for ( var i = 0; i < v.value; i++) {
                    b.append(v.name);
                }
                b.append("/");
                mainBuilder.append(b);
            }
        });

        return mainBuilder.substring(0,mainBuilder.length() - 1);
    }

    public static String mix(String s1, String s2) {
        return new Mixing().mixx(s1,s2);
    }

    private static boolean isValidChar(char c) {
        return Character.isAlphabetic(c) && Character.isLowerCase(c);
    }
}
