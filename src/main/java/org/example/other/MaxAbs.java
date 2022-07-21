package org.example.other;

/**
 * 给定一个长度为N（N>1）的整型数组arr，可以划分为左右两个部分，左部分为arr[0...K]，有部分为arr[K+1...N-1]
 * K可以取值的范围是[0,N-2]
 * 求在所有划分方案中，做部分的最大值减去有部分最大值的绝对值中，最大是多少
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class MaxAbs {

    // 预处理数组，节省时间复杂度。步骤如下：
    // 1.先求整个数组的最大值max
    // 2.如果max作为左部分最大值，接下来只要让右部分的最大值尽量小即可
    //   右部分只含有arr[N-1]的时候就是尽量小的时候
    // 3.如果max作为右部分最大值，做部分只含有arr[0]即可
    public int maxAbs(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(arr[i], max);
        }
        return max - Math.min(arr[0], arr[arr.length - 1]);
    }

}
