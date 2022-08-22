package org.example.queueandstack;

/**
 * 给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，表示两个自然数各位上的数字。
 * 现在从这两个数组中选出 k (k <= m + n) 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
 * 求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
 * 说明: 请尽可能地优化你算法的时间和空间复杂度。
 */
public class MaxNumber {

    public static int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int start = Math.max(0, k - n);
        int end = Math.min(k, m);
        int[] res = new int[k];
        for (int i = start; i <= end; i++) {
            int[] maxSeq1 = getMaxSeq(nums1, i);
            int[] maxSeq2 = getMaxSeq(nums2, k - i);
            int[] mergedSeq = merge(maxSeq1, maxSeq2);
            if (compare(mergedSeq, 0, res, 0)) {
                res = mergedSeq;
            }
        }
        return res;
    }

    private static int[] merge(int[] arr1, int[] arr2) {
        int index1 = 0;
        int index2 = 0;
        int index = 0;
        int[] res = new int[arr1.length + arr2.length];
        while (index1 < arr1.length && index2 < arr2.length) {
            if (compare(arr1, index1, arr2, index2)) {
                res[index++] = arr1[index1++];
            } else {
                res[index++] = arr2[index2++];
            }
        }
        while (index1 < arr1.length) {
            res[index++] = arr1[index1++];
        }
        while (index2 < arr2.length) {
            res[index++] = arr2[index2++];
        }
        return res;
    }

    private static boolean compare(int[] arr1, int i1, int[] arr2, int i2) {
        while (i1 < arr1.length && i2 < arr2.length) {
            if (arr1[i1] > arr2[i2]) {
                return true;
            } else if (arr1[i1] < arr2[i2]) {
                return false;
            }
            i1++;
            i2++;
        }
        return arr1.length - i1 > arr2.length - i2;
    }

    private static int[] getMaxSeq(int[] nums, int k) {
        if (k == 0) {
            return new int[0];
        }
        int[] stack = new int[k];
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            while (index > 0 && (nums.length - i > k- index) && nums[i] > stack[index-1]) {
                index--;
            }
            if (index < k) {
                stack[index++] = nums[i];
            }
        }
        return stack;
    }
}
