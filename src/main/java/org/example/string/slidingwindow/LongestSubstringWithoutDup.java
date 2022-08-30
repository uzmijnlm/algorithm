package org.example.string.slidingwindow;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class LongestSubstringWithoutDup {


    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }

        char[] chars = s.toCharArray();
        Set<Character> set = new HashSet<>();
        int ans = 0;
        int left = 0;
        int right = 0;
        while (right < chars.length) {
            char c = chars[right];
            while (set.contains(c)) {
                set.remove(chars[left++]);
            }
            set.add(c);
            ans = Math.max(ans, right - left + 1);
            right++;
        }

        return ans;
    }
}
