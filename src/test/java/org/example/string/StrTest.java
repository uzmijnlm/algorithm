package org.example.string;

import org.example.string.slidingwindow.FindSubstringWithWords;
import org.example.string.slidingwindow.LongestSubstringWithoutDup;
import org.example.string.slidingwindow.MinWindowContainsAllChar;
import org.junit.Test;

import java.util.*;

public class StrTest extends BaseTest {


    @Test
    public void testLongestSubstringWithoutDup() {
        for (int i = 0; i < 1000; i++) {
            String str = generateRandomString(100);
            String copiedStr = copyStr(str);
            assert LongestSubstringWithoutDup.lengthOfLongestSubstring(str) == lengthOfLongestSubstring(copiedStr);
        }
    }

    // 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
    private int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }


    @Test
    public void testFindSubstringWithWords() {
        for (int i = 0; i < 1000; i++) {
            int wordLength = new Random(System.nanoTime()).nextInt(4) + 4;
            int wordsSize = new Random(System.nanoTime()).nextInt(4) + 3;
            String[] words = new String[wordsSize];
            for (int j = 0; j < wordsSize; j++) {
                words[j] = generateRandomStringWithFixedLength(wordLength);
            }
            String str = generateWithWords(words);
            String copiedStr = copyStr(str);
            String[] copiedWords = new String[wordsSize];
            for (int j = 0; j < words.length; j++) {
                copiedWords[j] = words[j];
            }

            List<Integer> res1 = FindSubstringWithWords.findSubstring(str, words);
            List<Integer> res2 = findSubstring(copiedStr, copiedWords);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res2.contains(res1.get(j));
            }
            for (int j = 0; j < res2.size(); j++) {
                assert res1.contains(res2.get(j));
            }

        }
    }

    private String generateWithWords(String[] words) {
        StringBuilder sb = new StringBuilder();
        int times = new Random(System.nanoTime()).nextInt(10);
        for (int i = 0; i < times; i++) {
            sb.append(generateRandomString(10));

            List<String> list = new ArrayList<>(Arrays.asList(words));
            Collections.shuffle(list);
            for (String s : list) {
                sb.append(s);
            }

            sb.append(generateRandomString(10));
        }
        return sb.toString();
    }


    // 给定一个字符串 s 和一些 长度相同 的单词 words 。找出 s 中恰好可以由 words 中所有单词串联形成的子串的起始位置。
    //
    //注意子串要与 words 中的单词完全匹配，中间不能有其他字符 ，但不需要考虑 words 中单词串联的顺序。
    private List<Integer> findSubstring(String s, String[] words) {
        int n = s.length(), m = words.length, w = words[0].length();
        // 统计 words 中「每个目标单词」的出现次数
        Map<String, Integer> map = new HashMap<>();
        for (String str : words) map.put(str, map.getOrDefault(str, 0) + 1);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            // 构建一个当前子串对应的哈希表，统计当前子串中「每个目标单词」的出现次数
            Map<String, Integer> temp = new HashMap<>();
            // 滑动窗口的大小固定是 m * w，每次将下一个单词添加进 temp，上一个单词移出 temp
            for (int j = i; j + w <= n; j += w) {
                String cur = s.substring(j, j + w);
                temp.put(cur, temp.getOrDefault(cur, 0) + 1);
                if (j >= i + (m * w)) {
                    int idx = j - m * w;
                    String prev = s.substring(idx, idx + w);
                    if (temp.get(prev) == 1) temp.remove(prev);
                    else temp.put(prev, temp.get(prev) - 1);
                    if (!temp.getOrDefault(prev, 0).equals(map.getOrDefault(prev, 0))) continue;
                }
                if (!temp.getOrDefault(cur, 0).equals(map.getOrDefault(cur, 0))) continue;
                // 上面两个 continue 可以减少 map 之间的 equals 操作
                if (temp.equals(map)) ans.add(j - (m - 1) * w);
            }
        }
        return ans;
    }


    @Test
    public void testMinWindowContainsAllChar() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(100);
            String t = generateRandomString(10);
            String copiedS = copyStr(s);
            String copiedT = copyStr(t);
            assert MinWindowContainsAllChar.minWindow(s, t).equals(minWindow(copiedS, copiedT));
        }
    }

    // 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
    //
    //注意：
    //
    //对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
    //如果 s 中存在这样的子串，我们保证它是唯一的答案。
    public String minWindow(String s, String t) {
        //滑动窗口——左开右闭的区间
        int left = 0, right = 0, valid = 0;
        //返回子串时需要的变量（substring左闭右开）
        int start = 0, end = 0, len = Integer.MAX_VALUE;
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        while (right < s.length()) {
            char r = s.charAt(right);
            //扩张右边界
            right++;
            if (need.containsKey(r)) {
                window.put(r, window.getOrDefault(r, 0) + 1);
                //只有当字母对应的个数都相等了才算一个有效
                //注意用equals，==的话超过-128～127就会出错
                if (window.get(r).equals(need.get(r))) {
                    valid++;
                }
            }
            //只有当覆盖的串中能全部包含t才可以开始缩减左边界
            while (valid == need.size()) {
                //满足条件就看一次长度，往小了取
                int temp = right - left;
                if (len > temp) {
                    len = temp;
                    start = left;
                    end = right;
                }
                char l = s.charAt(left);
                left++;
                if (need.containsKey(l)) {
                    if (window.get(l).equals(need.get(l))) {
                        valid--;
                    }
                    window.put(l, window.get(l) - 1);
                }
            }
        }
        return len != Integer.MAX_VALUE ? s.substring(start, end) : "";
    }

}
