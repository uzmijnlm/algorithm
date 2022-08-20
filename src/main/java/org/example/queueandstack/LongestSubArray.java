package org.example.queueandstack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给你一个整数数组 nums ，和一个表示限制的整数 limit，
 * 请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。
 * 如果不存在满足条件的子数组，则返回 0 。
 */
public class LongestSubArray {


    public static int longestSubarray(int[] nums, int limit) {
        Deque<Integer> windowMin = new LinkedList<>();
        Deque<Integer> windowMax = new LinkedList<>();
        int ans = 0;
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            while (!windowMin.isEmpty() && nums[right] < windowMin.peekLast()) {
                windowMin.pollLast();
            }
            while (!windowMax.isEmpty() && nums[right] > windowMax.peekLast()) {
                windowMax.pollLast();
            }
            windowMin.addLast(nums[right]);
            windowMax.addLast(nums[right]);
            while (!windowMin.isEmpty() && !windowMax.isEmpty()
                    && windowMax.peekFirst() - windowMin.peekFirst() > limit) {
                if (nums[left] == windowMin.peekFirst()) {
                    windowMin.pollFirst();
                }
                if (nums[left] == windowMax.peekFirst()) {
                    windowMax.pollFirst();
                }
                left++;
            }
            right++;
            ans = Math.max(ans, right - left);
        }
        return ans;
    }
}
