package org.example.arr.presum;

import java.util.HashSet;
import java.util.Set;

/**
 * 给你一个整数数组 nums 和一个整数 k ，编写一个函数来判断该数组是否含有同时满足下述条件的连续子数组：
 * 1.子数组大小 至少为 2 ，且
 * 2.子数组元素总和为 k 的倍数。
 * 如果存在，返回 true ；否则，返回 false 。
 * 如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。0 始终视为 k 的一个倍数。
 */
public class CheckSubArraySum {


    public static boolean checkSubarraySum(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();
        int[] preSum = new int[nums.length+1];
        for (int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }

        for (int i = 2; i < preSum.length; i++) {
            set.add(preSum[i-2] % k);

            if (set.contains(preSum[i] % k)) {
                return true;
            }
        }

        return false;

    }
}
