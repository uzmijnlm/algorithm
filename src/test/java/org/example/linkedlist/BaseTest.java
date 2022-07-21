package org.example.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseTest {

    protected Node generateRandomLinkedList(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound);
        int max = random.nextInt(maxBound) + 1;
        Node preHead = new Node(-1);
        Node cur = preHead;
        while (length != 0) {
            cur.next = new Node(random.nextInt(max));
            cur = cur.next;
            length--;
        }
        return preHead.next;
    }

    protected DoubleNode generateRandomDoubleLinkedList(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound);
        int max = random.nextInt(maxBound) + 1;
        DoubleNode preHead = new DoubleNode(-1);
        DoubleNode cur = preHead;
        while (length != 0) {
            cur.next = new DoubleNode(random.nextInt(max));
            cur.next.prev = cur;
            cur = cur.next;
            length--;
        }
        if (preHead.next != null) {
            preHead.next.prev = null;
        }
        return preHead.next;
    }

    protected NodeWithRand generateRandomRandomNodeLinkedList(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 2;
        int max = random.nextInt(maxBound) + 1;
        NodeWithRand preHead = new NodeWithRand(-1);
        List<NodeWithRand> list = new ArrayList<>();
        NodeWithRand cur = preHead;
        while (length != 0) {
            NodeWithRand node = new NodeWithRand(random.nextInt(max));
            list.add(node);
            cur.next = node;
            cur = cur.next;
            length--;
        }

        cur = preHead.next;
        while (cur != null) {
            Random random1 = new Random(System.nanoTime());
            NodeWithRand rand = list.get(random1.nextInt(list.size()));
            while (rand == cur) {
                random1 = new Random(System.nanoTime());
                rand = list.get(random1.nextInt(list.size()));
            }
            cur.rand = rand;
            cur = cur.next;
        }
        return preHead.next;
    }
}
