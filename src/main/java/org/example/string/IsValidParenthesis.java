package org.example.string;

/**
 * 给定一个字符串，判断是不是整体有效的括号字符串
 */
public class IsValidParenthesis {

    public boolean isValid(String str) {
        if (str == null || str.equals("")) {
            return false;
        }

        char[] chars = str.toCharArray();
        int status = 0;
        for (char c : chars) {
            if (c != ')' && c != '(') {
                return false;
            }
            if (c == ')' && --status < 0) {
                return false;
            }
            if (c == '(') {
                status++;
            }
        }
        return status == 0;
    }


    // 补充问题：给定一个括号字符串，返回最长的有效括号子串
    // 要求：时间复杂度O(N)，额外空间复杂度O(N)
    // 思路：用动态规划。dp[i]表示str[0...i]中必须以字符str[i]结尾的最长有效括号子串长度
    public int maxLength(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] chars = str.toCharArray();
        int[] dp = new int[chars.length];

        int res = 0; // 最终的结果

        // 情况1：dp[0]肯定为0
        // 情况2：如果str[i]为'('，那么dp[i]一定为0
        // 情况3：dp[i-1]表示必须以str[i-1]结尾的最长有效括号子串长度，所以如果i-1-dp[i-1]位置上的字符是'('，就能与当前位置匹配成对
        //       另外还要加上dp[i-dp[i-1]-2]的值，把前面的续上
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == ')') {
                int pre = i - dp[i - 1] - 1;
                if (pre >= 0 && chars[pre] == '(') {
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }
}
