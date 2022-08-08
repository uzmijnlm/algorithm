package org.example.arr.twopointer;

/**
 * 给定排序数组arr和整数k，不重复打印arr中所有相加和为k的不降序二元组
 */
public class UniquePair {


    // 步骤如下：
    // 1.设置变量left=0，right=arr.length-1
    // 2.比较arr[left]+arr[right]的值与k的大小
    //   a.如果等于k，打印二元组，left++，right--。打印时检查是否与上一个元素相等，即可做到不重复打印
    //   b.如果大于k，right--
    //   c.如果小于k，left++
    // 3.如果left<right，则一直重复步骤2
    public void printUniquePair(int[] arr, int k) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            if (arr[left] + arr[right] < k) {
                left++;
            } else if (arr[left] + arr[right] > k) {
                right--;
            } else {
                if (left == 0 || arr[left - 1] != arr[left]) {
                    System.out.println(arr[left] + "," + arr[right]);
                }
                left++;
                right--;
            }
        }
    }


    // 补充问题：打印三元组
    // 思路：选定三元组中第一个值不重复，然后剩下的问题与原问题相同
    public void printUniqueTriad(int[] arr, int k) {
        if (arr == null || arr.length < 3) {
            return;
        }

        for (int i = 0; i < arr.length - 2; i++) {
            if (i == 0 || arr[i] != arr[i - 1]) {
                printRest(arr, i, i + 1, arr.length - 1, k - arr[i]);
            }
        }
    }

    private void printRest(int[] arr, int firstIndex, int left, int right, int k) {
        while (left < right) {
            if (arr[left] + arr[right] < k) {
                left++;
            } else if (arr[left] + arr[right] > k) {
                right--;
            } else {
                if (left == firstIndex + 1 || arr[left - 1] != arr[left]) {
                    System.out.println(arr[left] + "," + arr[right]);
                }
                left++;
                right--;
            }
        }
    }

}
