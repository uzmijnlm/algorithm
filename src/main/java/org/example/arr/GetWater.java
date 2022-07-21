package org.example.arr;

/**
 * 给定一个数组arr，已知其中所有值都非负，将这个数组看作一个容器，请返回容器能装多少水
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class GetWater {

    public int getWater(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int res = 0;
        int leftMax = arr[0];
        int rightMax = arr[arr.length - 1];
        int L = 1;
        int R = arr.length - 2;
        while (L <= R) {
            if (leftMax <= rightMax) {
                res += Math.max(0, leftMax - arr[L]);
                leftMax = Math.max(leftMax, arr[L++]);
            } else {
                res += Math.max(0, rightMax - arr[R]);
                rightMax = Math.max(rightMax, arr[R++]);
            }
        }
        return res;
    }


}
