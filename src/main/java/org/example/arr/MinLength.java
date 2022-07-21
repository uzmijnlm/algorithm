package org.example.arr;

/**
 * 给定一个无序数组，求出需要排序的最短子数组长度
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class MinLength {

    // 步骤如下：
    // 1.从右向左遍历，遍历过程中记录右侧出现过的数的最小值，记为min
    //   假设当前数为arr[i]，如果arr[i]>min，说明如果要整体有序，min值必然会挪到arr[i]的左边
    //   用noMinIndex记录最左边出现这种情况的位置
    // 2.如果遍历完成后noMinIndex=-1，说明不需要排序，返回0
    // 3.从左向右遍历，记录最大值max。如果arr[i]<max，max值必须会挪到arr[i]的右边
    //   用noMaxIndex记录最右边出现这种情况的位置
    // 4.返回arr[noMaxIndex...noMinIndex]这部分的长度
    public int getMinLength(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int min = arr[arr.length - 1];
        int noMinIndex = -1;
        for (int i = arr.length - 2; i != -1; i--) {
            if (arr[i] > min) {
                noMinIndex = i;
            } else {
                min = Math.min(min, arr[i]);
            }
        }
        if (noMinIndex == -1) {
            return 0;
        }

        int max = arr[0];
        int noMaxIndex = -1;
        for (int i = 1; i != arr.length; i++) {
            if (arr[i] < max) {
                noMaxIndex = i;
            } else {
                max = Math.max(max, arr[i]);
            }
        }
        return noMaxIndex - noMinIndex + 1;

    }

}
