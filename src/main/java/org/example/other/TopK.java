package org.example.other;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定String类型的数组strArr，再给定整数k，请严格按照排名顺序打印出现次数前k名的字符串
 * 如果词频一样，随便打印哪个都行
 * 要求：时间复杂度O(Nlogk)
 */
public class TopK {

    static class Node {
        public String str;
        public int times;

        public Node(String s, int t) {
            str = s;
            times = t;
        }
    }


    // 词频表+小根堆
    public void printTopKAndRank(String[] arr, int topK) {
        if (arr == null || topK < 1) {
            return;
        }

        // 词频表
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            String cur = arr[i];
            if (!map.containsKey(cur)) {
                map.put(cur, 1);
            } else {
                map.put(cur, map.get(cur) + 1);
            }
        }

        // 小根堆
        Node[] heap = new Node[topK];
        int index = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String str = entry.getKey();
            int times = entry.getValue();
            Node node = new Node(str, times);
            if (index != topK) {
                heap[index] = node;
                heapInsert(heap, index++);
            } else {
                if (heap[0].times < node.times) {
                    heap[0] = node;
                    heapify(heap, 0, topK);
                }
            }
        }

        // 小根堆排序
        for (int i = index - 1; i != 0; i--) {
            swap(heap, 0, i);
            heapify(heap, 0, i);
        }

        // 打印
        for (int i = 0; i != heap.length; i++) {
            if (heap[i] == null) {
                break;
            } else {
                System.out.print("No." + (i + 1) + ": ");
                System.out.print(heap[i].str + ", times: ");
                System.out.println(heap[i].times);
            }
        }


    }

    private void heapify(Node[] heap, int index, int heapSize) {
        int left = index * 2 + 1;
        int right = index * 2 + 2;
        int smallest = index;
        while (left < heapSize) {
            if (heap[left].times < heap[index].times) {
                smallest = left;
            }
            if (right < heapSize && heap[right].times < heap[smallest].times) {
                smallest = right;
            }
            if (smallest != index) {
                swap(heap, smallest, index);
            } else {
                break;
            }
            index = smallest;
            left = index * 2 + 1;
            right = index * 2 + 2;
        }
    }

    private void heapInsert(Node[] heap, int index) {
        while (index != 0) {
            int parent = (index - 1) / 2;
            if (heap[index].times < heap[parent].times) {
                swap(heap, parent, index);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void swap(Node[] heap, int i1, int i2) {
        Node tmp = heap[i1];
        heap[i1] = heap[i2];
        heap[i2] = tmp;
    }


    // 进阶问题：设计并实现TopKRecord结构，可以不断向其中加入字符串，并且随时打印加入次数最多的前k个字符
    // 要求：add方法时间复杂度不超过O(logk)，printTopK方法时间复杂度不超过O(k)
    static class TopKRecord {
        private final Node[] heap; // 按词频统计的小根堆，大小为k
        private int index; // 堆的当前大小
        private final Map<String, Node> strNodeMap;
        private final Map<Node, Integer> nodeIndexMap; // 记录对象在堆中的位置，如果一个对象不在堆中，value为-1

        public TopKRecord(int size) {
            heap = new Node[size];
            strNodeMap = new HashMap<>();
            nodeIndexMap = new HashMap<>();
        }

        public void add(String str) {
            Node curNode;
            int preIndex = -1; // 之前在堆中的位置
            if (!strNodeMap.containsKey(str)) {
                curNode = new Node(str, 1);
                strNodeMap.put(str, curNode);
                nodeIndexMap.put(curNode, -1);
            } else {
                curNode = strNodeMap.get(str);
                curNode.times++;
                preIndex = nodeIndexMap.get(curNode);
            }

            if (preIndex == -1) { // 之前不在堆中
                if (index == heap.length) { // 堆已经满了
                    if (heap[0].times < curNode.times) {
                        nodeIndexMap.put(heap[0], -1);
                        nodeIndexMap.put(curNode, 0);
                        heap[0] = curNode;
                        heapify(0, index);
                    }
                } else { // 堆没有满，直接放入堆中
                    nodeIndexMap.put(curNode, index);
                    heap[index] = curNode;
                    heapInsert(index++);
                }
            } else { // 之前在堆中
                heapify(preIndex, index);
            }
        }


        public void printTopK() {
            System.out.println("TOP: ");
            for (int i = 0; i != heap.length; i++) {
                if (heap[0] == null) {
                    break;
                }
                System.out.print("Str: " + heap[i].str);
                System.out.println(" Times: " + heap[i].times);
            }
        }

        private void heapInsert(int index) {
            while (index != 0) {
                int parent = (index - 1) / 2;
                if (heap[index].times < heap[parent].times) {
                    swap(parent, index);
                    index = parent;
                } else {
                    break;
                }
            }
        }

        private void heapify(int index, int heapSize) {
            int l = index * 2 + 1;
            int r = index * 2 + 2;
            int smallest = index;
            while (l < heapSize) {
                if (heap[l].times < heap[index].times) {
                    smallest = l;
                }
                if (r < heapSize && heap[r].times < heap[smallest].times) {
                    smallest = r;
                }

                if (smallest != index) {
                    swap(smallest, index);
                } else {
                    break;
                }
                l = index * 2 + 1;
                r = index * 2 + 2;
            }
        }

        private void swap(int i1, int i2) {
            nodeIndexMap.put(heap[i1], i2);
            nodeIndexMap.put(heap[i2], i1);
            Node tmp = heap[i1];
            heap[i1] = heap[i2];
            heap[i2] = tmp;
        }
    }

}
