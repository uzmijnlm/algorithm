package org.example.linkedlist;

public class CommonUtil {

    public static void printLinkedList(Node node) {
        System.out.print("LinkedList: ");
        while (node != null) {
            System.out.print(node.value + "->");
            node = node.next;
        }
        System.out.println("null");
    }

    public static void printDoubleLinkedList(DoubleNode node) {
        System.out.print("DoubleLinkedList: ");
        while (node != null) {
            if (node.next == null) {
                System.out.print(node.value);
            } else {
                System.out.print(node.value + "<->");
            }
            node = node.next;
        }
        System.out.println();
    }

    public static Node copyLinkedList(Node node) {
        if (node == null) {
            return null;
        }

        Node head = new Node(node.value);
        Node cur = head;
        while (node.next != null) {
            cur.next = new Node(node.next.value);
            cur = cur.next;
            node = node.next;
        }
        return head;
    }

    public static DoubleNode copyDoubleLinkedList(DoubleNode node) {
        if (node == null) {
            return null;
        }

        DoubleNode head = new DoubleNode(node.value);
        DoubleNode cur = head;
        while (node.next != null) {
            cur.next = new DoubleNode(node.next.value);
            cur.next.prev = cur;
            cur = cur.next;
            node = node.next;
        }
        return head;
    }

    public static NodeWithRand copyNodeWithRand(NodeWithRand node) {
        if (node == null) {
            return null;
        }

        NodeWithRand cur = node;
        while (cur != null) {
            NodeWithRand copy = new NodeWithRand(cur.value);
            NodeWithRand next = cur.next;
            cur.next = copy;
            copy.next = next;
            cur = next;
        }

        cur = node;
        while (cur != null) {
            NodeWithRand oldNext = cur.next.next;
            NodeWithRand oldRand = cur.rand;
            NodeWithRand newNode = cur.next;
            newNode.rand = oldRand.next;
            cur = oldNext;
        }

        NodeWithRand result = null;
        cur = node;
        while (cur.next != null) {
            NodeWithRand next = cur.next;
            if (result == null) {
                result = next;
            }
            cur.next = cur.next.next;
            cur = next;
        }
        return result;
    }

}
