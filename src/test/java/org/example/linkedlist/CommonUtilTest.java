package org.example.linkedlist;

import org.junit.Test;

import static org.example.linkedlist.CommonUtil.*;

public class CommonUtilTest extends BaseTest {

    @Test
    public void testCopyLinkedList() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomLinkedList(100, 100);
            Node copiedNode = copyLinkedList(node);

            while (node != null && copiedNode != null) {
                assert node.value == copiedNode.value;
                node = node.next;
                copiedNode = copiedNode.next;
            }
            assert node == null;
            assert copiedNode == null;
        }
    }

    @Test
    public void testCopyDoubleLinkedList() {
        for (int i = 0; i < 1000; i++) {
            DoubleNode node = generateRandomDoubleLinkedList(100, 100);
            DoubleNode copiedNode = copyDoubleLinkedList(node);
            DoubleNode tail1 = null;
            DoubleNode tail2 = null;

            while (node != null && copiedNode != null) {
                assert node.value == copiedNode.value;
                if (node.next == null) {
                    tail1 = node;
                }
                if (copiedNode.next == null) {
                    tail2 = copiedNode;
                }
                node = node.next;
                copiedNode = copiedNode.next;
            }
            assert node == null;
            assert copiedNode == null;

            while (tail1 != null && tail2 != null) {
                assert  tail1.value == tail2.value;
                tail1 = tail1.prev;
                tail2 = tail2.prev;
            }
            assert tail1 == null;
            assert tail2 == null;
        }
    }

    @Test
    public void testCopyNodeWithRand() {
        for (int i = 0; i < 1000; i++) {
            NodeWithRand node = generateRandomRandomNodeLinkedList(100, 100);
            NodeWithRand copiedNode = copyNodeWithRand(node);
            NodeWithRand cur1 = node;
            NodeWithRand cur2 = copiedNode;
            while (cur1 != null && cur2 != null) {
                assert cur1.value == cur2.value;
                assert cur1.rand.value == cur2.rand.value;
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            assert cur1 == null;
            assert cur2 == null;
        }
    }
}
