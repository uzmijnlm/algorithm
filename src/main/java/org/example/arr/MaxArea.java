package org.example.arr;

/**
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 */
public class MaxArea {

    public static int maxArea(int[] height) {
        if (height == null || height.length <= 1) {
            return  0;
        }

        int left = 0;
        int right = height.length - 1;
        int max = Integer.MIN_VALUE;
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            int w = right - left;
            int size = h * w;
            max = Math.max(max, size);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }
}
