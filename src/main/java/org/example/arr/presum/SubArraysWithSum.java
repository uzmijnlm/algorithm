package org.example.arr.presum;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个二元数组 nums ，和一个整数 goal ，请你统计并返回有多少个和为 goal 的 非空 子数组。
 */
public class SubArraysWithSum {


    public static int numSubArraysWithSum(int[] nums, int goal) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int[] preSum = new int[nums.length + 1];
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        for (int i = 1; i < preSum.length; i++) {
            map.put(preSum[i-1], map.getOrDefault(preSum[i-1], 0) + 1);
            if (map.containsKey(preSum[i] - goal)) {
                res += map.get(preSum[i] - goal);
            }
        }
        return res;
    }



    /**
     * 给出矩阵 matrix 和目标值 target，返回元素总和等于目标值的非空子矩阵的数量。
     * 子矩阵 x1, y1, x2, y2 是满足 x1 <= x <= x2 且 y1 <= y <= y2 的所有单元 matrix[x][y] 的集合。
     * 如果 (x1, y1, x2, y2) 和 (x1', y1', x2', y2') 两个子矩阵中部分坐标不同（如：x1 != x1'），那么这两个子矩阵也不同。
     */
    public static int numSubMatrixSumTarget(int[][] matrix, int target) {
        int res = 0;
        for (int topRow=0; topRow<matrix.length; topRow++) {
            int[] sum = new int[matrix[0].length];
            for (int bottomRow=topRow; bottomRow<matrix.length; bottomRow++) {
                for (int col=0; col<matrix[0].length; col++) {
                    sum[col] += matrix[bottomRow][col];
                }

                res += process(sum, target);
            }
        }
        return res;
    }

    private static int process(int[] sum, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        int[] preSum = new int[sum.length + 1];
        for (int i=1; i<preSum.length; i++) {
            preSum[i] = preSum[i-1] + sum[i-1];
        }

        for (int i=1; i<preSum.length; i++) {
            map.put(preSum[i-1], map.getOrDefault(preSum[i-1], 0) + 1);
            if (map.containsKey(preSum[i] - target)) {
                res += map.get(preSum[i]-target);
            }
        }
        return res;
    }
}
