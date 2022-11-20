package org.example.string.twopointer;

import java.util.List;

/**
 * 给你一个字符串 s 和一个字符串数组 dictionary ，找出并返回 dictionary 中最长的字符串，该字符串可以通过删除 s 中的某些字符得到。
 * 如果答案不止一个，返回长度最长且字母序最小的字符串。如果答案不存在，则返回空字符串。
 */
public class FindLongest {

    public static String findLongestWord(String s, List<String> dictionary) {
        String res = "";
        for (String str : dictionary) {
            if (valid(s, str)) {
                if (str.length() > res.length() || (str.length() == res.length() && str.compareTo(res) < 0)) {
                    res = str;
                }
            }
        }
        return res;
    }

    private static boolean valid(String s, String str) {
        int index1 = 0;
        int index2 = 0;
        while (index1 < s.length() && index2 < str.length()) {
            if (s.charAt(index1) == str.charAt(index2)) {
                index2++;
            }
            index1++;
        }
        return index2 == str.length();
    }
}
