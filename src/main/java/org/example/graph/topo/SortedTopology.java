package org.example.graph.topo;

import org.example.graph.Graph;
import org.example.graph.Node;

import java.util.*;

public class SortedTopology {

    public List<Node> sortedTopology(Graph graph) {
        // key: 某一个node
        // value: 剩余的入度
        Map<Node, Integer> inMap = new HashMap<>();
        // 入度为0的点才能进queue
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                queue.add(node);
            }
        }

        // 拓扑排序的结果，依次加入result
        List<Node> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    queue.add(next);
                }
            }
        }
        return result;
    }
}
