package org.example.string.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * 注意：
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 */
public class MinWindowContainsAllChar {

    public static String minWindow(String s, String t) {
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            need.put(t.charAt(i), need.getOrDefault(t.charAt(i), 0) + 1);
        }

        Map<Character, Integer> window = new HashMap<>();
        int len = Integer.MAX_VALUE;
        int start = 0;
        int end = 0;
        int left = 0;
        int right = 0;
        int valid = 0;
        while (right < s.length()) {
            char r = s.charAt(right);

            if (need.containsKey(r)) {
                window.put(r, window.getOrDefault(r, 0) + 1);
                if (window.get(r).equals(need.get(r))) {
                    valid++;
                }
            }

            while (valid == need.size()) {
                int curLen = right - left + 1;
                if (curLen < len) {
                    len = curLen;
                    start = left;
                    end = right;
                }
                char l = s.charAt(left++);
                if (need.containsKey(l)) {
                    if (window.get(l).equals(need.get(l))) {
                        valid--;
                    }
                    window.put(l, window.get(l) - 1);
                    if (window.get(l) == 0) {
                        window.remove(l);
                    }
                }
            }

            right++;
        }

        return len != Integer.MAX_VALUE ? s.substring(start, end + 1) : "";
    }
}
