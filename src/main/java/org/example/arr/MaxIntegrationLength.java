package org.example.arr;

import java.util.HashSet;
import java.util.Set;

/**
 * 可整合数组的定义：如果一个数组在排序之后，每相邻两个数差的绝对值都为1，则该数组为可整合数组
 * 给定一个整型数组，返回其中最大可整合子数组的长度
 */
public class MaxIntegrationLength {


    // 核心思路：一个数组中如果没有重复元素，并且最大值减去最小值，再加1的结果等于元素个数，那么这个数组就是可整合数组
    // 验证每一个子数组是否为可整合数组
    public int getMaxIntegrationLength(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int len = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (int j = i; j < arr.length; j++) {
                if (set.contains(arr[j])) {
                    break;
                }
                set.add(arr[j]);
                max = Math.max(max, arr[j]);
                min = Math.min(min, arr[j]);
                if (max - min == j - i) {
                    len = Math.max(len, j - i + 1);
                }
            }
            set.clear();
        }
        return len;
    }
}
