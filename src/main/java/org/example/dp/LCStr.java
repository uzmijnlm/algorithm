package org.example.dp;

/**
 * 给定两个字符串，返回最长公共子串
 */
public class LCStr {

    // 动态规划。dp[i][j]表示在必须把str1[i]和str2[j]当作公共子串最后一个字符的情况下，公共子串最长能有多长
    // 对于一个普通位置的dp[i][j]，有两种情况：
    // a.如果str1[i]!=str2[j]，说明在必须把str1[i]和str2[j]当作公共子串最后一个字符是不可能的，令dp[i][j]=0
    // b.如果str1[i]==str2[j]，说明它们可以作为公共子串最后一个字符，dp[i][j]=dp[i-1][j-1]+1
    public String lcStr(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }

        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int[][] dp = getDP(chs1, chs2);
        int end = 0;
        int max = 0;
        for (int i = 0; i < chs1.length; i++) {
            for (int j = 0; j < chs2.length; j++) {
                if (dp[i][j] > max) {
                    end = i;
                    max = dp[i][j];
                }
            }
        }
        return str1.substring(end - max + 1, end + 1);
    }

    private int[][] getDP(char[] chs1, char[] chs2) {
        int[][] dp = new int[chs1.length][chs2.length];
        for (int i = 0; i < chs1.length; i++) {
            if (chs1[i] == chs2[0]) {
                dp[i][0] = 1;
            }
        }
        for (int i = 1; i < chs2.length; i++) {
            if (chs1[0] == chs2[i]) {
                dp[0][i] = 1;
            }
        }
        for (int i = 1; i < chs1.length; i++) {
            for (int j = 1; j < chs2.length; j++) {
                if (chs1[i] == chs2[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        return dp;
    }


    // 空间压缩。由于每个dp[i][j]只依赖左上角的值，因此可以一条斜线一条斜线地求最大值
    public String lcStr2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.equals("") || str2.equals("")) {
            return "";
        }

        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int row = 0; // 斜线开始的行
        int col = chs2.length - 1; // 斜线开始的列
        int max = 0; // 最大长度
        int end = 0; // 最大程度更新时，记录子串的结尾位置
        while (row < chs1.length) {
            int i = row;
            int j = col;
            int len = 0;
            while (i < chs1.length && j < chs2.length) {
                if (chs1[i] != chs2[j]) {
                    len = 0;
                } else {
                    len++;
                }
                // 记录max和end
                if (len > max) {
                    end = i;
                    max = len;
                }
                i++;
                j++;
            }
            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return str1.substring(end - max + 1, end + 1);
    }
}
