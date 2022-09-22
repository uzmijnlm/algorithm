package org.example.arr.presum;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。
 * 如果不存在这样的 子数组 ，返回 -1 。
 */
public class ShortestSubArray {

    public static int shortestSubarray(int[] nums, int k) {
        long[] preSum = new long[nums.length+1];
        for (int i=1; i<preSum.length;i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }

        int ans = Integer.MAX_VALUE;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < preSum.length; i++) {
            while (!deque.isEmpty() && preSum[i] <= preSum[deque.peekLast()]) {
                deque.removeLast();
            }

            while (!deque.isEmpty() && preSum[i] - preSum[deque.peekFirst()] >= k) {
                ans = Math.min(ans, i - deque.removeFirst());
            }

            deque.addLast(i);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
