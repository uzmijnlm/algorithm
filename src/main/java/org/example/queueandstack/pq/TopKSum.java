package org.example.queueandstack.pq;

import java.util.*;

/**
 * 给定两个有序数组arr1和arr2，再给定一个整数k，返回来自arr1和arr2的两个数相加和最大的前k个，两个数必须分别来自两个数组
 * 要求：时间复杂度O(klogk)
 */
public class TopKSum {

    // 因为是两个有序数组，所以从末尾开始相加判断即可。用堆排序
    // 初始时把(M-1,N-1)放入堆中，然后弹出，把(M-2,N-1)和(M-1,N-2)放入堆中，依次类推。堆的大小为k
    static class Node {
        public int index1;
        public int index2;
        public int value;

        public Node(int i1, int i2, int sum) {
            index1 = i1;
            index2 = i2;
            value = sum;
        }


        static class MaxHeapComparator implements Comparator<Node> {
            @Override
            public int compare(Node o1, Node o2) {
                return o2.value - o1.value;
            }
        }


        public int[] topKSum(int[] arr1, int[] arr2, int topK) {
            if (arr1 == null || arr2 == null || topK < 1) {
                return null;
            }

            topK = Math.min(topK, arr1.length * arr2.length);
            int[] res = new int[topK];
            int resIndex = 0;
            Queue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
            Set<String> positionSet = new HashSet<>();
            int i1 = arr1.length - 1;
            int i2 = arr2.length - 1;
            maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2]));
            while (resIndex != topK) {
                Node cur = maxHeap.poll();
                res[resIndex++] = cur.value;
                i1 = cur.index1;
                i2 = cur.index2;
                if (!positionSet.contains((i1 - 1) + "_" + i2)) {
                    positionSet.add((i1 - 1) + "_" + i2);
                    maxHeap.add(new Node(i1 - 1, i2, arr1[i1 - 1] + arr2[i2]));
                }
                if (!positionSet.contains((i1 + "_" + (i2 - 1)))) {
                    positionSet.add(i1 + "_" + (i2 - 1));
                    maxHeap.add(new Node(i1, i2 - 1, arr1[i1] + arr2[i2 - 1]));
                }
            }
            return res;
        }
    }


}
