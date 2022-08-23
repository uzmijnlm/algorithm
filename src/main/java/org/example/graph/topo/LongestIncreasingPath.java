package org.example.graph.topo;

import java.util.*;

/**
 * 给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。
 * 对于每个单元格，你可以往上，下，左，右四个方向移动。 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）。
 */
class LongestIncreasingPath {


    public int longestIncreasingPath(int[][] matrix) {
        Graph graph = Graph.createGraph(matrix);
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            if (node.in == 0) {
                queue.add(node);
            }
        }

        int ans = 0;
        while (!queue.isEmpty()) {
            ans++;
            int size = queue.size();
            while (size != 0) {
                Node cur = queue.poll();
                for (Node next : cur.nexts) {
                    next.in--;
                    if (next.in == 0) {
                        queue.add(next);
                    }
                }
                size--;
            }
        }
        return ans;
    }


    static class Graph {
        Map<String, Node> nodes = new HashMap<>();

        private static Graph createGraph(int[][] matrix) {
            Graph graph = new Graph();
            for (int row = 0; row<matrix.length; row++) {
                for (int col = 0; col<matrix[0].length; col++) {
                    String index = row + "," + col;
                    Node node = new Node(matrix[row][col]);
                    graph.nodes.put(index, node);
                    dealWithNeighbors(row, col, graph, node);
                }
            }
            return graph;
        }

        private static void dealWithNeighbors(int row, int col, Graph graph, Node node) {
            if (row > 0) {
                String index = "" + (row-1) + "," + col;
                Node upNode = graph.nodes.get(index);
                if (upNode.val > node.val) {
                    node.nexts.add(upNode);
                    upNode.in++;
                } else if (node.val > upNode.val) {
                    upNode.nexts.add(node);
                    node.in++;
                }
            }
            if (col > 0) {
                String index = "" + row + "," + (col-1);
                Node leftNode = graph.nodes.get(index);
                if (leftNode.val > node.val) {
                    node.nexts.add(leftNode);
                    leftNode.in++;
                } else if (node.val > leftNode.val) {
                    leftNode.nexts.add(node);
                    node.in++;
                }
            }
        }
    }

    static class Node {
        int val;
        int in;
        List<Node> nexts = new ArrayList<>();

        public Node(int val) {
            this.val = val;
        }
    }


}