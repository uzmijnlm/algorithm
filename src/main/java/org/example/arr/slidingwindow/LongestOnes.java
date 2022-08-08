package org.example.arr.slidingwindow;

/**
 * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1的最大个数 。
 */
public class LongestOnes {

    public static int longestOnes(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int countOfZeros = 0;
        int res = 0;
        while (right < nums.length) {
            if (nums[right++] == 0) {
                countOfZeros++;
            }

            while (left < right && countOfZeros > k) {
                if (nums[left++] == 0) {
                    countOfZeros--;
                }
            }

            res = Math.max(res, right - left);
        }

        return res;
    }
}
