package org.example.graph.dijkstra;

import org.example.graph.Edge;
import org.example.graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 输入一个点，返回图中这个点到所有点的最短距离，到达不了的点的距离是正无穷
 * 所有边的权值为正数
 */
public class Dijkstra {

    // 核心思路：先构造一张表，表示输入点A到所有点的距离。到自己的距离是0，到其他所有点的距离是正无穷
    //         在表中选最小的记录，此时把A选出来，看从A能跳到哪些节点，更新它到其他点的距离
    //         用完A点以后，在表中锁定它，再也不使用。然后再选表中最小的记录出来，重复此步骤，如果距离更小就更新
    public Map<Node, Integer> dijkstra(Node from) {
        // 如果表中没有某个key的记录，表示距离为正无穷
        Map<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 记录再也不使用的点
        Set<Node> selectedNodes = new HashSet<>();
        Node minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            int distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnSelectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    private Node getMinDistanceAndUnSelectedNode(Map<Node, Integer> distanceMap, Set<Node> selectedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!selectedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }


    // 因为普通的方法需要每次都遍历map，浪费了很多时间，所以可以考虑用小根堆维护这些记录，每次弹出顶部元素即可
    // 但是因为需要修改堆中记录的值，会导致堆发生变动，只能自己实现堆
    public Map<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        Map<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }
        return result;
    }

    static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }


    static class NodeHeap {
        // 堆
        private final Node[] nodes;

        // 维护每个点在堆中的位置。如果value为-1，说明之前进来过，但被弹出了
        private final Map<Node, Integer> heapIndexMap;

        // 维护源节点到每个点的最小距离
        private final Map<Node, Integer> distanceMap;

        // 当前堆中有多少节点
        private int size;

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 发现了源节点到node节点一条新路径，距离是distance
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) { // 如果node在堆中，如果distance更小则更新，并且由于distance可能变得更小，因此许阿哟insertHeapify
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) { // 如果node从来没进过堆，则添加并insertHeapify
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
            // 如果node进过堆，但是已经弹出了，则忽略
        }

        private void insertHeapify(Node node, Integer index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1); // 元素已弹出，在heapIndexMap中将value设置为-1，表示曾经在堆中，但已弹出
            distanceMap.remove(nodes[size - 1]);
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size
                        && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ? left + 1 : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }

    }


}
