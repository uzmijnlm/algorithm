package org.example.dp;

/**
 * 字符串匹配问题
 * 记住字符为*时的情况
 */
public class Match {

    // 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
    //
    //'.' 匹配任意单个字符
    //'*' 匹配零个或多个前面的那一个元素
    //所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
    public static boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i < n + 1; i++) {
            if ((i & 1) == 0) {
                if (p.charAt(i - 1) == '*') {
                    dp[0][i] = true;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (p.charAt(j - 1) == '*') {
                    if (match(s, p, i, j - 1)) {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 2];
                    } else {
                        dp[i][j] = dp[i][j - 2];
                    }
                } else {
                    if (match(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[m][n];
    }

    private static boolean match(String s, String p, int i, int j) {
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return p.charAt(j - 1) == s.charAt(i - 1);
    }


    // 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
    //
    //'?' 可以匹配任何单个字符。
    //'*' 可以匹配任意字符串（包括空字符串）。
    //两个字符串完全匹配才算匹配成功。
    //
    //说明:
    //
    //s 可能为空，且只包含从 a-z 的小写字母。
    //p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
    public static boolean isMatch2(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i < n + 1; i++) {
            if (p.charAt(i - 1) != '*') {
                break;
            } else {
                dp[0][i] = true;
            }
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (p.charAt(j - 1) != '*') {
                    if (match2(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }
        return dp[m][n];
    }

    private static boolean match2(String s, String p, int i, int j) {
        if (p.charAt(j - 1) == '?') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}
