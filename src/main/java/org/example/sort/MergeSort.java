package org.example.sort;

public class MergeSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(arr, low, mid);
            mergeSort(arr, mid + 1, high);
            merge(arr, low, mid, high);
        }
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int left = low;
        int right = mid + 1;
        int[] tmp = new int[high - low + 1];
        int index = 0;
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
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
    }


}
