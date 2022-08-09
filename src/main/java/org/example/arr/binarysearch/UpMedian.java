package org.example.arr.binarysearch;

/**
 * 给定两个有序数组，长度都是N，求两个数组中所有数的上中位数（即整体排序后中间两个数中的前一个数）
 * 要求：时间复杂度O(logN)，额外空间复杂度O(1)
 */
public class UpMedian {


    // 二分查找
    public static int getUpMedian(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length || arr1.length == 0) {
            throw new RuntimeException("invalid");
        }

        int start1 = 0;
        int end1 = arr1.length - 1;
        int start2 = 0;
        int end2 = arr2.length - 1;
        while (start1 < end1) {
            int mid1 = (start1 + end1) / 2;
            int mid2 = (start2 + end2) / 2;
            // 元素个数为奇数，则offset为0；元素个数为偶数，则offset为1
            int offset = ((end1 - start1 + 1) & 1) ^ 1;
            if (arr1[mid1] > arr2[mid2]) {
                end1 = mid1;
                start2 = mid2 + offset;
            } else if (arr1[mid1] < arr2[mid2]) {
                start1 = mid1 + offset;
                end2 = mid2;
            } else {
                return arr1[mid1];
            }
        }
        return Math.min(arr1[start1], arr2[start2]);
    }


    public static int getUpMedianRecur(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null || arr1.length != arr2.length || arr1.length == 0) {
            throw new RuntimeException("invalid");
        }

        return getUpMedianRecur(arr1, 0, arr1.length - 1, arr2, 0, arr2.length - 1);
    }

    private static int getUpMedianRecur(int[] arr1, int start1, int end1, int[] arr2, int start2, int end2) {
        if (end1 - start1 != end2 - start2) {
            throw new RuntimeException("invalid");
        }
        if (start1 == end1) {
            return Math.min(arr1[start1], arr2[start2]);
        }

        int mid1 = (start1 + end1) / 2;
        int mid2 = (start2 + end2) / 2;
        int offset = ((end1 - start1 + 1) & 1) ^ 1;
        if (arr1[mid1] == arr2[mid2]) {
            return arr1[mid1];
        } else if (arr1[mid1] > arr2[mid2]) {
            return getUpMedianRecur(arr1, start1, mid1, arr2, mid2 + offset, end2);
        } else {
            return getUpMedianRecur(arr1, mid1 + offset, end1, arr2, start2, mid2);
        }
    }


}
