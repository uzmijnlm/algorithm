package org.example.arr;

/**
 * 给定一个数组，返回子数组的最大累加和
 * 要求：时间复杂度为O(N)，额外空间复杂度O(1)
 */
public class MaxSum {


    public static int maxSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int cur = 0;
        for (int i = 0; i != arr.length; i++) {
            cur += arr[i];
            max = Math.max(max, cur);
            cur = Math.max(cur, 0);
        }
        return max;
    }


    // 进阶问题：给定一个矩阵，返回子矩阵的最大累加和
    public int maxSum(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;

        for (int i = 0; i < m.length; i++) {
            int[] s = new int[m[0].length];
            for (int j = i; j < m.length; j++) {
                int cur = 0;
                for (int k = 0; k < s.length; k++) {
                    s[k] += m[j][k];
                    cur += s[k];
                    max = Math.max(max, cur);
                    cur = Math.max(cur, 0);
                }
            }
        }
        return max;
    }

}
