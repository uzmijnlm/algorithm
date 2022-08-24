package org.example.other.unionfind;

/**
 * 树可以看成是一个连通且 无环 的 无向 图。
 * 给定往一棵 n 个节点 (节点值 1～n) 的树中添加一条边后的图。
 * 添加的边的两个顶点包含在 1 到 n 中间，且这条附加的边不属于树中已存在的边。
 * 图的信息记录于长度为 n 的二维数组 edges ，edges[i] = [ai, bi] 表示图中在 ai 和 bi 之间存在一条边。
 * 请找出一条可以删去的边，删除后可使得剩余部分是一个有着 n 个节点的树。
 * 如果有多个答案，则返回数组 edges 中最后出现的边。
 */
class FindRedundantConnection {

    public int[] findRedundantConnection(int[][] edges) {
        int[] fatherMap = new int[edges.length + 1];
        for (int i = 1; i <= edges.length; i++) {
            fatherMap[i] = i;
        }

        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int node1 = edge[0];
            int node2 = edge[1];
            if (findHead(node1, fatherMap) != findHead(node2, fatherMap)) {
                union(node1, node2, fatherMap);
            } else {
                return edge;
            }
        }
        return null;
    }


    private int findHead(int node, int[] fatherMap) {
        if (fatherMap[node] != node) {
            fatherMap[node] = findHead(fatherMap[node], fatherMap);
        }
        return fatherMap[node];
    }


    private void union(int node1, int node2, int[] fatherMap) {
        fatherMap[findHead(node1, fatherMap)] = findHead(node2, fatherMap);
    }
}

