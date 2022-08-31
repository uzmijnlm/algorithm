package org.example.arr.binarysearch;

/**
 * 给定一个无序数组，找出一个局部最小值的位置
 */
public class LocalMin {

    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }

        int left = 1;
        int right = arr.length - 2;
        int mid;
        while (left < right) {
            mid = left + (right - left) / 2;
            if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return left;
    }
}
