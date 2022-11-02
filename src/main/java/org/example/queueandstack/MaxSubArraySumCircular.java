package org.example.queueandstack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
 * 环形数组 意味着数组的末端将会与开头相连呈环状。
 * 形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
 * 子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。
 * 形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
 */
public class MaxSubArraySumCircular {


    public static int maxSubarraySumCircular(int[] A) {
        int[] preSum = new int[A.length * 2 + 1];
        for (int i = 1; i < A.length * 2 + 1; i++) {
            preSum[i] = preSum[i - 1] + A[(i - 1 + A.length) % A.length];
        }

        Deque<Integer> deque = new LinkedList<>();
        int ans = Integer.MIN_VALUE;
        for (int i = 1; i < preSum.length; i++) {
            while (!deque.isEmpty() && i- deque.peekFirst() > A.length) {
                deque.pollFirst();
            }
            ans = Math.max(ans, preSum[i] - (deque.isEmpty() ? 0 : preSum[deque.peekFirst()]));
            while (!deque.isEmpty() && preSum[i] <= preSum[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        return ans;
    }
}
