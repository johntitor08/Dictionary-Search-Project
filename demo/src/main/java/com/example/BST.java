package com.example;

import java.util.ArrayDeque;
import java.util.Deque;

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

    // FIX: iterative traversal to avoid stack overflow on large/degenerate trees
    public int count() {
        if (root == null) return 0;
        int count = 0;
        Deque<BSTNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            BSTNode node = stack.pop();
            count++;
            if (node.left  != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        return count;
    }
}
