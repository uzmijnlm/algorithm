package org.example.graph.traverse;


import java.util.*;

/**
 * 克隆图
 */
public class CloneGraph {

    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    // 广度优先遍历
    public static Node cloneGraphBFS(Node node) {
        if (node == null) {
            return null;
        }

        Map<Node, Node> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        map.put(node, new Node(node.val));
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (Node neighbor : cur.neighbors) {
                if (!map.containsKey(neighbor)) {
                    Node cloneNeighbor = new Node(neighbor.val, new ArrayList<>());
                    map.put(neighbor, cloneNeighbor);
                    queue.add(neighbor);
                }
                map.get(cur).neighbors.add(map.get(neighbor));
            }
        }
        return map.get(node);
    }

    // 深度优先遍历
    public static Node cloneGraphDFS(Node node) {
        if (node == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>();
        return cloneGraphDFS(node, map);
    }

    private static Node cloneGraphDFS(Node node, Map<Node, Node> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }
        Node cloneNode = new Node(node.val);
        map.put(node, cloneNode);
        List<Node> neighbors = new ArrayList<>();
        for (Node neighbor : node.neighbors) {
            neighbors.add(cloneGraphDFS(neighbor, map));
        }
        cloneNode.neighbors = neighbors;
        return cloneNode;
    }
}
