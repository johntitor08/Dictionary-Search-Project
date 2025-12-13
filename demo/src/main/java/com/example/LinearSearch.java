package com.example;

public class LinearSearch {
    private final String[] words;
    private final String[] defs;

    public LinearSearch(String[] words, String[] defs) {
        this.words = words;
        this.defs = defs;
    }

    public String search(String key) {
        for (int i = 0; i < words.length; i++) {
            if (words[i] != null && words[i].equals(key))
                return defs == null ? "" : defs[i];
        }
        return null;
    }

    public String[] getWords() {
        return words;
    }
}