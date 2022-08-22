package org.example.dp;

/**
 * 给定两个字符串，返回最长公共子序列
 */
public class LCSeq {


    // 仅求最长公共子序列的长度
    public static int lcSeqLength(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 1; i < str1.length() + 1; i++) {
            char c1 = str1.charAt(i - 1);
            for (int j = 1; j < str2.length() + 1; j++) {
                char c2 = str2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }


    // 动态规划。dp[i][j]表示str1[0...i]与str2[0...j]的最长公共子序列的长度
    // 先初始化第一行第一列。对于普通的dp[i][j]，有四种情况：
    // a.以i结尾，不以j结尾
    // b.不以i结尾，以j记为
    // c.str1[i]==str2[j]，此时以i结尾，以j结尾
    // d.不以i记为，不以j结尾。这种情况一定比前面三种小，可以省略
    public String lcSeq(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getDP(chs1, chs2);
        int m = chs1.length - 1;
        int n = chs2.length - 1;
        char[] res = new char[dp[m][n]];
        int index = res.length - 1;
        while (index >= 0) {
            if (n > 0 && dp[m][n] == dp[m][n - 1]) {
                n--;
            } else if (m > 0 && dp[m][n] == dp[m - 1][n]) {
                m--;
            } else {
                res[index--] = chs1[m];
                m--;
                n--;
            }
        }
        return String.valueOf(res);
    }

    private int[][] getDP(char[] chs1, char[] chs2) {
        int[][] dp = new int[chs1.length][chs2.length];
        dp[0][0] = chs1[0] == chs2[0] ? 1 : 0;
        for (int i = 1; i < chs1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], chs1[i] == chs2[0] ? 1 : 0);
        }
        for (int i = 1; i < chs2.length; i++) {
            dp[0][i] = Math.max(dp[0][i - 1], chs1[0] == chs2[i] ? 1 : 0);
        }

        for (int i = 1; i < chs1.length; i++) {
            for (int j = 1; j < chs2.length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (chs1[i] == chs2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp;
    }
}
