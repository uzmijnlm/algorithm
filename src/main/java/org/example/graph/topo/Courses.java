package org.example.graph.topo;

import java.util.*;

/**
 * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。
 * 给你一个数组 prerequisites ，其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
 * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
 * 返回你为了学完所有课程所安排的学习顺序。
 * 可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
 */
class Courses {

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Graph graph = Graph.createGraph(numCourses, prerequisites);
        Map<Node, Integer> inMap = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            if (node.in != 0) {
                inMap.put(node, node.in);
            } else {
                queue.add(node);
            }
        }

        int[] res = new int[numCourses];
        int index = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            res[index++] = cur.val;
            numCourses--;
            if (numCourses == 0) {
                break;
            }
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    inMap.remove(next);
                    queue.add(next);
                }
            }
        }
        if (inMap.size() == 0) {
            return res;
        } else {
            return new int[]{};
        }
    }


    static class Graph {
        Map<Integer, Node> nodes = new HashMap<>();

        private static Graph createGraph(int numCourses, int[][] prerequisites) {
            Graph graph = new Graph();
            for (int i=0; i<numCourses; i++) {
                graph.nodes.put(i, new Node(i));
            }
            for (int i = 0; i<prerequisites.length; i++) {
                int[] tuple = prerequisites[i];
                int from = tuple[1];
                int to = tuple[0];
                Node fromNode = graph.nodes.get(from);
                Node toNode = graph.nodes.get(to);
                fromNode.nexts.add(toNode);
                toNode.in++;
            }
            return graph;
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