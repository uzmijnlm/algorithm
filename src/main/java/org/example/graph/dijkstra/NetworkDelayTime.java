package org.example.graph.dijkstra;

import java.util.*;

class NetworkDelayTime {

    public int networkDelayTime(int[][] times, int n, int k) {
        Graph graph = Graph.createGraph(times, n);
        Node node = graph.nodes.get(k);
        Map<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(node, 0);
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
        if (distanceMap.size() == n) {
            int res = 0;
            for (int distance : distanceMap.values()) {
                res = Math.max(res, distance);
            }
            return res;
        } else {
            return -1;
        }
    }


    private Node getMinDistanceAndUnSelectedNode(Map<Node, Integer> distanceMap, Set<Node> selectedNodes) {
        int minDistance = Integer.MAX_VALUE;
        Node minNode = null;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node cur = entry.getKey();
            int distance = entry.getValue();
            if (!selectedNodes.contains(cur) && distance < minDistance) {
                minDistance = distance;
                minNode = cur;
            }
        }
        return minNode;
    }


    static class Graph {
        Map<Integer, Node> nodes = new HashMap<>();

        private static Graph createGraph(int[][] times, int n) {
            Graph graph = new Graph();
            for (int i = 1; i <= n; i++) {
                graph.nodes.put(i, new Node(i));
            }

            for (int i = 0; i < times.length; i++) {
                int[] tuple3 = times[i];
                int from = tuple3[0];
                int to = tuple3[1];
                int weight = tuple3[2];
                Node fromNode = graph.nodes.get(from);
                Node toNode = graph.nodes.get(to);
                Edge newEdge = new Edge(weight, fromNode, toNode);
                fromNode.nexts.add(toNode);
                fromNode.edges.add(newEdge);
            }
            return graph;
        }
    }

    static class Node {
        int val;
        List<Edge> edges = new ArrayList<>();
        List<Node> nexts = new ArrayList<>();

        public Node(int val) {
            this.val = val;
        }
    }

    static class Edge {
        int weight;
        Node from;
        Node to;

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }
}