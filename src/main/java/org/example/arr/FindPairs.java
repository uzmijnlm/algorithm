package org.example.arr;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums 和一个整数 k，请你在数组中找出 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
 * k-diff 数对定义为一个整数对 (nums[i], nums[j]) ，并满足下述全部条件：
 * 0 <= i, j < nums.length
 * i != j
 * nums[i] - nums[j] == k
 * 注意，|val| 表示 val 的绝对值。
 */
public class FindPairs {


    public static int findPairs(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);

        int res = 0;

        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            while (j < nums.length && (nums[j] - nums[i] < k || j <= i)) {
                j++;
            }

            if (j < nums.length && nums[j] - nums[i] == k) {
                res++;
            }
        }
        return res;

    }
}
