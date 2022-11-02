package org.example.arr.twopointer;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个 排序好 的数组 arr ，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。返回的结果必须要是按升序排好的。
 * 整数 a 比整数 b 更接近 x 需要满足：
 * |a - x| < |b - x| 或者
 * |a - x| == |b - x| 且 a < b
 */
public class FindClosestElements {


    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        if (arr == null || arr.length < k) {
            return new ArrayList<>();
        }

        int index = binarySearch(arr, x);
        int left = Math.max(0, index - k);
        int right = Math.min(index + 1 + k, arr.length - 1);
        while (right - left > k - 1) {
            if (arr[right] - x >= x - arr[left]) {
                right--;
            } else {
                left++;
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            list.add(arr[i]);
        }

        return list;
    }

    private static int binarySearch(int[] arr, int x) {
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (arr[mid] == x) {
                return mid;
            } else if (arr[mid] > x) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
