package org.example.graph;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int value;
    public int in;
    public int out;
    public List<Node> nexts;
    public List<Edge> edges;

    public Node(int value) {
        this.value = value;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
