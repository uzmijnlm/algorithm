package org.example.other.unionfind;

/**
 * 在本问题中，有根树指满足以下条件的 有向 图。该树只有一个根节点，所有其他节点都是该根节点的后继。
 * 该树除了根节点之外的每一个节点都有且只有一个父节点，而根节点没有父节点。
 * 输入一个有向图，该图由一个有着 n 个节点（节点值不重复，从 1 到 n）的树及一条附加的有向边构成。
 * 附加的边包含在 1 到 n 中的两个不同顶点间，这条附加的边不属于树中已存在的边。
 * 结果图是一个以边组成的二维数组 edges 。
 * 每个元素是一对 [ui, vi]，用以表示 有向 图中连接顶点 ui 和顶点 vi 的边，其中 ui 是 vi 的一个父节点。
 * 返回一条能删除的边，使得剩下的图是有 n 个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
 */
class FindRedundantDirectedConnection {

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] fatherMap = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            fatherMap[i] = i;
        }
        int[] parent = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            parent[i] = i;
        }
        int conflict = -1;
        int cycle = -1;
        for (int i = 0; i < n; ++i) {
            int[] edge = edges[i];
            int node1 = edge[0];
            int node2 = edge[1];
            if (parent[node2] != node2) {
                conflict = i;
            } else {
                parent[node2] = node1;
                if (findHead(node1, fatherMap) == findHead(node2, fatherMap)) {
                    cycle = i;
                } else {
                    union(node1, node2, fatherMap);
                }
            }
        }
        if (conflict < 0) {
            return edges[cycle];
        } else {
            int[] conflictEdge = edges[conflict];
            if (cycle >= 0) {
                return new int[]{parent[conflictEdge[1]], conflictEdge[1]};
            } else {
                return conflictEdge;
            }
        }
    }

    private void union(int node1, int node2, int[] fatherMap) {
        fatherMap[findHead(node1, fatherMap)] = findHead(node2, fatherMap);
    }


    private int findHead(int node, int[] fatherMap) {
        if (fatherMap[node] != node) {
            fatherMap[node] = findHead(fatherMap[node], fatherMap);
        }
        return fatherMap[node];
    }
}


