package org.example.binarytree;

import org.junit.Test;

import java.util.*;

public class AdvancedTest extends BaseTest {

    @Test
    public void testSerAndDeSer() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            String str = Advanced.serialize(node);
            Node recoveredNode = Advanced.deSerialize(str);
            assertNodeEqual(node, recoveredNode);
        }
    }

    @Test
    public void testGetMaxBreadth() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            assert Advanced.GetMaxBreadth(node) == getMaxBreadth(node);
        }
    }

    private int getMaxBreadth(Node node) {
        if (node == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        int max = Integer.MIN_VALUE;
        while (!queue.isEmpty()) {
            int size = queue.size();
            max = Math.max(max, size);
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
        }
        return max;
    }


    @Test
    public void testGetSizeOfCompleteBinaryTree() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateCompletedBinaryTree(100, 200);
            assert Advanced.getSizeOfCompleteBinaryTree(node) == getSizeOfCompleteBinaryTree(node);
        }
    }

    private int getSizeOfCompleteBinaryTree(Node node) {
        if (node == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        int ans = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            ans++;
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return ans;
    }


    @Test
    public void testGetMaxLenWithSum() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 10);
            int k = new Random(System.nanoTime()).nextInt(50);
            assert Advanced.getMaxLenWithSum(node, k) == getMaxLenWithSum(node, k);
        }
    }

    private int getMaxLenWithSum(Node node, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        return preOrder(node, k, 0, 1, 0, map);
    }

    private int preOrder(Node head,
                         int sum,
                         int preSum,
                         int level,
                         int maxLen,
                         Map<Integer, Integer> map) {
        if (head == null) {
            return maxLen;
        }
        int curSum = preSum + head.value;
        if (!map.containsKey(curSum)) {
            map.put(curSum, level);
        }
        if (map.containsKey(curSum - sum)) {
            maxLen = Math.max(level - map.get(curSum - sum), maxLen);
        }

        maxLen = preOrder(head.left, sum, curSum, level + 1, maxLen, map);
        maxLen = preOrder(head.right, sum, curSum, level + 1, maxLen, map);
        if (level == map.get(curSum)) {
            map.remove(curSum);
        }
        return maxLen;
    }


    @Test
    public void testMaxBST() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            assert Advanced.getMaxBST(node) == getMaxBST(node);
        }
    }

    static class BSTStat {
        Node head;
        int size;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
    }
    private Node getMaxBST(Node node) {
        return getMaxBSTStat(node).head;
    }

    private BSTStat getMaxBSTStat(Node node) {
        BSTStat stat = new BSTStat();
        if (node == null) {
            return stat;
        }

        BSTStat leftStat = getMaxBSTStat(node.left);
        BSTStat rightStat = getMaxBSTStat(node.right);
        int min = Math.min(node.value, Math.min(leftStat.min, rightStat.min));
        int max = Math.max(node.value, Math.max(leftStat.max, rightStat.max));
        int size = Math.max(leftStat.size, rightStat.size);
        Node head = leftStat.size > rightStat.size ? leftStat.head : rightStat.head;
        if (leftStat.head == node.left && rightStat.head == node.right
                && node.value > leftStat.max && node.value < rightStat.min) {
            size = leftStat.size + rightStat.size + 1;
            head = node;
        }
        stat.head = head;
        stat.size = size;
        stat.max = max;
        stat.min = min;
        return stat;
    }


}
