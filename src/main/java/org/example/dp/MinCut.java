package org.example.dp;

/**
 * 给定一个字符串，返回把它全部切成回文子串的最小分割数
 */
public class MinCut {


    // 动态规划问题。dp[i]表示子串str[i...len-1]至少需要切割几次，才能把str[i...len-1]全部切成回文子串。dp[0]是最终结果
    public int minCut(String s) {
        // 预处理，先获得二维数组p，p[i][j]表示s[i...j]是否是回文串
        boolean[][] p = new boolean[s.length()][s.length()];
        for (int i=0; i<s.length(); i++) {
            for (int j=0; j<=i; j++) {
                if (i==j) {
                    p[j][i] = true;
                } else if ((i - j <= 2 || p[j+1][i-1]) && s.charAt(i) == s.charAt(j)) {
                    p[j][i] = true;
                }
            }
        }

        // dp[i]表示s[0...i]的最少分割次数
        int[] dp = new int[s.length()];
        for (int i=1; i<s.length(); i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j=0; j<=i; j++) {
                if (p[j][i]) {
                    if (j==0) {
                        dp[i] = 0;
                    } else {
                        dp[i] = Math.min(dp[i], dp[j-1] + 1); // 如果s[j...i]是回文串，则dp[i] = dp[j-1] + 1
                    }

                }

            }
        }
        return dp[dp.length - 1];
    }
}
