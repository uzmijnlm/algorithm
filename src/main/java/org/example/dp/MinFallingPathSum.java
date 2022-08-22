package org.example.dp;

/**
 * 给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
 * 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。
 * 在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第一个元素）。
 * 具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。
 */
public class MinFallingPathSum {


    public static int minFallingPathSum(int[][] A) {
        int[][] dp = new int[A.length][A[0].length];
        for (int i = 0; i < A[0].length; i++) {
            dp[0][i] = A[0][i];
        }

        for (int i = 1; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (j == 0) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j + 1]) + A[i][j];
                } else if (j == A[0].length - 1) {
                    dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + A[i][j];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i - 1][j - 1]), dp[i - 1][j + 1]) + A[i][j];
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < A[0].length; i++) {
            res = Math.min(res, dp[dp.length-1][i]);
        }
        return res;
    }
}
