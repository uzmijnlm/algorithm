package org.example.linkedlist;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.example.linkedlist.Advanced.findCrossPoint;
import static org.example.linkedlist.Advanced.isPalindrome;

public class AdvancedTest extends BaseTest {

    @Test
    public void testIsPalindrome() {
        // case 1: null
        assert !isPalindrome(null);

        // case 2: 长度为1
        Node node1 = new Node(-1);
        Node copiedNode1 = CommonUtil.copyLinkedList(node1);
        assert isPalindrome(node1);
        while (node1 != null && copiedNode1 != null) {
            assert node1.value == copiedNode1.value;
            node1 = node1.next;
            copiedNode1 = copiedNode1.next;
        }

        // case 3: 长度为2且对称
        Node node2 = new Node(-1);
        node2.next = new Node(-1);
        Node copiedNode2 = CommonUtil.copyLinkedList(node2);
        assert isPalindrome(node2);
        while (node2 != null && copiedNode2 != null) {
            assert node2.value == copiedNode2.value;
            node2 = node2.next;
            copiedNode2 = copiedNode2.next;
        }

        // case 4: 长度为2且不对称
        Node node3 = new Node(-1);
        node3.next = new Node(1);
        Node copiedNode3 = CommonUtil.copyLinkedList(node3);
        assert !isPalindrome(node3);
        while (node3 != null && copiedNode3 != null) {
            assert node3.value == copiedNode3.value;
            node3 = node3.next;
            copiedNode3 = copiedNode3.next;
        }

        // case 5: 长度为3且对称
        Node node4 = new Node(1);
        node4.next = new Node(2);
        node4.next.next = new Node(1);
        Node copiedNode4 = CommonUtil.copyLinkedList(node4);
        assert isPalindrome(node4);
        while (node4 != null && copiedNode4 != null) {
            assert node4.value == copiedNode4.value;
            node4 = node4.next;
            copiedNode4 = copiedNode4.next;
        }

        // case 6: 长度为3且不对称
        Node node5 = new Node(1);
        node5.next = new Node(2);
        node5.next.next = new Node(3);
        Node copiedNode5 = CommonUtil.copyLinkedList(node5);
        assert !isPalindrome(node5);
        while (node5 != null && copiedNode5 != null) {
            assert node5.value == copiedNode5.value;
            node5 = node5.next;
            copiedNode5 = copiedNode5.next;
        }

        // case 7: 任意长度且不为null且对称
        for (int i = 0; i < 1000; i++) {
            Node node6 = null;
            while (node6 == null) {
                node6 = generateRandomLinkedList(10, 100);
            }
            Node copiedNode6 = CommonUtil.copyLinkedList(node6);
            Node reversedNode6 = Basic.reverse(copiedNode6);
            Node cur = node6;
            Node tail = null;
            while (cur != null) {
                if (cur.next == null) {
                    tail = cur;
                }
                cur = cur.next;
            }
            tail.next = reversedNode6;
            Node copiedWholeNode6 = CommonUtil.copyLinkedList(node6);
            assert isPalindrome(node6);
            while (node6 != null && copiedNode6 != null) {
                assert node6.value == copiedNode6.value;
                node6 = node6.next;
                copiedNode6 = copiedNode6.next;
            }
        }

        // case 8: 任意长度且不为null且不对称
        for (int i = 0; i < 1000; i++) {
            Node node7 = null;
            while (node7 == null) {
                node7 = generateRandomLinkedList(10, 100);
            }
            Node head = new Node(-1);
            head.next = node7;
            Node cur = head;
            Node tail = null;
            while (cur != null) {
                if (cur.next == null) {
                    tail = cur;
                }
                cur = cur.next;
            }
            tail.next = new Node(1);
            Node copiedNode7 = CommonUtil.copyLinkedList(node7);
            assert !isPalindrome(head);
            while (node7 != null && copiedNode7 != null) {
                assert node7.value == copiedNode7.value;
                node7 = node7.next;
                copiedNode7 = copiedNode7.next;
            }
        }
    }


    @Test
    public void testSplitThreeParts() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomLinkedList(100, 100);
            Random random = new Random(System.nanoTime());
            int num = random.nextInt(100);
            Node result = Advanced.splitThreeParts(node, num);
            boolean meetEqual = false;
            boolean meetLarger = false;
            Node cur = result;
            while (cur != null) {
                if (!meetEqual && !meetLarger) {
                    if (cur.value == num) {
                        meetEqual = true;
                    } else if (cur.value > num) {
                        meetEqual = true;
                        meetLarger = true;
                    }
                    cur = cur.next;
                } else if (meetEqual && !meetLarger) {
                    if (cur.value < num) {
                        assert false;
                    } else if (cur.value > num) {
                        meetLarger = true;
                    }
                    cur = cur.next;
                } else if (meetEqual && meetLarger) {
                    if (cur.value <= num) {
                        assert false;
                    } else {
                        cur = cur.next;
                    }
                } else {
                    assert false;
                }
            }
        }
    }


    @Test
    public void testFindCrossPoint() {
        // case 1: 至少一个为null
        assert Advanced.findCrossPoint(null, new Node(-1)) == null;
        assert Advanced.findCrossPoint(new Node(-1), null) == null;
        assert Advanced.findCrossPoint(null, null) == null;

        // case 2: 两个都不成环且不相交
        for (int i = 0; i < 1000; i++) {
            Node node1 = generateRandomLinkedList(100, 100);
            Node node2 = generateRandomLinkedList(100, 100);
            assert Advanced.findCrossPoint(node1, node2) == null;
        }

        // case 3: 两个都不成环且相交
        for (int i = 0; i < 1000; i++) {
            Node commonStart = generateRandomLinkedList(100, 100);
            Node node1 = generateRandomLinkedList(100, 100);
            Node node2 = generateRandomLinkedList(100, 100);
            Node tail1 = null;
            Node tail2 = null;
            Node cur1 = node1;
            Node cur2 = node2;
            while (cur1 != null) {
                if (cur1.next == null) {
                    tail1 = cur1;
                }
                cur1 = cur1.next;
            }
            while (cur2 != null) {
                if (cur2.next == null) {
                    tail2 = cur2;
                }
                cur2 = cur2.next;
            }
            if (tail1 != null) {
                tail1.next = commonStart;
            } else {
                node1 = commonStart;
            }
            if (tail2 != null) {
                tail2.next = commonStart;
            } else {
                node2 = commonStart;
            }
            assert findCrossPoint(node1, node2) == commonStart;
        }

        // case 4: 一个成环一个不成环
        for (int i = 0; i < 1000; i++) {
            Node node1 = generateRandomLinkedList(100, 100);
            Node loopHead = generateRandomLinkedList(100, 100);
            if (loopHead == null) {
                loopHead = new Node(-1);
            }
            loopHead.next = new Node(0);
            Node tail = null;
            Node cur = loopHead;
            while (cur != null) {
                if (cur.next == null) {
                    tail = cur;
                }
                cur = cur.next;
            }
            tail.next = loopHead;
            Node node2 = generateRandomLinkedList(100, 100);
            Node tail2 = null;
            Node cur2 = node2;
            while (cur2 != null) {
                if (cur2.next == null) {
                    tail2 = cur2;
                }
                cur2 = cur2.next;
            }
            if (tail2 != null) {
                tail2.next = loopHead;
            } else {
                node2 = loopHead;
            }
            assert Advanced.findCrossPoint(node1, node2) == null;
            assert Advanced.findCrossPoint(node2, node1) == null;
        }

        // case 5: 分别独立成环
        for (int i = 0; i < 1000; i++) {
            Node loopHead1 = generateRandomLinkedList(100, 100);
            if (loopHead1 == null) {
                loopHead1 = new Node(-1);
            }
            loopHead1.next = new Node(0);
            Node loopTail1 = null;
            Node cur1 = loopHead1;
            while (cur1 != null) {
                if (cur1.next == null) {
                    loopTail1 = cur1;
                }
                cur1 = cur1.next;
            }
            loopTail1.next = loopHead1;

            Node loopHead2 = generateRandomLinkedList(100, 100);
            if (loopHead2 == null) {
                loopHead2 = new Node(-1);
            }
            loopHead2.next = new Node(0);
            Node loopTail2 = null;
            Node cur2 = loopHead2;
            while (cur2 != null) {
                if (cur2.next == null) {
                    loopTail2 = cur2;
                }
                cur2 = cur2.next;
            }
            loopTail2.next = loopHead2;

            Node node1 = generateRandomLinkedList(100, 100);
            Node tail1 = null;
            Node cur3 = node1;
            while (cur3 != null) {
                if (cur3.next == null) {
                    tail1 = cur3;
                }
                cur3 = cur3.next;
            }
            if (tail1 != null) {
                tail1.next = loopHead1;
            } else {
                node1 = loopHead1;
            }

            Node node2 = generateRandomLinkedList(100, 100);
            Node tail2 = null;
            Node cur4 = node2;
            while (cur4 != null) {
                if (cur4.next == null) {
                    tail2 = cur4;
                }
                cur4 = cur4.next;
            }
            if (tail2 != null) {
                tail2.next = loopHead2;
            } else {
                node2 = loopHead2;
            }

            assert Advanced.findCrossPoint(node1, node2) == null;
        }

        // case 6: 两个都成环且同一点入环
        for (int i = 0; i < 1000; i++) {
            Node loopHead = generateRandomLinkedList(5, 100);
            if (loopHead == null) {
                loopHead = new Node(-1);
            }
            loopHead.next = new Node(0);
            Node loopTail = null;
            Node cur1 = loopHead;
            while (cur1 != null) {
                if (cur1.next == null) {
                    loopTail = cur1;
                }
                cur1 = cur1.next;
            }
            loopTail.next = loopHead;

            Node commonStart = generateRandomLinkedList(5, 100);
            Node commonTail = null;
            Node cur2 = commonStart;
            while (cur2 != null) {
                if (cur2.next == null) {
                    commonTail = cur2;
                }
                cur2 = cur2.next;
            }
            if (commonTail != null) {
                commonTail.next = loopHead;
            } else {
                commonStart = loopHead;
            }

            Node node1 = generateRandomLinkedList(5, 100);
            Node tail1 = null;
            Node cur3 = node1;
            while (cur3 != null) {
                if (cur3.next == null) {
                    tail1 = cur3;
                }
                cur3 = cur3.next;
            }
            if (tail1 != null) {
                tail1.next = commonStart;
            } else {
                node1 = commonStart;
            }

            Node node2 = generateRandomLinkedList(5, 100);
            Node tail2 = null;
            Node cur4 = node2;
            while (cur4 != null) {
                if (cur4.next == null) {
                    tail2 = cur4;
                }
                cur4 = cur4.next;
            }
            if (tail2 != null) {
                tail2.next = commonStart;
            } else {
                node2 = commonStart;
            }
            assert Advanced.findCrossPoint(node1, node2) == commonStart;
        }

        // case 7: 两个都成环且不同点入环
        for (int i = 0; i < 1000; i++) {
            Node loopHead = generateRandomLinkedList(5, 100);
            if (loopHead == null) {
                loopHead = new Node(-1);
            }
            loopHead.next = new Node(0);
            Node loopTail = null;
            Node cur1 = loopHead;
            while (cur1 != null) {
                if (cur1.next == null) {
                    loopTail = cur1;
                }
                cur1 = cur1.next;
            }
            loopTail.next = loopHead;

            List<Node> list = new ArrayList<>();
            Node cur2 = loopHead.next;
            while (cur2 != loopHead) {
                list.add(cur2);
                cur2 = cur2.next;
            }

            Random random = new Random(System.nanoTime());
            Node anotherLoopHead = list.get(random.nextInt(list.size()));

            Node node1 = generateRandomLinkedList(100, 100);
            Node tail1 = null;
            Node cur3 = node1;
            while (cur3 != null) {
                if (cur3.next == null) {
                    tail1 = cur3;
                }
                cur3 = cur3.next;
            }
            if (tail1 != null) {
                tail1.next = loopHead;
            } else {
                node1 = loopHead;
            }

            Node node2 = generateRandomLinkedList(100, 100);
            Node tail2 = null;
            Node cur4 = node2;
            while (cur4 != null) {
                if (cur4.next == null) {
                    tail2 = cur4;
                }
                cur4 = cur4.next;
            }
            if (tail2 != null) {
                tail2.next = anotherLoopHead;
            } else {
                node2 = anotherLoopHead;
            }

            assert (Advanced.findCrossPoint(node1, node2) == loopHead
                    || Advanced.findCrossPoint(node1, node2) == anotherLoopHead);

        }

    }

    @Test
    public void testSelectSort() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomLinkedList(10, 100);
            Node copiedNode = CommonUtil.copyLinkedList(node);

            Node result1 = Advanced.selectSort(node);
            Node result2 = sort(copiedNode);

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
    public void testInsertSort() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomLinkedList(10, 100);
            Node copiedNode = CommonUtil.copyLinkedList(node);

            Node result1 = Advanced.insertSort(node);
            Node result2 = sort(copiedNode);

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
    public void testMergeSort() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomLinkedList(10, 100);
            Node copiedNode = CommonUtil.copyLinkedList(node);

            Node result1 = Advanced.mergeSort(node);
            Node result2 = sort(copiedNode);

            while (result1 != null && result2 != null) {
                assert result1.value == result2.value;
                result1 = result1.next;
                result2 = result2.next;
            }
            assert result1 == null;
            assert result2 == null;
        }
    }

    private Node sort(Node node) {
        int size = 0;
        Node cur = node;
        while (cur != null) {
            size++;
            cur = cur.next;
        }
        if (size > 0) {
            int[] arr = new int[size];
            cur = node;
            int index = 0;
            while (cur != null) {
                arr[index++] = cur.value;
                cur = cur.next;
            }

            Arrays.sort(arr);

            Node preHead = new Node(-1);
            cur = preHead;

            for (int i = 0; i < arr.length; i++) {
                cur.next = new Node(arr[i]);
                cur = cur.next;
            }
            return preHead.next;
        } else {
            return node;
        }
    }


}
