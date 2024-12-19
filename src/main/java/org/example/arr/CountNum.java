package org.example.arr;


/**
 * 无序数组长度为n，元素取值为[1, n]，取值可重复
 * 输出各个元素出现的次数
 * 要求时间复杂度O(n)，空间复杂度O(1)
 */
public class CountNum {

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            while (arr[i] > 0) {
                int index = arr[i] - 1;
                if (arr[index] > 0) {
                    arr[i] = arr[index];
                    arr[index] = -1;
                } else {
                    arr[i] = 0;
                    arr[index]--;
                }
            }
        }
    }
}
