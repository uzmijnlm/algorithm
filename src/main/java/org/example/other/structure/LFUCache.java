package org.example.other.structure;

import java.util.HashMap;
import java.util.Map;

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
public class LFUCache {
    private NodeBucket headBucket;
    private final Map<Integer, Node> key2Node;
    private final Map<Node, NodeBucket> node2Bucket;
    private int size;
    private final int capacity;

    public LFUCache(int capacity) {
        this.key2Node = new HashMap<>();
        this.node2Bucket = new HashMap<>();
        this.size = 0;
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if (key2Node.containsKey(key)) {
            Node node = key2Node.get(key);
            node.times++;
            NodeBucket bucket = node2Bucket.get(node);
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
            NodeBucket bucket = node2Bucket.get(node);
            moveForward(node, bucket);
        } else {
            if (capacity == 0) {
                return;
            }
            if (size == capacity) {
                Node removedNode = headBucket.tail;
                headBucket.deleteNode(removedNode);
                modifyBucketIfEmtpy(headBucket);
                key2Node.remove(removedNode.key);
                node2Bucket.remove(removedNode);
                size--;
            }
            Node node = new Node(key, value);
            if (headBucket == null) {
                headBucket = new NodeBucket(node);
            } else {
                if (headBucket.head.times == node.times) {
                    headBucket.addNodeToHead(node);
                } else {
                    NodeBucket newBucket = new NodeBucket(node);
                    newBucket.next = headBucket;
                    headBucket.prev = newBucket;
                    headBucket = newBucket;
                }
            }
            key2Node.put(key, node);
            node2Bucket.put(node, headBucket);
            size++;
        }
    }

    private void moveForward(Node node, NodeBucket bucket) {
        bucket.deleteNode(node);

        NodeBucket preBucket;
        if (modifyBucketIfEmtpy(bucket)) {
            preBucket = bucket.prev;
        } else {
            preBucket = bucket;
        }

        NodeBucket nextBucket = bucket.next;
        if (nextBucket == null) {
            NodeBucket newBucket = new NodeBucket(node);
            if (preBucket != null) {
                preBucket.next = newBucket;
            }
            newBucket.prev = preBucket;
            if (headBucket == null) {
                headBucket = newBucket;
            }
            node2Bucket.put(node, newBucket);
        } else {
            if (nextBucket.head.times == node.times) {
                nextBucket.addNodeToHead(node);
                node2Bucket.put(node, nextBucket);
            } else {
                NodeBucket newBucket = new NodeBucket(node);
                newBucket.next = nextBucket;
                if (nextBucket != null) {
                    nextBucket.prev = newBucket;
                }
                newBucket.prev = preBucket;
                if (preBucket != null) {
                    preBucket.next = newBucket;
                }
                if (headBucket == nextBucket) {
                    headBucket = newBucket;
                }
                node2Bucket.put(node, newBucket);
            }
        }
    }

    private boolean modifyBucketIfEmtpy(NodeBucket bucket) {
        if (bucket.isEmpty()) {
            if (headBucket == bucket) {
                headBucket = headBucket.next;
                if (headBucket != null) {
                    headBucket.prev = null;
                }
            } else {
                if (bucket.prev != null) {
                    bucket.prev.next = bucket.next;
                }
                if (bucket.next != null) {
                    bucket.next.prev = bucket.prev;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    static class Node {
        public int key;
        public int value;
        public int times;
        public Node prev;
        public Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.times = 1;
        }
    }

    static class NodeBucket {
        public Node head;
        public Node tail;
        public NodeBucket prev;
        public NodeBucket next;

        public NodeBucket(Node node) {
            this.head = node;
            this.tail = node;
        }

        public void addNodeToHead(Node node) {
            if (node == null) {
                return;
            }

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
            if (node == null) {
                return;
            }

            if (head==null) {
                return;
            }
            if (head==tail) {
                head = null;
                tail = null;
            } else {
                if (head==node) {
                    head = head.next;
                    head.prev = null;
                } else if (tail == node) {
                    tail = tail.prev;
                    tail.next = null;
                } else {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                }
            }
            node.next = null;
            node.prev = null;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

}














