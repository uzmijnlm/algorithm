package org.example.string.slidingwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个字符串 s 和一些 长度相同 的单词 words 。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符 ，但不需要考虑 words 中单词串联的顺序。
 * 示例 1：
 * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
 * 输出：[0,9]
 * 解释：
 * 从索引 0 和 9 开始的子串分别是 "barfoo" 和 "foobar" 。
 * 输出的顺序不重要, [9,0] 也是有效答案。
 */
public class FindSubstringWithWords {


    public static List<Integer> findSubstring(String s, String[] words) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String str : words) {
            wordCount.put(str, wordCount.getOrDefault(str, 0) + 1);
        }

        List<Integer> ans = new ArrayList<>();
        int wordLength = words[0].length();
        int wordsSize = words.length;
        for (int i = 0; i < wordLength; i++) { // i是起点，以每个i为出发点进行窗口滑动

            Map<String, Integer> wcForSubstring = new HashMap<>();
            int pointer = i; // pointer相当于右指针，左指针是隐性的，一开始也在起点

            while (pointer + wordLength <= s.length()) {
                String cur = s.substring(pointer, pointer + wordLength);
                wcForSubstring.put(cur, wcForSubstring.getOrDefault(cur, 0) + 1);

                // 如果右指针与起始距离超过总体长度，则删除最左的单词
                // 移动左指针在这里对应的操作就是移除最左的单词，可以认为左指针向右移动了一个单词的距离
                if (pointer - i >= wordsSize * wordLength) {
                    String first = s.substring(pointer - wordsSize * wordLength, pointer - wordsSize * wordLength + wordLength);
                    wcForSubstring.put(first, wcForSubstring.get(first) - 1);
                    if (wcForSubstring.get(first) == 0) {
                        wcForSubstring.remove(first);
                    }
                }

                if (wcForSubstring.equals(wordCount)) {
                    ans.add(pointer - (wordsSize - 1) * wordLength);
                }

                pointer += wordLength;
            }

        }
        return ans;
    }
}
