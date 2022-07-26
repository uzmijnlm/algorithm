package org.example.other;

public class Skiplist {
    private final static double FACTOR = 0.25;
    private final static int MAX_LEVEL = 32;

    private final Node head;
    private int currentLevel;

    private int randomLevel() {
        int level = 1;
        while (Math.random() < FACTOR && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    private Node findClosest(Node node, int level, int value) {
        Node cur = node;
        while (cur.nexts[level] != null && cur.nexts[level].value < value) {
            cur = cur.nexts[level];
        }
        return cur;
    }

    public Skiplist() {
        this.head = new Node(-1, MAX_LEVEL);
        this.currentLevel = 1;
    }

    public boolean search(int target) {
        Node searchNode = head;
        for (int i = currentLevel - 1; i >= 0; i--) {
            searchNode = findClosest(searchNode, i, target);
            if (searchNode.nexts[i] != null && searchNode.nexts[i].value == target) {
                return true;
            }
        }
        return false;
    }

    public void add(int num) {
        int level = randomLevel();
        Node node = new Node(num, level);
        Node updateNode = head;
        for (int i = currentLevel - 1; i >= 0; i--) {
            updateNode = findClosest(updateNode, i, num);
            if (i < level) {
                if (updateNode.nexts[i] != null) {
                    Node oldNext = updateNode.nexts[i];
                    updateNode.nexts[i] = node;
                    node.nexts[i] = oldNext;
                } else {
                    updateNode.nexts[i] = node;
                }
            }
        }
        if (level > currentLevel) {
            for (int i = currentLevel; i < level; i++) {
                head.nexts[i] = node;
            }
            currentLevel = level;
        }
    }

    public boolean erase(int num) {
        Node node = head;
        boolean flag = false;
        for (int i = currentLevel - 1; i >= 0; i--) {
            node = findClosest(node, i, num);
            if (node.nexts[i] != null && node.nexts[i].value == num) {
                node.nexts[i] = node.nexts[i].nexts[i];
                flag = true;
            }
        }
        return flag;
    }

    static class Node {
        public int value;
        public Node[] nexts;

        public Node(int value, int level) {
            this.value = value;
            this.nexts = new Node[level];
        }
    }
}


/**
 * Your Skiplist object will be instantiated and called as such:
 * Skiplist obj = new Skiplist();
 * boolean param_1 = obj.search(target);
 * obj.add(num);
 * boolean param_3 = obj.erase(num);
 */