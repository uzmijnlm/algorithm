package org.example.arr.slidingwindow;

/**
 * 给定一个含有 n 个正整数的数组和一个正整数 target 。
 * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。
 * 如果不存在符合条件的子数组，返回 0 。
 */
public class MinSubArrayLen {


    public static int minSubArrayLen(int target, int[] nums) {
        int minLen = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = 0;
        while (right < nums.length) {
            sum += nums[right];

            while (left <= right && sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left++];
            }

            right++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
