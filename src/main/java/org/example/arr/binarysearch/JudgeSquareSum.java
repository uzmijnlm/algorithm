package org.example.arr.binarysearch;

/**
 * 给定一个非负整数 c ，你要判断是否存在两个整数 a 和 b，使得 a^2 + b^2 = c 。
 */
public class JudgeSquareSum {


    public static boolean judgeSquareSum(int c) {
        int left = 0;
        int right = (int) Math.sqrt(c);
        while (left < right) {
            int sum = left * left + right * right;
            if (sum == c) {
                return true;
            }
            if (sum < c) {
                left++;
            } else {
                right--;
            }
        }
        return left * left + right * right == c;
    }
}
