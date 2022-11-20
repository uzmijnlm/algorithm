package org.example.string.manacher;

/**
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 */
public class LongestPalindrome {


    public static String longestPalindrome(String s) {
        char[] manacher = manacher(s);
        int c = 0;
        int r = 0;
        int[] pArr = new int[manacher.length];
        int longestCenter = 0;
        int max = 0;
        for (int i = 0; i < manacher.length; i++) {
            pArr[i] = r > i ? Math.min(pArr[2 * c - i], r - i) : 1;

            while (i + pArr[i] < manacher.length && i - pArr[i] >= 0) {
                if (manacher[i + pArr[i]] == manacher[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }


            if (i + pArr[i] > r) {
                r = i + pArr[i];
                c = i;
            }
            if (pArr[i] > max) {
                max = pArr[i];
                longestCenter = i;
            }
        }


        char[] res = new char[max - 1];
        int startIndex = longestCenter - max + 1;
        int endIndex = longestCenter + max - 1;
        int index = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            if ((i & 1) == 1) {
                res[index++] = manacher[i];
            }
        }
        return String.valueOf(res);
    }


    private static char[] manacher(String s) {
        char[] chars = s.toCharArray();
        char[] manacher = new char[chars.length * 2 + 1];
        int index = 0;
        while (index < manacher.length) {
            if ((index & 1) == 0) {
                manacher[index] = '#';
            } else {
                manacher[index] = chars[index / 2];
            }
            index++;
        }
        return manacher;
    }
}
