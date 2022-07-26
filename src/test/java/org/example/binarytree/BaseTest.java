package org.example.binarytree;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class BaseTest {

    protected Node generateRandomBinaryTree(int size, int max) {
        AtomicInteger totalSize = new AtomicInteger(size);
        return generateRandomBinaryTree(totalSize, max, 0.95);
    }

    private Node generateRandomBinaryTree(AtomicInteger size, int max, double p) {
        if (Math.random() < p && size.get() > 0) {
            Node node = new Node(new Random(System.nanoTime()).nextInt(max));
            size.decrementAndGet();
            node.left = generateRandomBinaryTree(size, max, p * 0.9);
            node.right = generateRandomBinaryTree(size, max, p * 0.9);
            return node;
        } else {
            return null;
        }
    }

    protected Node generateBinaryTreeWithoutDup(int size, int max) {
        AtomicInteger totalSize = new AtomicInteger(size);
        Set<Integer> set = new HashSet<>();
        return generateBinaryTreeWithoutDup(totalSize, max, 0.95, set);
    }

    private Node generateBinaryTreeWithoutDup(AtomicInteger size, int max, double p, Set<Integer> set) {
        if (Math.random() < p && size.get() > 0) {
            Random random = new Random((System.nanoTime()));
            int i = random.nextInt(max);
            while (set.contains(i)) {
                i = random.nextInt(max);
            }
            set.add(i);
            Node node = new Node(i);
            size.decrementAndGet();
            node.left = generateBinaryTreeWithoutDup(size, max, p * 0.9, set);
            node.right = generateBinaryTreeWithoutDup(size, max, p * 0.9, set);
            return node;
        } else {
            return null;
        }
    }

    protected void assertNodeEqual(Node node, Node copiedNode) {
        if (node != null && copiedNode == null) {
            assert false;
        } else if (node == null && copiedNode != null) {
            assert false;
        } else if (node != null && copiedNode != null) {
            assert node.value == copiedNode.value;
            assertNodeEqual(node.left, copiedNode.left);
            assertNodeEqual(node.right, copiedNode.right);
        }
    }

    protected Node generateSearchBinaryTree(int size, int max) {
        AtomicInteger totalSize = new AtomicInteger(size);
        return generateSearchBinaryTree(totalSize, max, 0.95, 0, max - 1);
    }

    private Node generateSearchBinaryTree(AtomicInteger size, int max, double p, int low, int high) {
        if (low == 0 || high == max - 1) {
            return null;
        }
        if (Math.random() < p && size.get() > 0) {
            int value = new Random(System.nanoTime()).nextInt(high - low) + low;
            Node node = new Node(value);
            size.decrementAndGet();
            node.left = generateSearchBinaryTree(size, max, p * 0.9, low, value - 1);
            node.right = generateSearchBinaryTree(size, max, p * 0.9, value + 1, high);
            return node;
        } else {
            return null;
        }
    }

    protected Node generateCompletedBinaryTree(int sizeBound, int max) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(sizeBound);
        if (length == 0) {
            return null;
        }
        int[] tree = new int[length];
        for (int i = 0; i < length; i++) {
            tree[i] = random.nextInt(max);
        }

        Node[] nodes = new Node[length];
        for (int i = 0; i < length; i++) {
            nodes[i] = new Node(tree[i]);
        }
        for (int i = 0; i < length; i++) {
            if (i * 2 + 1 < length) {
                nodes[i].left = nodes[i * 2 + 1];
            }
            if (i * 2 + 2 < length) {
                nodes[i].right = nodes[i * 2 + 2];
            }
        }
        return nodes[0];
    }

}
