package org.example.arr.binarysearch;

/**
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 */
public class SearchRange {


    public static int[] searchRange(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else if (nums[mid] < target){
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (nums[left] != target) {
            return new int[]{-1, -1};
        }

        int L = left;
        left = 0;
        right = nums.length - 1;
        while (left < right) {
            int mid = (left + right + 1) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target){
                left = mid;
            } else {
                left = mid;
            }
        }
        return new int[]{L, right};
    }
}
