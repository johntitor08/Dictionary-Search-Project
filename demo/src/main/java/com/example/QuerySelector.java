package com.example;

import java.util.Random;

public class QuerySelector {
    public static String[] select(String[] words, int numPresent, int numAbsent, long seed) {
        int n = words.length;
        if (numPresent > n)
            numPresent = n;
        String[] queries = new String[numPresent + numAbsent];
        Random rng = new Random(seed);
        // select present words: pick evenly across array
        for (int i = 0; i < numPresent; i++) {
            // random index
            int idx = Math.abs(rng.nextInt()) % n;
            queries[i] = words[idx];
        }
        // generate absent words by mutating existing words or random strings
        int k = 0;
        while (k < numAbsent) {
            String candidate = mutate(words[Math.abs(rng.nextInt()) % n]);
            if (!contains(words, candidate) && !contains(queries, candidate)) {
                queries[numPresent + k] = candidate;
                k++;
            }
        }
        return queries;
    }

    private static String mutate(String s) {
        // simple mutation: append a random suffix and maybe change a char
        StringBuilder sb = new StringBuilder(s);
        sb.append("_x");
        int pos = sb.length() > 0 ? (Math.abs(sb.hashCode()) % sb.length()) : 0;
        char c = (char) ('a' + Math.abs(sb.hashCode()) % 26);
        sb.setCharAt(pos, c);
        return sb.toString();
    }

    private static boolean contains(String[] arr, String key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null)
                continue;
            if (arr[i].equals(key))
                return true;
        }
        return false;
    }
}