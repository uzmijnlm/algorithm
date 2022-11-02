package org.example.other.structure;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentLRUCache {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final Lock readLock = readWriteLock.readLock();

    private final ConcurrentHashMap<Integer, Node> key2Node;
    private final ConcurrentLinkedQueue<Node> queue;
    private final int capacity;
    private int size;

    public ConcurrentLRUCache(int capacity) {
        this.key2Node = new ConcurrentHashMap<>();
        this.queue = new ConcurrentLinkedQueue<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        readLock.lock();
        try {
            if (key2Node.containsKey(key)) {
                Node node = key2Node.get(key);
                moveToTail(queue, node);
                return node.value;
            } else {
                return -1;
            }
        } finally {
            readLock.unlock();
        }
    }

    private void moveToTail(ConcurrentLinkedQueue<Node> queue, Node node) {
        queue.remove(node);
        queue.add(node);
    }

    public void put(int key, int value) {
        writeLock.lock();
        try {
            if (key2Node.containsKey(key)) {
                Node node = key2Node.get(key);
                node.value = value;
                moveToTail(queue, node);
            } else {
                Node node = new Node(key, value);
                moveToTail(queue, node);
                key2Node.put(key, node);
                size++;
                if (size > capacity) {
                    Node nodeToRemove = queue.poll();
                    key2Node.remove(nodeToRemove.key);
                    size--;
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    static class Node {
        int key;
        int value;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
