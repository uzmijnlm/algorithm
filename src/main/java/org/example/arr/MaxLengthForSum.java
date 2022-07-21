package org.example.arr;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个无序数组，但每个值均为正数
 * 再给定一个正数k
 * 求数组所有子数组中所有元素相加为k的最长子数组长度
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class MaxLengthForSum {

    // 核心思路是双指针。步骤如下：
    // 1.用两个位置left和right来标记子数组左右两头，开始都为0
    // 2.变量sum表示子数组arr[left...right]的和，初始为arr[0]
    // 3.变量len记录累加和为k的所有子数组中最大子数组的长度
    // 4.比较sum与k
    //   a.如果sum==k，说明arr[left...right]累加和为k，更新len。然后开始从left+1位置开始考虑
    //   b.如果sum<k，令right加1，注意是否越界
    //   c.如果sum>k，令left加1
    // 5.重复步骤4直到结束
    public int getMaxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }

        int left = 0;
        int right = 0;
        int sum = arr[0];
        int len = 0;
        while (left <= right) {
            if (sum == k) {
                len = Math.max(len, right - left + 1);
                sum -= arr[left++];
            } else if (sum < k) {
                right++;
                if (right == arr.length) {
                    break;
                }
                sum += arr[right];
            } else {
                sum -= arr[left++];
            }
        }
        return len;
    }


    // 进阶问题：无序数组中的元素可正、可负、可0
    // 要求：时间复杂度O(N)，额外空间复杂度O(N)
    // 累加和问题
    public int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // key表示累加和，value表示出现这个累加和最早的位置
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 必须先初始化(0, -1)，表示一开始累加和就是0
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - k)) {
                len = Math.max(i - map.get(sum - k), len);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }

    // 补充问题1：给定一个无序数组，元素可正、可负、可0，求数组中所有子数组中正数与负数个数相等的最长子数组长度
    // 累加和的变体。将正数当成1，负数当成-1，进行累加即可
    public int maxLength1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // key表示累加和，value表示出现这个累加和最早的位置
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 必须先初始化(0, -1)，表示一开始累加和就是0
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                sum += 1;
            } else if (arr[i] < 0) {
                sum -= 1;
            }
            if (map.containsKey(sum)) {
                len = Math.max(i - map.get(sum), len);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }

    // 补充问题2：给定一个无序数组，元素只能是1或0，求数组所有子数组中0和1个数相等的最长子数组长度
    // 累加和的变体。将0当成-1，进行累加即可
    public int maxLength2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // key表示累加和，value表示出现这个累加和最早的位置
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1); // 必须先初始化(0, -1)，表示一开始累加和就是0
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                sum += 1;
            } else if (arr[i] == 0) {
                sum -= 1;
            }
            if (map.containsKey(sum)) {
                len = Math.max(i - map.get(sum), len);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }


    // 进阶问题2：给定一个无序数组，元素可正、可负、可0。给定一个整数k，求子数组中累加和小于等于k的最长子数组长度
    // 要求：时间复杂度O(N)，额外空间复杂度O(N)
    // 建立两个长度为N的数组minSums[]和minSumEnds[]
    // minSums[i]表示必须以arr[i]开头的所有子数组中，能够得到的最小累加和是多少
    // minSumEnds[i]表示必须以arr[i]开头的所有子数组中，如果得到了最小累加和，那么这个子数组右边界在哪个位置
    // 从右往左遍历一遍生成这两个数组，然后利用这两个数组求解
    public int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int[] minSums = new int[arr.length];
        int[] minSumEnds = new int[arr.length];
        initMinSumsAndMinSumEnds(arr, minSums, minSumEnds);

        // 将任意一个数arr[i]的位置i开头，minSumEnds[i]结尾视为一个窗口
        int end = 0; // 窗口最右位置的下一个位置
        int sum = 0; // 窗口累加和
        int res = 0; // 最终的结果
        for (int i = 0; i < arr.length; i++) { // 以i开头
            // 一个窗口一个窗口累加
            while (end < arr.length && sum + minSums[end] <= k) {
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            res = Math.max(res, end - i);

            if (end > i) { // 将之前大窗口中的数一一剔除
                sum -= arr[i];
            } else { // 都剔除完了都没有满足上面while的条件，说明必须从先前end的下一个位置开始（此时end==i，因此i+1就是end+1）
                end = i + 1;
            }
        }
        return res;
    }


    private void initMinSumsAndMinSumEnds(int[] arr, int[] minSums, int[] minSumEnds) {
        minSums[arr.length - 1] = arr[arr.length - 1];
        minSumEnds[arr.length - 1] = arr.length - 1;
        for (int i = arr.length - 2; i >= 0; i--) {
            if (minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
    }


}
