package org.example.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 两个字符串是否互为变形词，即字符种类一样且每种字符出现次数一样
 */
public class IsDeformation {
    public boolean isDeformation(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }

        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();

        Map<Character, Integer> map = new HashMap<>();
        for (char c : chars1) {
            if (map.containsKey(c)) {
                Integer count = map.get(c);
                map.put(c, count + 1);
            } else {
                map.put(c, 1);
            }
        }

        for (char c : chars2) {
            if (!map.containsKey(c)) {
                return false;
            }
            Integer count = map.get(c);
            if (count < 0) {
                return false;
            }
            map.put(c, count - 1);
        }
        return true;
    }
}
