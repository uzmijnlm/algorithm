package org.example.arr;

/**
 * 小和定义如下：对每一个元素求它左边小于等于它的数的和，然后将这些和相加，就是数组的小和
 * 给定一个数组，返回它的小和
 * 要求：时间复杂度O(NlogN)，额外空间复杂度O(N)
 */
public class SmallSum {


    public int getSmallSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return getSmallSumByMergeSort(arr, 0, arr.length - 1);
    }

    private int getSmallSumByMergeSort(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int mid = left + (right - left) / 2;
        return getSmallSumByMergeSort(arr, left, mid - 1)
                + getSmallSumByMergeSort(arr, mid + 1, right)
                + merge(arr, left, mid, right);
    }

    private int merge(int[] arr, int left, int mid, int right) {
        int[] h = new int[right - left + 1];
        int hi = 0;
        int i = left;
        int j = mid + 1;
        int smallSum = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                smallSum += arr[i] * (right - j + 1);
                h[hi++] = arr[i++];
            } else {
                h[hi++] = arr[j++];
            }
        }
        for (; (j < right + 1) || (i < mid + 1); j++, i++) {
            h[hi++] = i > mid ? arr[j] : arr[i];
        }
        for (int k = 0; k != h.length; k++) {
            arr[left++] = h[k];
        }
        return smallSum;

    }


}
