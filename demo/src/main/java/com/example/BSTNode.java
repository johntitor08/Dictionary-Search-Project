package com.example;

public class BSTNode {
    DictionaryEntry entry;
    BSTNode left;
    BSTNode right;

    public BSTNode(DictionaryEntry entry) {
        this.entry = entry;
        this.left = null;
        this.right = null;
    }
}
