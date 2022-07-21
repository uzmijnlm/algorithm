package org.example.arr;

/**
 * 给定一个长度为N的整型数组，其中有N个互不相等的自然数1~N
 * 请实现该数组的排序，但是不要把下标0~N-1位置上的数通过直接赋值的方式替换成1~N
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class JumpSort {

    public void sort(int[] arr) {
        int tmp = 0;
        for (int i = 0; i < arr.length; i++) {
            while (arr[i] != i + 1) {
                tmp = arr[arr[i] - 1];
                arr[arr[i] - 1] = arr[i];
                arr[i] = tmp;
            }
        }
    }
}
