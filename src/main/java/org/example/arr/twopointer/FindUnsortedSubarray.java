package org.example.arr.twopointer;

/**
 * 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
 * 请你找出符合题意的 最短 子数组，并输出它的长度。
 */
public class FindUnsortedSubarray {

    public static int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = nums[0];
        int end = -1;
        int min = nums[nums.length - 1];
        int begin = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= max) {
                max = nums[i];
            } else {
                end = i;
            }

            if (nums[nums.length - i - 1] <= min) {
                min = nums[nums.length - i - 1];
            } else {
                begin = nums.length - i - 1;
            }
        }

        return end - begin + 1;
    }
}
