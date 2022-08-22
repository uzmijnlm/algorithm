package org.example.dp;

/**
 * 如果一个二进制字符串，是以一些 0（可能没有 0）后面跟着一些 1（也可能没有 1）的形式组成的，那么该字符串是 单调递增 的。
 * 给你一个二进制字符串 s，你可以将任何 0 翻转为 1 或者将 1 翻转为 0 。
 * 返回使 s 单调递增的最小翻转次数。
 */
public class MinFlipsMonoIncr {


    public static int minFlipsMonoIncr(String s) {
        int a = s.charAt(0) == '0' ? 0 : 1;
        int b = s.charAt(0) == '1' ? 0 : 1;
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '0') {
                b = Math.min(a, b) + 1;
            } else {
                b = Math.min(a, b);
                a = a + 1;
            }
        }
        return Math.min(a, b);
    }
}
