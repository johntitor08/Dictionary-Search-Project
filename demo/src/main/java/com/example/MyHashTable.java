package com.example;

public class MyHashTable {
    private final HashNode[] table;
    private final int M;

    private static class HashNode {
        String key;
        String value;
        HashNode next;

        HashNode(String key, String value, HashNode next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashTable(int M) {
        this.M = M;
        this.table = new HashNode[M];
    }

    public MyHashTable(String[] words, String[] defs, int M) {
        this(M);
        for (int i = 0; i < words.length; i++) {
            put(words[i], defs == null ? "" : defs[i]);
        }
    }

    private int hash(String w) {
        long res = 0L;
        for (int i = 0; i < w.length(); i++) {
            res = (res * 31L + (int) w.charAt(i)) % M;
        }
        return (int) res;
    }

    public void put(String key, String value) {
        int idx = hash(key);
        HashNode head = table[idx];
        for (HashNode p = head; p != null; p = p.next) {
            if (p.key.equals(key)) {
                p.value = value;
                return;
            }
        }
        table[idx] = new HashNode(key, value, head);
    }

    public String get(String key) {
        int idx = hash(key);
        for (HashNode p = table[idx]; p != null; p = p.next) {
            if (p.key.equals(key))
                return p.value;
        }
        return null;
    }
}