package org.example.sort;

import static org.example.sort.CommonUtil.swap;

public class SelectSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int smallIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[smallIndex]) {
                    smallIndex = j;
                }
            }
            swap(arr, smallIndex, i);
        }
    }
}
