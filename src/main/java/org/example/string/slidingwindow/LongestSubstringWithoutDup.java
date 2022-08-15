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
        int right = -1;
        while (left < chars.length && right < chars.length) {
            if (left > 0) {
                set.remove(chars[left - 1]);
            }

            while (right + 1 < chars.length && !set.contains(chars[right+1])) {
                set.add(chars[right+1]);
                right++;
            }

            ans = Math.max(ans, right - left + 1);
            left++;
        }

        return ans;
    }
}
