package org.example.other.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
public class LRUCache {
    private final Map<Integer, Node> key2node;
    private DoubleLinkedList list;
    int size;
    int capacity;

    public LRUCache(int capacity) {
        key2node = new HashMap<>();
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if (key2node.containsKey(key)) {
            Node node = key2node.get(key);
            list.moveNodeToTail(node);
            return node.val;
        } else {
            return -1;
        }
    }
    
    public void put(int key, int value) {
        if (key2node.containsKey(key)) {
            Node node = key2node.get(key);
            list.moveNodeToTail(node);
            node.val = value;
        } else {
            if (capacity == 0) {
                return;
            }
            Node node = new Node(key, value);
            if (size == 0) {
                list = new DoubleLinkedList(node);
            } else {
                list.addNodeToTail(node);
            }
            key2node.put(key, node);
            size++;
            if (size > capacity) {
                Node nodeToRemove = list.removeHead();
                key2node.remove(nodeToRemove.key);
                size--;
            }
            
        }
    }


    static class DoubleLinkedList {
        Node head;
        Node tail;

        public DoubleLinkedList(Node node) {
            this.head = node;
            this.tail = node;
        }

        public void addNodeToTail(Node node) {
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        }

        public void moveNodeToTail(Node node) {
            if (node == tail) {
                return;
            }
            if (node == head) {
                head = head.next;
                head.prev = null;
                node.next = null;
                addNodeToTail(node);
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                addNodeToTail(node);
            }
        }

        public Node removeHead() {
            if (head == tail) {
                Node node = head;
                head = null;
                tail = null;
                return node;
            } else {
                Node node = head;
                head = head.next;
                head.prev = null;
                node.next = null;
                return node;
            }
        }
    }


    static class Node {
        Node prev;
        Node next;
        int key;
        int val;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}

