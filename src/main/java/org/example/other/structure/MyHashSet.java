package org.example.other.structure;

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */
class MyHashSet {
    private final Node[] nodes = new Node[10009];


    public MyHashSet() {

    }
    
    public void add(int key) {
        int index = getIndex(key);
        Node head = nodes[index];
        if (head == null) {
            nodes[index] = new Node(key);
        } else {
            Node cur = head;
            Node tail = null;
            while (cur != null) {
                if (cur.key == key) {
                    return;
                }
                if (cur.next == null) {
                    tail = cur;
                }
                cur = cur.next;
            }
            tail.next = new Node(key);
        }
    }
    
    public void remove(int key) {
        int index = getIndex(key);
        Node head = nodes[index];
        if (head != null) {
            if (head.key == key) {
                nodes[index] = head.next;
            } else {
                Node pre = new Node(-1);
                Node cur = head;
                while (cur != null) {
                    if (cur.key == key) {
                        pre.next = cur.next;
                        cur.next = null;
                        return;
                    }
                    pre = cur;
                    cur = cur.next;
                }
            }
        }
    }
    
    public boolean contains(int key) {
        int index = getIndex(key);
        Node head = nodes[index];
        if (head != null) {
            Node cur = head;
            while (cur != null) {
                if (cur.key == key) {
                    return true;
                }
                cur = cur.next;
            }
        }
        return false;
    }


    private int getIndex(int key) {
        int hash = Integer.hashCode(key);
        hash = (hash >>> 16) ^ hash;
        return hash % nodes.length;
    }
}

class Node {
    int key;
    Node next;

    public Node(int key) {
        this.key = key;
    }
}

