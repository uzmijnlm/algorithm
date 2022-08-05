package org.example.arr;

/**
 * 给定一个数组，返回排序后相邻两数的最大差值
 * 要求：时间复杂度O(N)
 */
public class MaxGap {

    // 利用桶排序的思想，步骤如下：
    // 1.遍历数组找到max和min
    // 2.准备N+1个桶，把max单独放在N+1号桶里。其余每个桶负责的区间大小是(max-min)/N
    //   那么一个数num就应该被放进 (num-min) / ((max-min)/N) = (num-min) * N / (max-min) 号桶
    // 3.将N个数放到N+1个桶里，必然最后会有空桶。产生最大差值的两个数一定来自不同的桶，因此只需要计算桶之间数的间距即可
    public int maxGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int len = nums.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        if (min == max) {
            return 0;
        }

        boolean[] hasNum = new boolean[len + 1]; // 记录每个桶是否有数
        int[] maxs = new int[len + 1]; // 记录每个桶的最大值
        int[] mins = new int[len + 1]; // 记录每个桶的最小值
        int bid;
        for (int i = 0; i < len; i++) {
            bid = bucket(nums[i], len, min, max); // 计算这个数被放进几号桶
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
            hasNum[bid] = true;
        }

        int res = 0;
        int lastMax = maxs[0];
        int i = 1;
        for (; i <= len; i++) {
            if (hasNum[i]) {
                res = Math.max(res, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return res;

    }

    // long类型防止相乘时溢出
    private int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }
}
