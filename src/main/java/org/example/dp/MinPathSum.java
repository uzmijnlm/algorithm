package org.example.dp;

/**
 * 给定一个矩阵m，从左上角开始每次只能向右或向下走，最后到达右下角的位置
 * 路径上所有数字累加和就是路径和，返回所有路径中最小的路径和
 */
public class MinPathSum {


    public int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];

        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + m[0][i];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    // 空间压缩
    public int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }

        if (m.length >= m[0].length) {
            int[] dp = new int[m[0].length];
            dp[0] = m[0][0];
            for (int i = 1; i < m[0].length; i++) { // 先求一行的值
                dp[i] = dp[i-1] + m[0][i];
            }
            for (int i = 1; i < m.length; i++) { // 一行行迭代
                dp[0] = dp[0] + m[i][0];
                for (int j = 1; j < m[0].length; j++) {
                    dp[j] = Math.min(dp[j-1], dp[j]) + m[i][j];
                }
            }
            return dp[m[0].length-1];
        } else {
            int[] dp = new int[m.length];
            dp[0] = m[0][0];
            for (int i = 1; i < m.length; i++) {
                dp[i] = dp[i-1] + m[i][0];
            }
            for (int i = 1; i < m[0].length; i++) {
                dp[0] = dp[0] + m[0][i];
                for (int j = 1; j < m.length; j++) {
                    dp[j] = Math.min(dp[j-1], dp[j]) + m[j][i];
                }
            }
            return dp[m.length-1];
        }
    }
}
