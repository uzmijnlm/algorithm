package org.example.queueandstack;

import java.util.Stack;

/**
 * 给你一个整数数组 nums ，数组中共有 n 个整数。
 * 132 模式的子序列 由三个整数 nums[i]、nums[j] 和 nums[k] 组成，
 * 并同时满足：i < j < k 和 nums[i] < nums[k] < nums[j] 。
 * 如果 nums 中存在 132 模式的子序列 ，返回 true ；否则，返回 false 。
 */
public class Find132Pattern {

    public static boolean find132pattern(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int k = -1; // k是3的下标
        for (int i = nums.length - 1; i >= 0; i--) {
            if (k != -1 && nums[i] < nums[k]) { // i是1的下标
                return true;
            } else {
                while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                    k = stack.pop(); // 栈顶元素是2的下标
                }
                stack.push(i);
            }
        }
        return false;
    }
}
