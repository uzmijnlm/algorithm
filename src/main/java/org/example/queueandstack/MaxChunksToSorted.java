package org.example.queueandstack;

import java.util.Stack;

/**
 * 给定一个长度为 n 的整数数组 arr ，它表示在 [0, n - 1] 范围内的整数的排列。
 * 我们将 arr 分割成若干 块 (即分区)，并对每个块单独排序。将它们连接起来后，使得连接的结果和按升序排序后的原数组相同。
 * 返回数组能分成的最多块数量。
 * 进阶问题：arr内的元素为任意可重复整数
 */
public class MaxChunksToSorted {


    // 原问题和进阶问题均可用下面的方式求解
    public static int maxChunksToSorted(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            if (stack.isEmpty() || arr[i] >= stack.peek()) {
                stack.push(arr[i]);
            } else {
                int max = stack.pop();
                while (!stack.isEmpty() && arr[i] < stack.peek()) {
                    stack.pop();
                }
                stack.push(max);
            }
        }
        return stack.size();
    }
}
