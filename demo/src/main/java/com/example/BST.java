package com.example;

public class BST {

    private BSTNode root;

    public BST() {
        root = null;
    }

    public void insert(DictionaryEntry entry) {
        root = insertRec(root, entry);
    }

    private BSTNode insertRec(BSTNode node, DictionaryEntry entry) {
        if (node == null) {
            return new BSTNode(entry);
        }

        int cmp = entry.getWord().compareToIgnoreCase(node.entry.getWord());

        if (cmp < 0) {
            node.left = insertRec(node.left, entry);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, entry);
        }

        return node;
    }

    public String search(String word) {
        BSTNode node = searchRec(root, word);
        return (node == null) ? null : node.entry.getMeaning();
    }

    private BSTNode searchRec(BSTNode node, String word) {
        if (node == null)
            return null;

        int cmp = word.compareToIgnoreCase(node.entry.getWord());

        if (cmp == 0)
            return node;
        if (cmp < 0)
            return searchRec(node.left, word);
        return searchRec(node.right, word);
    }

    public int count() {
        return countRec(root);
    }

    private int countRec(BSTNode node) {
        if (node == null)
            return 0;
        return 1 + countRec(node.left) + countRec(node.right);
    }
}
