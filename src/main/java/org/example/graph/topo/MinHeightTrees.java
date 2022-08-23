package org.example.graph.topo;

import java.util.*;

/**
 * 树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，一个任何没有简单环路的连通图都是一棵树。
 * 给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。
 * 给定数字 n 和一个有 n - 1 条无向边的 edges 列表（每一个边都是一对标签），
 * 其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条无向边。
 * 可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。
 * 在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
 * 请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
 * 树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
 */
class MinHeightTrees {

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if (n == 1) {
            res.add(0);
            return res;
        }
        Graph graph = Graph.createGraph(n, edges);
        Queue<Node> queue = new LinkedList<>();

        for (Node node : graph.nodes.values()) {
            if (node.degree == 1) {
                queue.add(node);
            }
        }


        while (!queue.isEmpty()) {
            res = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                res.add(cur.val);
   
                List<Node> neighbors = cur.neighbors;
                for (Node neighbor : neighbors) {
                    neighbor.degree--;
                    if (neighbor.degree == 1) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        return res;
    }


    static class Graph {
        Map<Integer, Node> nodes = new HashMap<>();

        private static Graph createGraph(int n, int[][] edges) {
            Graph graph = new Graph();
            for (int i=0; i<n; i++) {
                graph.nodes.put(i, new Node(i));
            }
            for (int i = 0; i<edges.length; i++) {
                int from = edges[i][0];
                int to = edges[i][1];
                Node fromNode = graph.nodes.get(from);
                Node toNode = graph.nodes.get(to);
                fromNode.neighbors.add(toNode);
                toNode.neighbors.add(fromNode);
                fromNode.degree++;
                toNode.degree++;
            }
            return graph;
        }
    }

    static class Node {
        int val;
        int degree;
        List<Node> neighbors = new ArrayList<>();

        public Node(int val) {
            this.val = val;
        }
    }

}