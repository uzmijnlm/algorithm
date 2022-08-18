package org.example.string.slidingwindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
 */
public class FindAnagrams {


    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        if (p.length() > s.length()) {
            return ans;
        }
        int[] sCnt = new int[26];
        int[] pCnt = new int[26];
        for (int i = 0; i < p.length(); i++) {
            pCnt[p.charAt(i) - 'a']++;
        }
        int left = 0;
        int right = 0;
        while (right < s.length()) {
            int index = s.charAt(right) - 'a';
            sCnt[index]++;
            while (sCnt[index] > pCnt[index]) {
                sCnt[s.charAt(left++) - 'a']--;
            }
            if (right - left + 1 == p.length()) {
                ans.add(left);
            }
            right++;
        }
        return ans;
    }
}
