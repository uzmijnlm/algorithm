package org.example.dp;

/**
 * 给定两个字符串str1和str2，再给定三个整数ic、dc和rc，分别代表插入、删除和替换一个字符的代价，返回将str1编辑成str2的最小代价
 */
public class EditDistance {

    // 动态规划，时间复杂度O(M*N)，额外空间复杂度O(M*N)
    // dp[i][j]的值代表str1[0...i-1]编辑成str2[0...j-1]的最小代价
    // 因为要考虑空字符串，所以dp大小为(M+1)*(N+1)
    public int editDistance(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }

        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int row = chs1.length + 1;
        int col = chs2.length + 1;

        // 初始化dp[0][0]、dp[i][0]和dp[0][i]。比较好理解
        int[][] dp = new int[row][col];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dc * i;
        }
        for (int i = 1; i < col; i++) {
            dp[0][i] = ic * i;
        }

        // 一般位置的值有4种情况：
        // a.str1[0...i-1]先删除字符str1[i-1]变成str1[0...i-2]，再编辑成str2[0...j-1]。此时代价为dp[i-1][j]+dc
        // b.str1[0...i-1]先编辑成str2[0...j-2]，再添加一个字符变成str2[0...j-1]。此时代价为dp[i][j-1]+ic
        // c.如果str1[i-1]!=str2[j-1]，那么可以先将str1[0...i-2]编辑成str2[0...j-2]，再将str1[i-1]替换成str2[j-1]。此时代价为dp[i-1][j-1]+rc
        // d.如果str1[i-1]==str2[j-1]，那么将str1[0...i-2]编辑成str2[0...j-2]即可。此时代价为dp[i-1][j-1]
        // 取以上几种情况的最小值即可
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (chs1[i - 1] == chs2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + rc;
                }
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ic);
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);
            }
        }
        return dp[row - 1][col - 1];
    }


    // 空间压缩优化，额外空间复杂度O(min(M,N))
    // dp[i]表示将较长字符串编辑成较短字符串的代价
    public int editDistance2(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }

        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;

        if (chs1.length < chs2.length) { // 如果str2较长就交换ic和dc的值
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }

        int[] dp = new int[shorts.length + 1];
        // 第一次给dp赋值时表示将空串编辑成较短字符串的代价
        // 对比原方法，从这里也可以看出为什么要将dc和ic交换
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = ic * i;
        }

        for (int i = 1; i <= longs.length; i++) {
            int pre = dp[0]; // 相当于左上角的值dp[i-1][j-1]
            dp[0] = dc * i;
            for (int j = 1; j <= shorts.length; j++) {
                int tmp = dp[j]; // 相当于dp[i-1][j]
                if (longs[i - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + rc;
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                dp[j] = Math.min(dp[j], tmp + dc);
                pre = tmp; // pre更新成新的左上角的值
            }
        }
        return dp[shorts.length];
    }
}

