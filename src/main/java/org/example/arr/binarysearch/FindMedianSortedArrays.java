package org.example.arr.binarysearch;

/**
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 */
public class FindMedianSortedArrays {


    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            throw new RuntimeException("invalid arr");
        }

        int totalLength = nums1.length + nums2.length;
        if (totalLength % 2 == 0) {
            return (findKthNum(nums1, nums2, totalLength / 2) + findKthNum(nums1, nums2, totalLength / 2 + 1)) / 2.0;
        } else {
            return findKthNum(nums1, nums2, totalLength / 2 + 1);
        }
    }

    private static double findKthNum(int[] nums1, int[] nums2, int k) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            throw new RuntimeException("invalid arr");
        }

        if (k < 1 || k > nums1.length + nums2.length) {
            throw new RuntimeException("invalid k");
        }

        int[] longer = nums1.length > nums2.length ? nums1 : nums2;
        int[] shorter = nums1.length > nums2.length ? nums2 : nums1;
        if (k <= shorter.length) {
            return getUpMedian(shorter, 0, k - 1, longer, 0, k - 1);
        } else if (k > longer.length) {
            if (longer[k - shorter.length - 1] >= shorter[shorter.length - 1]) {
                return longer[k - shorter.length - 1];
            }
            if (shorter[k - longer.length - 1] >= longer[longer.length - 1]) {
                return shorter[k - longer.length - 1];
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

    private static double getUpMedian(int[] arr1, int start1, int end1, int[] arr2, int start2, int end2) {
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
