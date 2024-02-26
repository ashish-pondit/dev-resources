package org.tutorial.chapter9.Example2;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ToupleDemo {
    public static void main(String[] args){
        Touple<String, String> touple = new Touple<>("Hello", "World");
        touple.showTypes();

        // Example 2
        Touple<String, Integer> person = new Touple<>("Rakib", 59);
        person.showTypes();

        // Touple inside touple
        Touple<String, Touple<Integer, Integer>> nestedTouple= new Touple<String, Touple<Integer, Integer>>("Nested touple generic", new Touple<>(66,77));
        nestedTouple.showTypes();

        // nested map
        Map<String, List<String>> anagrams = new HashMap<String, List<String>>();
        // using diamond operator
        Map<String, List<String>> anagram2 = new HashMap<>();
    }
}
