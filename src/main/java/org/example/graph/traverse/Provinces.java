package org.example.graph.traverse;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。
 * 如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 * 给你一个 n x n 的矩阵 isConnected ，
 * 其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
 * 返回矩阵中 省份 的数量。
 */
class Provinces {

    public int findCircleNumDFS(int[][] isConnected) {
        int ans = 0;
        boolean[] visited = new boolean[isConnected.length];
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(visited, isConnected, i);
                ans++;
            }
        }
        return ans;
    }


    private void dfs(boolean[] visited, int[][] isConnected, int i) {
        for (int j = 0; j < isConnected.length; j++) {
            if (!visited[j] && isConnected[i][j] == 1) {
                visited[j] = true;
                dfs(visited, isConnected, j);
            }
        }
    }


    public int findCircleNumBFS(int[][] isConnected) {
        int ans = 0;
        boolean[] visited = new boolean[isConnected.length];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < isConnected.length; i++) {
            if (!visited[i]) {
                visited[i] = true;

                // bfs
                queue.add(i);
                while (!queue.isEmpty()) {
                    int curIndex = queue.poll();
                    for (int j = 0; j < isConnected.length; j++) {
                        if (!visited[j] && isConnected[curIndex][j] == 1) {
                            visited[j] = true;
                            queue.add(j);
                        }
                    }
                }

                ans++;
            }
        }
        return ans;
    }


}