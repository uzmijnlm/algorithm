package org.example.string.kmp;

/**
 * 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
 */
public class ShortestPalindrome {


    // 思路：
    // 找s的最长回文前缀，这样只需要把剩下的后缀翻转过来添加在前面即可
    // 构造s+"#"+_s字符串，对它求KMP算法中的next数组，数组最后一个元素的值+1就是最长回文前缀的长度
    public static String shortestPalindrome(String s) {
        int n = s.length();
        if (n == 0) {
            return "";
        }
        // 字符串拼接为 s + "#" + _s
        String str = s + "#" + new StringBuilder(s).reverse();
        int[] next = getNextArray(str.toCharArray());
        int max = next[str.length() - 1];
        String last = (max == n - 1 ? "" : s.substring(max + 1));
        return new StringBuilder(last).reverse() + s;
    }

    private static int[] getNextArray(char[] match) {
        if (match.length < 2) {
            return new int[]{-1};
        }

        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (match[i - 1] == match[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                i++;
            }
        }
        return next;
    }
}
