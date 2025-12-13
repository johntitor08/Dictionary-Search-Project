package com.example;

class HashNode {
    String key;
    String value;
    HashNode next;

    HashNode(String k, String v, HashNode n) {
        key = k;
        value = v;
        next = n;
    }
}