package org.example.linkedlist;

import org.junit.Test;

import java.util.Random;
import java.util.Stack;

import static org.example.linkedlist.CommonUtil.*;

public class BasicTest extends BaseTest {

    @Test
    public void testReverse() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomLinkedList(100, 100);
            Node head1 = copyLinkedList(node);
            Node head2 = copyLinkedList(node);
            Node result1 = reverse(head1);
            Node result2 = Basic.reverse(head2);
            while (result1 != null && result2 != null) {
                assert result1.value == result2.value;
                result1 = result1.next;
                result2 = result2.next;
            }
            assert result1 == null;
            assert result2 == null;
        }
    }

    @Test
    public void testReverseDoubleLinkedNode() {
        for (int i = 0; i < 1000; i++) {
            DoubleNode node = generateRandomDoubleLinkedList(100, 100);
            DoubleNode head1 = copyDoubleLinkedList(node);
            DoubleNode head2 = copyDoubleLinkedList(node);
            DoubleNode result1 = reverse(head1);
            DoubleNode result2 = Basic.reverse(head2);
            DoubleNode tail1 = null;
            DoubleNode tail2 = null;
            while (result1 != null && result2 != null) {
                assert result1.value == result2.value;
                if (result1.next == null) {
                    tail1 = result1;
                }
                if (result2.next == null) {
                    tail2 = result2;
                }
                result1 = result1.next;
                result2 = result2.next;
            }
            assert result1 == null;
            assert result2 == null;

            while (tail1 != null && tail2 != null) {
                assert tail1.value == tail2.value;
                tail1 = tail1.prev;
                tail2 = tail2.prev;
            }
            assert tail1 == null;
            assert tail2 == null;
        }
    }

    private Node reverse(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        Node preHead = new Node(-1);
        Node node = preHead;
        while (!stack.isEmpty()) {
            node.next = new Node(stack.pop().value);
            node = node.next;
        }
        return preHead.next;
    }

    private DoubleNode reverse(DoubleNode head) {
        Stack<DoubleNode> stack = new Stack<>();
        DoubleNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        DoubleNode preHead = new DoubleNode(-1);
        DoubleNode node = preHead;
        while (!stack.isEmpty()) {
            node.next = new DoubleNode(stack.pop().value);
            node.next.prev = node;
            node = node.next;
        }
        if (preHead.next != null) {
            preHead.next.prev = null;
        }
        return preHead.next;
    }


    @Test
    public void testGetCommonStart() {
        // 不相交
        for (int i = 0; i < 1000; i++) {
            Node node1 = generateRandomLinkedList(100, 100);
            Node node2 = generateRandomLinkedList(100, 100);
            assert Basic.getCommonStart(node1, node2) == null;
        }

        // 相交
        for (int i = 0; i < 1000; i++) {
            Node commonStart = generateRandomLinkedList(100, 100);
            Node head1 = generateRandomLinkedList(100, 100);
            if (head1 == null) {
                head1 = commonStart;
            } else {
                Node tail1 = null;
                Node cur1 = head1;
                while (cur1 != null) {
                    if (cur1.next == null) {
                        tail1 = cur1;
                    }
                    cur1 = cur1.next;
                }
                tail1.next = commonStart;
            }
            Node head2 = generateRandomLinkedList(100, 100);
            if (head2 == null) {
                head2 = commonStart;
            } else {
                Node tail2 = null;
                Node cur2 = head2;
                while (cur2 != null) {
                    if (cur2.next == null) {
                        tail2 = cur2;
                    }
                    cur2 = cur2.next;
                }
                tail2.next = commonStart;
            }
            assert Basic.getCommonStart(head1, head2) == commonStart;
        }
    }

    @Test
    public void testHasLoop() {
        // hasLoop
        for (int i = 0; i < 1000; i++) {
            Node entryNode;
            while (true) {
                entryNode = generateRandomLinkedList(100, 100);
                if (entryNode == null || entryNode.next == null || entryNode.next.next == null) {
                    continue;
                } else {
                    break;
                }
            }
            Node loopTail = null;
            Node cur = entryNode;
            while (cur != null) {
                if (cur.next == null) {
                    loopTail = cur;
                }
                cur = cur.next;
            }
            loopTail.next = entryNode;

            Node head = generateRandomLinkedList(100, 100);
            if (head == null) {
                head = entryNode;
            } else {
                Node tail = null;
                cur = head;
                while (cur != null) {
                    if (cur.next == null) {
                        tail = cur;
                    }
                    cur = cur.next;
                }
                tail.next = entryNode;
            }
            assert Basic.hasLoop(head);
        }

        // hasNoLoop
        for (int i = 0; i < 1000; i++) {
            Node head = generateRandomLinkedList(100, 100);
            assert !Basic.hasLoop(head);
        }
    }

    @Test
    public void testFindEntryNode() {
        // hasLoop
        for (int i = 0; i < 1000; i++) {
            Node entryNode;
            while (true) {
                entryNode = generateRandomLinkedList(100, 100);
                if (entryNode == null || entryNode.next == null || entryNode.next.next == null) {
                    continue;
                } else {
                    break;
                }
            }
            Node loopTail = null;
            Node cur = entryNode;
            while (cur != null) {
                if (cur.next == null) {
                    loopTail = cur;
                }
                cur = cur.next;
            }
            loopTail.next = entryNode;

            Node head = generateRandomLinkedList(100, 100);
            if (head == null) {
                head = entryNode;
            } else {
                Node tail = null;
                cur = head;
                while (cur != null) {
                    if (cur.next == null) {
                        tail = cur;
                    }
                    cur = cur.next;
                }
                tail.next = entryNode;
            }
            assert Basic.findEntryNode(head) == entryNode;
        }

        // hasNoLoop
        for (int i = 0; i < 1000; i++) {
            Node head = generateRandomLinkedList(100, 100);
            assert Basic.findEntryNode(head) == null;
        }
    }

    @Test
    public void testRemoveLastKthNode() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomLinkedList(100, 100);
            Random random = new Random(System.nanoTime());
            int k = random.nextInt(50);
            Node copied = copyLinkedList(node);
            Node result1 = removeLastKthNode(node, k);
            Node result2 = Basic.removeLastKthNode(copied, k);
            while (result1 != null && result2 != null) {
                assert result1.value == result2.value;
                result1 = result1.next;
                result2 = result2.next;
            }
            assert result1 == null;
            assert result2 == null;
        }
    }

    private Node removeLastKthNode(Node node, int k) {
        if (node == null || k <= 0) {
            return node;
        }
        Node reverse = reverse(node);
        if (k == 1) {
            return reverse(reverse.next);
        }
        int count = k;
        Node cur = reverse;
        Node prev = null;
        while (cur != null) {
            count--;
            if (count == 0) {
                prev.next = prev.next.next;
                return reverse(reverse);
            }
            prev = cur;
            cur = cur.next;
        }
        return reverse(reverse);
    }

    @Test
    public void testRemoveLastKthDoubleNode() {
        for (int i = 0; i < 1; i++) {
            DoubleNode node = generateRandomDoubleLinkedList(10, 100);

            Random random = new Random(System.nanoTime());
            int k = random.nextInt(5);
            System.out.println(k);
            printDoubleLinkedList(node);
            DoubleNode copied = copyDoubleLinkedList(node);
            DoubleNode result1 = removeLastKthNode(node, k);
            printDoubleLinkedList(result1);
            DoubleNode result2 = Basic.removeLastKthNode(copied, k);
            printDoubleLinkedList(result2);
            DoubleNode tail1 = null;
            DoubleNode tail2 = null;
            while (result1 != null && result2 != null) {
                assert result1.value == result2.value;
                if (result1.next == null) {
                    tail1 = result1;
                }
                if (result2.next == null) {
                    tail2 = result2;
                }
                result1 = result1.next;
                result2 = result2.next;
            }
            assert result1 == null;
            assert result2 == null;

            while (tail1 != null && tail2 != null) {
                assert tail1.value == tail2.value;
                tail1 = tail1.prev;
                tail2 = tail2.prev;
            }
            assert tail1 == null;
            assert tail2 == null;
        }
    }

    private DoubleNode removeLastKthNode(DoubleNode node, int k) {
        if (node == null || k <= 0) {
            return node;
        }
        DoubleNode reverse = reverse(node);
        if (k == 1) {
            return reverse(reverse.next);
        }
        int count = k;
        DoubleNode cur = reverse;
        DoubleNode prev = null;
        while (cur != null) {
            count--;
            if (count == 0) {
                DoubleNode next = prev.next.next;
                prev.next = next;
                if (next != null) {
                    next.prev = prev;
                }
                return reverse(reverse);
            }
            prev = cur;
            cur = cur.next;
        }
        return reverse(reverse);
    }


}
