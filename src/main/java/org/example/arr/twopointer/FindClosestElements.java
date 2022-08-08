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

        int index = binarySearch(arr, x, 0, arr.length - 1);
        int left = Math.max(0, index - k);
        int right = Math.min(index + k, arr.length - 1);
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

    private static int binarySearch(int[] arr, int x, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] > x) {
                return binarySearch(arr, x, low, mid - 1);
            } else if (arr[mid] < x) {
                return binarySearch(arr, x, mid + 1, high);
            } else {
                return mid;
            }
        } else {
            return low;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 4, 5, 6, 8};
        int i = FindClosestElements.binarySearch(arr, 7, 0, arr.length - 1);
        System.out.println();
    }
}
