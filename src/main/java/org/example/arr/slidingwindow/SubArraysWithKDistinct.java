package org.example.arr.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个正整数数组 nums和一个整数 k ，返回 num 中 「好子数组」 的数目。
 * 如果 nums 的某个子数组中不同整数的个数恰好为 k，则称 nums 的这个连续、不一定不同的子数组为 「好子数组 」。
 * 例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。
 */
public class SubArraysWithKDistinct {

    public static int subArraysWithKDistinct(int[] nums, int k) {
        return process(nums, k) - process(nums, k - 1);
    }

    private static int process(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int count = 0;
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        while (right < nums.length) {
            if (map.containsKey(nums[right])) {
                map.put(nums[right], map.get(nums[right]) + 1);
            } else {
                map.put(nums[right], 1);
                count++;
            }


            while (left <= right && count > k) {
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0) {
                    map.remove(nums[left]);
                    count--;
                }
                left++;
            }

            ans += right - left + 1;

            right++;
        }
        return ans;
    }
}
