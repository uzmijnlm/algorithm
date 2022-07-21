package org.example.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个字符串str，返回str的最长无重复字符子串的长度
 * 要求：时间复杂度O(N)
 */
public class MaxUnique {

    public int maxUnique(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] chars = str.toCharArray();
        // key表示某一个字符，value表示它最近一次出现的位置
        Map<Character, Integer> map = new HashMap<>();

        int res = 0; // 最终的结果，即最长无重复字符子串的长度
        int pre = -1; // 如果当前遍历到字符str[i]，pre表示在必须以str[i-1]字符结尾情况下，最长无重复字符子串开始位置的前一个位置

        // 算法核心：遍历到str[i]时，找到满足条件的左边界pre。取之前pre值和上一次出现str[i]时的位置的较大者作为pre的值
        for (int i = 0; i != chars.length; i++) {
            if (map.containsKey(chars[i])) {
                pre = Math.max(pre, map.get(chars[i]));
            }
            int cur = i - pre;
            res = Math.max(res, cur);
            map.put(chars[i], i);
        }
        return res;
    }


}
