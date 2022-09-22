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
}
