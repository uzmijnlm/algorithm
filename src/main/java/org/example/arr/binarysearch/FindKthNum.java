package org.example.arr.binarysearch;

/**
 * 给定两个有序数组arr1和arr2，再给定一个整数k，返回整体第k小的数
 * 两数组长度分别为N和M，要求时间复杂度O(log(min{M, N}))，额外空间复杂度O(1)
 */
public class FindKthNum {

    public static int findKthNum(int[] arr1, int[] arr2, int k) {
        if (arr1 == null || arr2 == null || arr1.length == 0 || arr2.length == 0) {
            throw new RuntimeException("invalid arr");
        }

        if (k < 1 || k > arr1.length + arr2.length) {
            throw new RuntimeException("invalid k");
        }

        int[] longer = arr1.length > arr2.length ? arr1 : arr2;
        int[] shorter = arr1.length > arr2.length ? arr2 : arr1;
        if (k <= shorter.length) {
            return getUpMedian(arr1, 0, k - 1, arr2, 0, k - 1);
        } else if (k > longer.length) {
            if (shorter[k - longer.length - 1] >= longer[longer.length - 1]) {
                return shorter[k - longer.length - 1];
            }
            if (longer[k - shorter.length - 1] >= shorter[shorter.length - 1]) {
                return longer[k - shorter.length - 1];
            }
            return getUpMedian(shorter, k - longer.length, shorter.length - 1,
                    longer, k - shorter.length, longer.length - 1);
        } else {
            if (longer[k - shorter.length - 1] >= shorter[shorter.length - 1]) {
                return longer[k - shorter.length - 1];
            }
            return getUpMedian(shorter, 0, shorter.length - 1, longer, k - shorter.length, k - 1);
        }
    }

    private static int getUpMedian(int[] arr1, int start1, int end1, int[] arr2, int start2, int end2) {
        if (end1 - start1 != end2 - start2) {
            throw new RuntimeException("invalid range");
        }

        while (start1 < end1) {
            int mid1 = (start1 + end1) / 2;
            int mid2 = (start2 + end2) / 2;
            int offset = ((end1 - start1 + 1) & 1) ^ 1;
            if (arr1[mid1] == arr2[mid2]) {
                return arr1[mid1];
            } else if (arr1[mid1] > arr2[mid2]) {
                end1 = mid1;
                start2 = mid2 + offset;
            } else {
                end2 = mid2;
                start1 = mid1 + offset;
            }
        }
        return Math.min(arr1[start1], arr2[start2]);
    }
}
