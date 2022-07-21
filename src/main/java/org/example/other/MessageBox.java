package org.example.other;

import java.util.Map;

/**
 * 一个消息流会不断吐出1～N，但不一定按顺序吐出
 * 如果上次打印的数为i，那么当i+1出现时，请打印i+1及其之后接受过的并连续的所有数，直到1~N全部接收并打印完，请设计这样一种结构MessageBox
 * 例如：
 * 消息流吐出2，MessageBox接收而不打印2，因为1还没出现
 * 消息流吐出1，MessageBox接收1，打印1 2
 * 消息流吐出4，MessageBox接收4，不打印
 * 消息流吐出5，MessageBox接收5，不打印
 * 消息流吐出7，MessageBox接收7，不打印
 * 消息流吐出3，MessageBox接收3，打印3 4 5
 * 要求：时间复杂度O(N)
 */
public class MessageBox {

    // 利用链表实现
    static class Node {
        public int num;
        public Node next;

        public Node(int num) {
            this.num = num;
        }
    }

    private Map<Integer, Node> headMap; // 维护连续区间第一个节点
    private Map<Integer, Node> tailMap; // 维护连续区间最后一个节点
    private int lastPrint; // 上次打印的数


    public void receive(int num) {
        if (num < 1) {
            return;
        }

        Node cur = new Node(num);
        headMap.put(num, cur);
        tailMap.put(num, cur);
        if (tailMap.containsKey(num - 1)) {
            tailMap.get(num - 1).next = cur;
            tailMap.remove(num - 1);
            headMap.remove(num);
        }
        if (headMap.containsKey(num + 1)) {
            cur.next = headMap.get(num + 1);
            tailMap.remove(num);
            headMap.remove(num + 1);
        }
        if (headMap.containsKey(lastPrint + 1)) {
            print();
        }
    }

    private void print() {
        Node node = headMap.get(++lastPrint);
        headMap.remove(lastPrint);
        while (node != null) {
            System.out.println(node.num + " ");
            node = node.next;
            lastPrint++;
        }
        tailMap.remove(--lastPrint);
        System.out.println();
    }
}
