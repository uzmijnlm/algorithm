package org.example.string;

/**
 * 给定一个字符串str，对其任意位置添加字符，请返回在添加字符最少的情况下，让str整体都是回文字符串的一种结果
 */
public class Palindrome {

    // dp[i][j]表示子串str[i...j]最少添加几个字符可以使str[i...j]整体都是回文串
    private int[][] getDP(char[] str) {
        // 情况1：只有一个字符dp[i][i]=0。初始化时默认就是0
        int[][] dp = new int[str.length][str.length];
        for (int j = 1; j < str.length; j++) {
            // 情况2：只有两个字符。如果相等则为0，否则为1
            dp[j - 1][j] = str[j - 1] == str[j] ? 0 : 1;
            // 情况3：多于两个字符。因为j-1的情况已经讨论过了，所以从j-2开始往前看
            for (int i = j - 2; i > -1; i--) {
                if (str[i] == str[j]) { // 情况3.1：头尾相等
                    dp[i][j] = dp[i + 1][j - 1];
                } else { // 情况3.2：头尾不等
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp;
    }

    public String getPalindrome1(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }

        char[] chars = str.toCharArray();
        int[][] dp = getDP(chars);
        char[] res = new char[chars.length + dp[0][chars.length - 1]];

        int i = 0;
        int j = chars.length - 1;
        int left = 0;
        int right = chars.length - 1;

        // 从左右两头依次填充
        while (i <= j) {
            if (chars[i] == chars[j]) { // 情况1：头尾相等，则直接填入
                res[left++] = chars[i++];
                res[right--] = chars[j--];
            } else if (dp[i][j - 1] < dp[i + 1][j]) { // 情况2：头尾不等，则看哪种变换代价小
                res[left++] = chars[j];
                res[right--] = chars[j--];
            } else {
                res[left++] = chars[i];
                res[right--] = chars[i++];
            }
        }
        return String.valueOf(res);
    }


    // 进阶问题：给定一个字符串str，再给定其最长回文子序列字符串strlps
    // 请返回在添加字符最少的情况下，让str整体都是回文字符串的一种结果
    // 要求：时间复杂度O(N)
    public String getPalindrome2(String str, String strlps) {
        if (str == null || str.equals("")) {
            return "";
        }

        char[] chars = str.toCharArray();
        char[] lps = strlps.toCharArray();
        char[] res = new char[2 * chars.length - lps.length]; // 因为strlps本身就是回文，所以减去它的长度

        int leftOfChars = 0;
        int rightOfChars = chars.length - 1;
        int leftOfLps = 0;
        int rightOfLps = lps.length - 1;
        int leftOfRes = 0;
        int rightOfRes = res.length - 1;

        int leftOfTmp = 0;
        int rightOfTmp = 0;

        // 整个过程类似剥洋葱
        // 例如str="A1BC22DE1F", strlps="1221"
        // 第0层：strlps是1..1，在str中分别找左右侧1外面的部分，将左边和右边逆序复制到res左侧，右边和左边逆序复制到res右侧，再填充1
        // 第1层：strlps的22，依次类推
        while (leftOfLps <= rightOfLps) {
            leftOfTmp = leftOfChars;
            rightOfTmp = rightOfChars;

            // 找到洋葱外的部分
            while (chars[leftOfChars] != lps[leftOfLps]) {
                leftOfChars++;
            }
            while (chars[rightOfChars] != lps[rightOfLps]) {
                rightOfChars--;
            }

            // 设置洋葱外的部分
            set(res, leftOfRes, rightOfRes, chars, leftOfTmp, leftOfChars, rightOfChars, rightOfTmp);
            // 设置本层strlps的值
            leftOfRes += leftOfChars - leftOfTmp + rightOfTmp - rightOfChars;
            rightOfRes -= leftOfChars - leftOfTmp + rightOfTmp - rightOfChars;
            res[leftOfRes++] = chars[leftOfChars++];
            res[rightOfRes--] = chars[rightOfChars--];

            // 剥完一层洋葱
            leftOfLps++;
            rightOfLps--;
        }
        return String.valueOf(res);
    }

    private void set(char[] res,
                     int leftOfRes,
                     int rightOfRes,
                     char[] chars,
                     int leftOfTmp,
                     int leftOfChars,
                     int rightOfChars,
                     int rightOfTmp) {
        for (int i = leftOfTmp; i < leftOfChars; i++) {
            res[leftOfRes++] = chars[i];
            res[rightOfRes--] = chars[i];
        }
        for (int i = rightOfTmp; i > rightOfChars; i--) {
            res[leftOfRes++] = chars[i];
            res[rightOfRes--] = chars[i];
        }


    }

}
