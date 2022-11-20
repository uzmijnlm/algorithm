package org.example.queueandstack;

import java.util.Stack;

/**
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 */
public class LargestRectArea {

    public static int largestRectangleArea(int[] heights) {
        int area = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
                int curHeight = heights[stack.pop()];
                int width;
                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }
                area = Math.max(area, curHeight * width);
            }
            stack.add(i);
        }

        while (!stack.isEmpty()) {
            int curHeight = heights[stack.pop()];
            int width;
            if (stack.isEmpty()) {
                width = heights.length;
            } else {
                width = heights.length - stack.peek() - 1;
            }
            area = Math.max(area, curHeight * width);
        }

        return area;
    }
}
