package org.example.other.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
class LFUCache {
    private final Map<Integer, Node> key2Node;
    private final Map<Node, NodeBucket> node2bucket;
    private int size;
    private final int capacity;
    private NodeBucket headBucket;

    public LFUCache(int capacity) {
        this.key2Node = new HashMap<>();
        this.node2bucket = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (key2Node.containsKey(key)) {
            Node node = key2Node.get(key);
            node.times++;
            NodeBucket bucket = node2bucket.get(node);
            moveForward(node, bucket);
            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (key2Node.containsKey(key)) {
            Node node = key2Node.get(key);
            node.times++;
            node.value = value;
            NodeBucket bucket = node2bucket.get(node);
            moveForward(node, bucket);
        } else {
            if (capacity == 0) {
                return;
            }
            if (size == capacity) {
                Node nodeToRemove = headBucket.tail;
                headBucket.deleteNode(nodeToRemove);
                modifyIfEmpty(headBucket);
                key2Node.remove(nodeToRemove.key);
                node2bucket.remove(nodeToRemove);
                size--;
            }
            Node node = new Node(key, value);
            node.times++;
            if (headBucket == null) {
                headBucket = new NodeBucket();
                headBucket.addNodeToHead(node);
            } else {
                if (headBucket.head.times == 1) {
                    headBucket.addNodeToHead(node);
                } else {
                    NodeBucket newBucket = new NodeBucket();
                    newBucket.addNodeToHead(node);
                    newBucket.next = headBucket;
                    headBucket.prev = newBucket;
                    headBucket = newBucket;
                }
            }
            key2Node.put(key, node);
            node2bucket.put(node, headBucket);
            size++;
        }
    }


    private void moveForward(Node node, NodeBucket bucket) {
        bucket.deleteNode(node);

        NodeBucket prevBucket;
        if (modifyIfEmpty(bucket)) {
            prevBucket = bucket.prev;
        } else {
            prevBucket = bucket;
        }

        NodeBucket nextBucket = bucket.next;
        if (nextBucket == null) {
            NodeBucket newBucket = new NodeBucket();
            newBucket.addNodeToHead(node);
            if (prevBucket != null) {
                prevBucket.next = newBucket;
            }
            newBucket.prev = prevBucket;
            if (headBucket == null) {
                headBucket = newBucket;
            }
            node2bucket.put(node, newBucket);
        } else {
            if (nextBucket.head.times == node.times) {
                nextBucket.addNodeToHead(node);
                node2bucket.put(node, nextBucket);
            } else {
                NodeBucket newBucket = new NodeBucket();
                newBucket.addNodeToHead(node);
                newBucket.next = nextBucket;
                nextBucket.prev = newBucket;
                newBucket.prev = prevBucket;
                if (prevBucket != null) {
                    prevBucket.next = newBucket;
                }
                if (headBucket == nextBucket) {
                    headBucket = newBucket;
                }
                node2bucket.put(node, newBucket);
            }
        }
    }


    private boolean modifyIfEmpty(NodeBucket bucket) {
        if (bucket.isEmpty()) {
            if (headBucket == bucket) {
                headBucket = headBucket.next;
                if (headBucket != null) {
                    headBucket.prev = null;
                }
            } else {
                bucket.prev.next = bucket.next;
                if (bucket.next != null) {
                    bucket.next.prev = bucket.prev;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    static class NodeBucket {
        Node head;
        Node tail;
        NodeBucket next;
        NodeBucket prev;

        public void addNodeToHead(Node node) {
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
        }

        public void deleteNode(Node node) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                if (head == node) {
                    head = head.next;
                    head.prev = null;
                } else if (tail == node) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
                node.next = null;
                node.prev = null;
            }
        }

        public boolean isEmpty() {
            return head == null;
        }


    }


    static class Node {
        int key;
        int value;
        int times;
        Node next;
        Node prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

