package org.example.graph.traverse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给你一个有 n 个节点的 有向无环图（DAG），请你找出所有从节点 0 到节点 n-1 的路径并输出（不要求按特定顺序）
 * graph[i] 是一个从节点 i 可以访问的所有节点的列表（即从节点 i 到节点 graph[i][j]存在一条有向边）。
 */
class AllPathsSourceTarget {

    public List<List<Integer>> allPathsSourceTargetDFS(int[][] graph) {
        Graph g = Graph.createGraph(graph);
        Node start = g.nodes.get(0);
        int n = graph.length - 1;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        boolean[] visited = new boolean[graph.length];
        tmp.add(start.val);
        visited[start.val] = true;
        dfs(start, result, tmp, n, visited);
        visited[start.val] = false;
        return result;
    }

    private void dfs(Node start, List<List<Integer>> result, List<Integer> tmp, int n, boolean[] visited) {
        for (Node next : start.nexts) {
            if (!visited[next.val]) {
                List<Integer> copy = new ArrayList<>(tmp);
                if (next.val == n) {
                    copy.add(next.val);
                    result.add(copy);
                } else {
                    visited[next.val] = true;
                    copy.add(next.val);
                    dfs(next, result, copy, n, visited);
                    visited[next.val] = false;
                }
            }
        }
    }




    static class Graph {
        Map<Integer, Node> nodes = new HashMap<>();

        private static Graph createGraph(int[][] g) {
            Graph graph = new Graph();
            for (int i=0; i<g.length; i++) {
                graph.nodes.put(i, new Node(i));
            }

            for (int i=0; i<g.length; i++) {
                int[] nexts = g[i];
                Node node = graph.nodes.get(i);
                for (int next : nexts) {
                    Node nextNode = graph.nodes.get(next);
                    node.nexts.add(nextNode);
                }
            }
            return graph;
        }
    }

    static class Node {
        int val;
        List<Node> nexts = new ArrayList<>();

        public Node(int val) {
            this.val = val;
        }
    }
}