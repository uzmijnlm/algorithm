package org.example.arr.presum;

import java.util.TreeSet;

/**
 * 给你一个 m x n 的矩阵 matrix 和一个整数 k ，找出并返回矩阵内部矩形区域的不超过 k 的最大数值和。
 * 题目数据保证总会存在一个数值和不超过 k 的矩形区域。
 */
public class SumSubMatrix {

    public static int maxSumSubMatrix(int[][] matrix, int k) {
        int ans = Integer.MIN_VALUE;
        for (int topRow = 0; topRow < matrix.length; topRow++) {
            int[] preSum = new int[matrix[0].length+1];
            for (int bottomRow = topRow; bottomRow < matrix.length; bottomRow++) {
                int[] preSumThisLevel = new int[matrix[0].length+1];
                for (int col = 1; col <= matrix[0].length; col++) {
                    preSumThisLevel[col] = preSumThisLevel[col - 1] + matrix[bottomRow][col - 1];
                }
                for (int col = 1; col <= matrix[0].length; col++) {
                    preSum[col] += preSumThisLevel[col];
                }

                TreeSet<Integer> set = new TreeSet<>();
                for (int i = 1; i < preSum.length; i++) {
                    set.add(preSum[i - 1]);
                    Integer ceil = set.ceiling(preSum[i] - k);
                    if (ceil != null) {
                        ans = Math.max(ans, preSum[i] - ceil);
                    }
                }
            }
        }
        return ans;
    }
}
