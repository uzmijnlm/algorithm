package org.example.arr.twopointer;

import java.util.Arrays;

/**
 * 给定一个包含非负整数的数组 nums ，返回其中可以组成三角形三条边的三元组个数。
 */
public class TriangleNumber {


    public static int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }

        Arrays.sort(nums);

        int res = 0;
        for (int longSide = 2; longSide < nums.length; longSide++) {
            int left = 0;
            int right = longSide - 1;
            while (left < right) {
                while (left < right && nums[left] + nums[right] <= nums[longSide]) {
                    left++;
                }
                res += right - left;
                right--;
            }
        }
        return res;
    }
}
