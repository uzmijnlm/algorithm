package org.example.other;

/**
 * 给定一个长度为N且没有重复元素的数组arr和一个整数M，实现函数等概率随机打印arr中的M个数
 * 要求：相同的数不要重复打印，时间复杂度O(M)，额外空间复杂度O(1)，可以改变arr数组
 */
public class RandM {

    // 步骤如下：
    // 1.在[0,N-1]中随机得到一个位置a，打印arr[a]
    // 2.把arr[a]和arr[N-1]交换
    // 3.在[0,N-2]中随机得到一个位置b，打印arr[b]
    // 4.以此类推
    public void printRandM(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 0) {
            return;
        }

        m = Math.min(arr.length, m);
        int count = 0;
        while (count < m) {
            int i = (int) (Math.random() * (arr.length - count));
            System.out.println(arr[i]);
            swap(arr, arr.length - count++ - 1, i);
        }
    }

    private void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }
}
