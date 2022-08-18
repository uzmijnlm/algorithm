package org.example.string.slidingwindow;

/**
 * 给你一个字符串 s 和一个整数 k 。你可以选择字符串中的任一字符，并将其更改为任何其他大写英文字符。该操作最多可执行 k 次。
 * 在执行上述操作后，返回包含相同字母的最长子字符串的长度。
 */
public class CharReplace {

    public static int characterReplacement(String s, int k) {
        int left = 0;
        int right = 0;
        char[] map = new char[26];
        int historyMax = 0;
        while (right < s.length()) {
            int index = s.charAt(right++) - 'A';
            map[index]++;
            historyMax = Math.max(historyMax, map[index]);
            if (right - left > historyMax + k) {
                map[s.charAt(left++) - 'A']--;
            }
        }
        return right - left;
    }
}
