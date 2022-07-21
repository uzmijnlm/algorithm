package org.example.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定字符串str1和str2，求str1的子串中含有str2所有字符的最小子串长度
 */
public class MinContainLength {


    public int minLength(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() < str2.length()) {
            return 0;
        }

        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();

        // key为字符，value表示对于key字符，str1字符串目前还欠str2字符串value个
        // 先初始化map
        Map<Character, Integer> map = new HashMap<>();
        for (char c : chars2) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        // 核心思路是双指针法
        // 遍历str1的过程中，str1[left...right]表示被框住的子串
        int left = 0;
        int right = 0;

        int match = chars2.length; // 表示对所有字符来说，str1[left...right]目前一共欠str2多少个
        int minLen = Integer.MAX_VALUE; // 最终的结果

        // 算法的核心部分
        // 先移动right，直到满足条件时，再移动left，刚好不满足条件时计算一次minLen
        while (right != chars1.length) {
            if (map.containsKey(chars1[right])) {
                map.put(chars1[right], map.get(chars1[right]) - 1);
            } else {
                map.put(chars1[right], -1);
            }
            // 这个字符之前还欠大于等于0个，所以要减match
            if (map.get(chars1[right]) >= 0) {
                match--;
            }

            if (match == 0) {
                while (map.get(chars1[left]) < 0) { // 某一个字符的value为0了，说明刚好不欠
                    map.put(chars1[left], map.get(chars1[left]) + 1);
                    left++;
                }
                minLen = Math.min(minLen, right - left + 1);

                // 继续移动left一位并更新map和match
                match++;
                map.put(chars1[left], map.get(chars1[left]) + 1);
                left++;
            }
            right++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
