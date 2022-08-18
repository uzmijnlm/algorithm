package org.example.string.slidingwindow;

/**
 * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
 * 换句话说，s1 的排列之一是 s2 的 子串 。
 */
public class CheckInclusion {


    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            cnt1[s1.charAt(i) - 'a']++;
        }

        int left = 0;
        int right = 0;
        while (right < s2.length()) {
            int index = s2.charAt(right) - 'a';
            cnt2[index]++;
            while (cnt2[index] > cnt1[index]) {
                cnt2[s2.charAt(left++) - 'a']--;
            }
            if (right - left + 1 == s1.length()) {
                return true;
            }
            right++;
        }
        return false;
    }
}
