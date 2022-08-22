package org.example.dp;

/**
 * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
 * 'A' -> "1"
 * 'B' -> "2"
 * ...
 * 'Z' -> "26"
 * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
 * "AAJF" ，将消息分组为 (1 1 10 6)
 * "KJF" ，将消息分组为 (11 10 6)
 * 注意，消息不能分组为 (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
 * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
 * 题目数据保证答案肯定是一个 32 位 的整数。
 */
public class NumDecodings {

    // 核心思路是用a表示i位置的值，用b表示i-1位置和i位置共同组成的值
    public static int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return s.charAt(0) == '0' ? 0 : 1;
        }
        if (s.charAt(0) == '0') {
            return 0;
        }

        int[] dp = new int[s.length()];
        dp[0] = 1;
        int a = s.charAt(1) - '0';
        int b = (s.charAt(0) - '0') * 10 + a;
        if (a >= 1 && a <= 9) {
            dp[1] = dp[0];
        }
        if (b >= 10 && b <= 26) {
            dp[1] += 1;
        }

        for (int i = 2; i < s.length(); i++) {
            a = s.charAt(i) - '0';
            b = (s.charAt(i - 1) - '0') * 10 + a;
            if (a >= 1 && a <= 9) {
                dp[i] = dp[i - 1]; // dp[i]从dp[i-1]来
            }
            if (b >= 10 && b <= 26) {
                dp[i] += dp[i - 2]; // dp[i]也可以从dp[i-2]来
            }
        }
        return dp[s.length() - 1];
    }
}
