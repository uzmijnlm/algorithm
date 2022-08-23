package org.example.graph.traverse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 存在一个 无向图 ，图中有 n 个节点。其中每个节点都有一个介于 0 到 n - 1 之间的唯一编号。
 * 给你一个二维数组 graph ，其中 graph[u] 是一个节点数组，由节点 u 的邻接节点组成。
 * 形式上，对于 graph[u] 中的每个 v ，都存在一条位于节点 u 和节点 v 之间的无向边。该无向图同时具有以下属性：
 * 不存在自环（graph[u] 不包含 u）。
 * 不存在平行边（graph[u] 不包含重复值）。
 * 如果 v 在 graph[u] 内，那么 u 也应该在 graph[v] 内（该图是无向图）
 * 这个图可能不是连通图，也就是说两个节点 u 和 v 之间可能不存在一条连通彼此的路径。
 * 二分图 定义：如果能将一个图的节点集合分割成两个独立的子集 A 和 B ，
 * 并使图中的每一条边的两个节点一个来自 A 集合，一个来自 B 集合，就将这个图称为 二分图 。
 * 如果图是二分图，返回 true ；否则，返回 false 。
 */
class IsBipartite {

    public boolean isBipartiteDFS(int[][] graph) {
        Graph g = Graph.createGraph(graph);
        int[] visited = new int[g.nodes.size()];
        boolean ans = false;
        for (Node node : g.nodes.values()) {
            if (visited[node.val] == 0) {
                visited[node.val] = 1;
                if (!dfs(visited, node.neighbors, 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean dfs(int[] visited, Map<Integer, Node> neighbors, int color) {
        for (Node neighbor : neighbors.values()) {
            if (visited[neighbor.val] == 0) {
                visited[neighbor.val] = -color;
                if (!dfs(visited, neighbor.neighbors, -color)) {
                    return false;
                }
            } else if (visited[neighbor.val] == color) {
                return false;
            }
        }
        return true;
    }


    public boolean isBipartiteBFS(int[][] graph) {
        Graph g = Graph.createGraph(graph);
        int[] visited = new int[g.nodes.size()];
        boolean ans = false;
        Queue<Node> queue = new LinkedList<>();
        for (Node node : g.nodes.values()) {
            if (visited[node.val] == 0) {
                visited[node.val] = 1;
                // bfs
                queue.add(node);
                while (!queue.isEmpty()) {
                    Node cur = queue.poll();
                    for (Node neighbor : cur.neighbors.values()) {
                        if (visited[neighbor.val] == 0) {
                            visited[neighbor.val] = -visited[cur.val];
                            queue.add(neighbor);
                        } else if (visited[neighbor.val] == visited[cur.val]) {
                            return false;
                        }
                    }
                }

            }
        }
        return true;
    }






    static class Graph {
        Map<Integer, Node> nodes = new HashMap<>();

        private static Graph createGraph(int[][] graph) {
            Graph g = new Graph();
            for (int i=0; i<graph.length; i++) {
                int[] neighbors = graph[i];
                if (!g.nodes.containsKey(i)) {
                    g.nodes.put(i, new Node(i));
                }
                Node node = g.nodes.get(i);
                for (int index : neighbors) {
                    if (!g.nodes.containsKey(index)) {
                        g.nodes.put(index, new Node(index));
                    }
                    Node neighbor = g.nodes.get(index);
                    if (!node.neighbors.containsKey(index)) {
                        node.neighbors.put(index, neighbor);
                    }
                    if (!neighbor.neighbors.containsKey(i)) {
                        neighbor.neighbors.put(i, node);
                    }
                }
            }
            return g;
        }
    }

    static class Node {
        int val;
        Map<Integer, Node> neighbors = new HashMap<>();

        public Node(int val) {
            this.val = val;
        }
    }
}