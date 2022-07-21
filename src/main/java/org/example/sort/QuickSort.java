package org.example.sort;


import java.util.Random;

import static org.example.sort.CommonUtil.swap;

public class QuickSort {

    static class Result {
        public int left;
        public int right;

        public Result(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }


    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            Random random = new Random(System.nanoTime());
            int index = random.nextInt(high - low) + low;
            swap(arr, index, high);
            Result result = partition(arr, low, high);
            quickSort(arr, low, result.left);
            quickSort(arr, result.right, high);
        }
    }

    private static Result partition(int[] arr, int low, int high) {
        int left = low - 1;
        int right = high + 1;
        int index = low;
        int num = arr[high];
        while (left < right && index < right) {
            if (arr[index] < num) {
                swap(arr, index, left + 1);
                index++;
                left++;
            } else if (arr[index] > num) {
                swap(arr, index, right - 1);
                right--;
            } else {
                index++;
            }
        }
        return new Result(left, right);
    }


}
