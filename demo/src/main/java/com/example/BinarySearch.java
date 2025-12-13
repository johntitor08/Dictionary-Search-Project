package com.example;

import java.util.Arrays;

public class BinarySearch {
    private final String[] words;
    private final String[] defs;

    public BinarySearch(String[] wordsIn, String[] defsIn) {
        int n = wordsIn.length;
        Pair[] arr = new Pair[n];
        for (int i = 0; i < n; i++)
            arr[i] = new Pair(wordsIn[i], defsIn == null ? "" : defsIn[i]);
        Arrays.sort(arr);
        words = new String[n];
        defs = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = arr[i].word;
            defs[i] = arr[i].def;
        }
    }

    private static class Pair implements Comparable<Pair> {
        String word;
        String def;

        Pair(String w, String d) {
            word = w;
            def = d;
        }

        public int compareTo(Pair o) {
            return this.word.compareTo(o.word);
        }
    }

    public String search(String key) {
        int low = 0, high = words.length - 1;
        while (low <= high) {
            int mid = low + ((high - low) >>> 1);
            int cmp = words[mid].compareTo(key);
            if (cmp == 0)
                return defs[mid];
            else if (cmp < 0)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return null;
    }
}