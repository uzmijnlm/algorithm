package org.example.linkedlist;

public class Basic {

    public static Node reverse(Node node) {
        if (node == null) {
            return null;
        }
        Node prev = null;
        Node cur = node;
        while (cur != null) {
            Node newNext = cur.next;
            cur.next = prev;
            prev = cur;
            cur = newNext;
        }
        return prev;
    }

    public static DoubleNode reverse(DoubleNode node) {
        if (node == null) {
            return null;
        }

        DoubleNode prev = null;
        DoubleNode cur = node;
        while (cur != null) {
            DoubleNode newNext = cur.next;
            cur.next = prev;
            cur.prev = newNext;
            prev = cur;
            cur = newNext;
        }
        return prev;
    }


    public static Node getCommonStart(Node node1, Node node2) {
        if (node1 == null || node2 == null) {
            return null;
        }

        int size1 = 0;
        int size2 = 0;
        Node cur1 = node1;
        Node cur2 = node2;
        while (cur1 != null) {
            size1++;
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            size2++;
            cur2 = cur2.next;
        }

        Node longer = size1 > size2 ? node1 : node2;
        Node shorter = size1 > size2 ? node2 : node1;

        int diff = size1 > size2 ? size1 - size2 : size2 - size1;

        Node cur3 = longer;
        for (int i = 0; i < diff; i++) {
            cur3 = cur3.next;
        }

        Node cur4 = shorter;

        while (true) {
            if (cur3==cur4) {
                return cur3;
            } else {
                cur3 = cur3.next;
                cur4 = cur4.next;
            }
        }
    }

    public static boolean hasLoop(Node node) {
        if (node == null || node.next == null || node.next.next == null) {
            return false;
        }

        Node slow = node.next;
        Node fast = node.next.next;

        while (fast != null && fast.next != null) {
            if (fast == slow) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    public static Node findEntryNode(Node node) {
        if (node == null || node.next == null || node.next.next == null) {
            return null;
        }

        Node slow = node.next;
        Node fast = node.next.next;

        boolean hasLoop = false;
        while (fast != null && fast.next != null) {
            if (fast == slow) {
                hasLoop = true;
                break;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        if (hasLoop) {
            fast = node;
            while (true) {
                if (fast==slow) {
                    return slow;
                } else {
                    fast = fast.next;
                    slow = slow.next;
                }
            }
        } else {
            return null;
        }
    }

    public static Node removeLastKthNode(Node node, int k) {
        if (node == null || k <= 0) {
            return node;
        }
        int lastK = k;
        Node cur = node;
        while (cur != null) {
            lastK--;
            cur = cur.next;
        }

        if (lastK > 0) {
            return node;
        } else if (lastK == 0) {
            return node.next;
        } else {
            cur = node;
            while (cur != null) {
                lastK++;
                if (lastK == 0) {
                    break;
                }
                cur = cur.next;
            }
            if (cur != null && cur.next != null) {
                cur.next = cur.next.next;
            }
            return node;
        }
    }

    public static DoubleNode removeLastKthNode(DoubleNode node, int k) {
        if (node == null || k <=0) {
            return node;
        }

        int lastK = k;
        DoubleNode cur = node;
        while (cur != null) {
            lastK--;
            cur = cur.next;
        }

        if (lastK > 0) {
            return node;
        } else if (lastK == 0) {
            if (node.next != null) {
                node.next.prev = null;
            }
            return node.next;
        } else {
            cur = node;
            while (cur != null) {
                lastK++;
                if (lastK == 0) {
                    break;
                }
                cur = cur.next;
            }
            if (cur != null && cur.next != null) {
                DoubleNode next = cur.next.next;
                cur.next = next;
                if (next != null) {
                    next.prev = cur;
                }
            }
            return node;
        }
    }



}
