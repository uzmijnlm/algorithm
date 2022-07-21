package org.example.string;

/**
 * 给定一个字符串，返回把它全部切成回文子串的最小分割数
 */
public class MinCut {


    // 动态规划问题。dp[i]表示子串str[i...len-1]至少需要切割几次，才能把str[i...len-1]全部切成回文子串。dp[0]是最终结果
    public int minCut(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] chars = str.toCharArray();
        int len = chars.length;
        int[] dp = new int[len + 1];
        dp[len] = -1;
        boolean[][] p = new boolean[len][len];

        // 从右往左依次计算dp[i]，步骤如下：
        // 1.假设j位置处在i与len-1之间(i<=j<len)，如果str[i...j]是回文串，那么dp[i]的值可能是dp[j+1]+1
        //   其含义是在str[i...len-1]上既然str[i...j]是一个回文串，那么它可以自己作为一个分割的部分
        //   剩下的部分（即str[j+1...len-1]）继续做最经济的切割，而dp[j+1]值的含义正好是str[j+1...len-1]的最少回文分割数
        // 2.让j在i到len-1位置上枚举，那么所有可能情况中的最小值就是dp[i]的值
        // 3.关键在于如何快速判断str[i...j]是否是回文串。可构造一个boolean类型的二维数组，p[i][j]=true表示str[i...j]是回文串
        //   在计算dp数组的过程中，位置i是从右向左计算的。对于每一个i来说，又依次从i位置向右枚举所有j（i<=j<len)
        //   因此对p[i][j]来说，p[i+1][j-1]一定被计算过，这使得它的计算变得方便
        for (int i = len - 1; i >= 0; i--) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = i; j < len; j++) {
                if (chars[i] == chars[j] && (j - i < 2 || p[i + 1][j - 1])) {
                    p[i][j] = true;
                    dp[i] = Math.min(dp[i], dp[j + 1] + 1);
                }
            }
        }
        return dp[0];
    }
}
