package org.example.arr.slidingwindow;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums 和一个整数 k，请你在数组中找出 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
 * k-diff 数对定义为一个整数对 (nums[i], nums[j]) ，并满足下述全部条件：
 * 0 <= i, j < nums.length
 * i != j
 * nums[i] - nums[j] == k
 */
public class FindPairs {


    public static int findPairs(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);

        int res = 0;

        int left = 0;
        int right = 1;
        while (right < nums.length) {
            while (left < right - 1 && nums[right] - nums[left] > k) {
                left++;
            }

            if (nums[right] - nums[left] == k) {
                res++;
                while (right < nums.length - 1 && nums[right] == nums[right + 1]) {
                    right++;
                }
                while (left < right - 1 && nums[left] == nums[left + 1]) {
                    left++;
                }
            }

            right++;
        }
        return res;

    }
}
