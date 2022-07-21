package org.example.arr;

/**
 * 给定一个长度不小于2的数组，实现一个函数，要么让其所有偶数下标都是偶数，要么让其所有奇数下标都是奇数
 * 要求：时间复杂度为O(N)，额外空间复杂度O(1)
 */
public class OddAndEven {

    // 步骤如下：
    // 1.设置变量even，表示目前arr最左边的偶数下标，初始为0
    // 2.设置变量odd，表示目前arr最左边的奇数下标，初始为1
    // 3.不断检查arr的最后一个数arr[N-1]
    //   如果是偶数，交换arr[N-1]和arr[even]，然后令even=even+2
    //   如果是奇数，交换arr[N-1]和arr[odd]，然后令odd=odd+2
    // 4.如果even或者odd大于或等于N，过程停止
    public void modify(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int even = 0;
        int odd = 1;
        int end = arr.length - 1;
        while (even <= end && odd <= end) {
            if ((arr[end] & 1) == 0) {
                swap(arr, end, even);
                even += 2;
            } else {
                swap(arr, end, odd);
                odd += 2;
            }
        }
    }

    private void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }


}
