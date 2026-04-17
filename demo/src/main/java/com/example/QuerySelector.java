package com.example;

import java.util.Random;

public class QuerySelector {

    // FIX: use Fisher-Yates shuffle to pick present words without duplicates
    public static String[] select(String[] words, int numPresent, int numAbsent, long seed) {
        int n = words.length;
        if (numPresent > n) numPresent = n;

        String[] queries = new String[numPresent + numAbsent];
        Random rng = new Random(seed);

        // Shuffle a copy of the indices and take the first numPresent
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        for (int i = n - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = indices[i]; indices[i] = indices[j]; indices[j] = tmp;
        }
        for (int i = 0; i < numPresent; i++) {
            queries[i] = words[indices[i]];
        }

        // Generate absent words that don't exist in the dictionary or query list
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
        StringBuilder sb = new StringBuilder(s);
        sb.append("_x");
        int pos = sb.length() > 0 ? (Math.abs(sb.hashCode()) % sb.length()) : 0;
        char c = (char) ('a' + Math.abs(sb.hashCode()) % 26);
        sb.setCharAt(pos, c);
        return sb.toString();
    }

    private static boolean contains(String[] arr, String key) {
        for (String s : arr) {
            if (s == null) continue;
            if (s.equals(key)) return true;
        }
        return false;
    }
}
