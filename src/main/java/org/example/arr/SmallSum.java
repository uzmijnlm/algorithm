package org.example.arr;

/**
 * 小和定义如下：对每一个元素求它左边小于等于它的数的和，然后将这些和相加，就是数组的小和
 * 给定一个数组，返回它的小和
 * 要求：时间复杂度O(NlogN)，额外空间复杂度O(N)
 */
public class SmallSum {


    public static int getSmallSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return getSmallSumByMergeSort(arr, 0, arr.length - 1);
    }

    private static int getSmallSumByMergeSort(int[] arr, int low, int high) {
        if (low == high) {
            return 0;
        }
        int mid = low + (high - low) / 2;
        return getSmallSumByMergeSort(arr, low, mid)
                + getSmallSumByMergeSort(arr, mid + 1, high)
                + merge(arr, low, mid, high);
    }

    private static int merge(int[] arr, int low, int mid, int high) {
        int[] tmp = new int[high - low + 1];
        int index = 0;
        int left = low;
        int right = mid + 1;
        int smallSum = 0;
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                smallSum += arr[left] * (high - right + 1);
                tmp[index++] = arr[left++];
            } else {
                tmp[index++] = arr[right++];
            }
        }
        while (left <= mid) {
            tmp[index++] = arr[left++];
        }
        while (right <= high) {
            tmp[index++] = arr[right++];
        }

        int k = low;
        for (int i = 0; i < tmp.length; i++) {
            arr[k++] = tmp[i];
        }
        return smallSum;

    }


}
