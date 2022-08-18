package org.example.string;

import org.example.string.kmp.KMP;
import org.example.string.kmp.RepeatedStringMatch;
import org.example.string.kmp.ShortestPalindrome;
import org.example.string.manacher.LongestPalindrome;
import org.example.string.manacher.Manacher;
import org.example.string.slidingwindow.*;
import org.example.string.twopointer.Compress;
import org.example.string.twopointer.FindLongest;
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

    @Test
    public void testRepeatedDNA() {
        for (int i = 0; i < 1000; i++) {
            int length = new Random(System.nanoTime()).nextInt(100) + 100;
            char[] selection = new char[]{'A', 'C', 'G', 'T'};
            char[] dna = new char[length];
            for (int j = 0; j < dna.length; j++) {
                dna[j] = selection[new Random(System.nanoTime()).nextInt(4)];
            }
            String s = String.valueOf(dna);
            String copiedS = copyStr(s);
            List<String> res1 = RepeatedDNA.findRepeatedDnaSequences(s);
            List<String> res2 = findRepeatedDnaSequences(copiedS);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res2.contains(res1.get(j));
            }
            for (int j = 0; j < res2.size(); j++) {
                assert res1.contains(res2.get(j));
            }
        }
    }

    // DNA序列 由一系列核苷酸组成，缩写为 'A', 'C', 'G' 和 'T'.。
    //
    //例如， "ACGAATTCCG" 是一个 DNA序列 。
    //在研究 DNA 时，识别 DNA 中的重复序列非常有用。
    //
    //给定一个表示 DNA序列 的字符串 s ，返回所有在 DNA 分子中出现不止一次的 长度为 10 的序列(子字符串)。你可以按 任意顺序 返回答案。
    private List<String> findRepeatedDnaSequences(String s) {
        List<String> ans = new ArrayList<>();
        int n = s.length();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i + 10 <= n; i++) {
            String cur = s.substring(i, i + 10);
            int cnt = map.getOrDefault(cur, 0);
            if (cnt == 1) ans.add(cur);
            map.put(cur, cnt + 1);
        }
        return ans;
    }

    @Test
    public void testCharReplace() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(100, true);
            int k = new Random(System.nanoTime()).nextInt(s.length());
            String copiedS = copyStr(s);
            assert CharReplace.characterReplacement(s, k) == characterReplacement(copiedS, k);
        }
    }

    // 给你一个字符串 s 和一个整数 k 。你可以选择字符串中的任一字符，并将其更改为任何其他大写英文字符。该操作最多可执行 k 次。
    //
    //在执行上述操作后，返回包含相同字母的最长子字符串的长度。
    private int characterReplacement(String s, int k) {
        if (s == null) {
            return 0;
        }
        int[] map = new int[26];
        char[] chars = s.toCharArray();
        int left = 0;
        int right = 0;
        int historyCharMax = 0;
        for (right = 0; right < chars.length; right++) {
            int index = chars[right] - 'A';
            map[index]++;
            historyCharMax = Math.max(historyCharMax, map[index]);
            if (right - left + 1 > historyCharMax + k) {
                map[chars[left] - 'A']--;
                left++;
            }
        }
        return chars.length - left;
    }


    @Test
    public void testFindAnagrams() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(1000);
            String p = generateRandomString(5);
            String copiedS = copyStr(s);
            String copiedP = copyStr(p);
            List<Integer> res1 = FindAnagrams.findAnagrams(copiedS, copiedP);
            List<Integer> res2 = findAnagrams(s, p);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res2.contains(res1.get(j));
            }
            for (int j = 0; j < res2.size(); j++) {
                assert res1.contains(res2.get(j));
            }
        }
    }


    // 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
    //
    //异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
    private List<Integer> findAnagrams(String s, String p) {
        int n = s.length(), m = p.length();
        List<Integer> res = new ArrayList<>();
        if (n < m) return res;

        int[] pCnt = new int[26];
        int[] sCnt = new int[26];

        for (int i = 0; i < m; i++) {
            pCnt[p.charAt(i) - 'a']++;
        }

        int left = 0;
        for (int right = 0; right < n; right++) {
            int curRight = s.charAt(right) - 'a';
            sCnt[curRight]++;
            while (sCnt[curRight] > pCnt[curRight]) {
                int curLeft = s.charAt(left) - 'a';
                sCnt[curLeft]--;
                left++;
            }
            if (right - left + 1 == m) {
                res.add(left);
            }
        }
        return res;
    }

    @Test
    public void testCheckInclusion() {
        for (int i = 0; i < 1000; i++) {
            String s1 = generateRandomString(20);
            List<Character> list = new ArrayList<>();
            for (int j = 0; j < s1.length(); j++) {
                list.add(s1.charAt(j));
            }
            Collections.shuffle(list);
            char[] chs = new char[s1.length()];
            for (int j = 0; j < chs.length; j++) {
                chs[j] = list.get(j);
            }
            String s2 = generateRandomString(50) + String.valueOf(chs) + generateRandomString(50);
            String copiedS1 = copyStr(s1);
            String copiedS2 = copyStr(s2);
            assert CheckInclusion.checkInclusion(s1, s2) == checkInclusion(copiedS1, copiedS2);
        }

        for (int i = 0; i < 1000; i++) {
            String s1 = generateRandomString(10);
            String s2 = generateRandomString(1000);
            String copiedS1 = copyStr(s1);
            String copiedS2 = copyStr(s2);
            assert CheckInclusion.checkInclusion(s1, s2) == checkInclusion(copiedS1, copiedS2);
        }
    }


    // 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
    //
    //换句话说，s1 的排列之一是 s2 的 子串 。
    private boolean checkInclusion(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        if (n > m) {
            return false;
        }
        int[] cnt = new int[26];
        for (int i = 0; i < n; ++i) {
            --cnt[s1.charAt(i) - 'a'];
        }
        int left = 0;
        for (int right = 0; right < m; ++right) {
            int x = s2.charAt(right) - 'a';
            ++cnt[x];
            while (cnt[x] > 0) {
                --cnt[s2.charAt(left) - 'a'];
                ++left;
            }
            if (right - left + 1 == n) {
                return true;
            }
        }
        return false;
    }


    @Test
    public void testCompareVersion() {
        for (int i = 0; i < 1000; i++) {
            int versionLength1 = new Random(System.nanoTime()).nextInt(3) + 1;
            String[] versions1 = new String[versionLength1];
            for (int j = 0; j < versionLength1; j++) {
                int fixLength = new Random(System.nanoTime()).nextInt(3) + 1;
                char[] chs = new char[fixLength];
                for (int k = 0; k < fixLength; k++) {
                    chs[k] = (char) (new Random(System.nanoTime()).nextInt(10) + '0');
                }
                versions1[j] = String.valueOf(chs);
            }
            StringBuilder version1 = new StringBuilder();
            for (int j = 0; j < versionLength1; j++) {
                version1.append(versions1[j]).append(".");
            }
            version1 = new StringBuilder(version1.substring(0, version1.length() - 1));

            int versionLength2 = new Random(System.nanoTime()).nextInt(3) + 1;
            String[] versions2 = new String[versionLength2];
            for (int j = 0; j < versionLength2; j++) {
                int fixLength = new Random(System.nanoTime()).nextInt(3) + 1;
                char[] chs = new char[fixLength];
                for (int k = 0; k < fixLength; k++) {
                    chs[k] = (char) (new Random(System.nanoTime()).nextInt(10) + '0');
                }
                versions2[j] = String.valueOf(chs);
            }
            StringBuilder version2 = new StringBuilder();
            for (int j = 0; j < versionLength2; j++) {
                version2.append(versions2[j]).append(".");
            }
            version2 = new StringBuilder(version2.substring(0, version2.length() - 1));

            String copiedV1 = copyStr(version1.toString());
            String copiedV2 = copyStr(version2.toString());

            assert CompareVersion.compareVersion(version1.toString(), version2.toString()) == compareVersion(copiedV1, copiedV2);
        }
    }

    // 给你两个版本号 version1 和 version2 ，请你比较它们。
    //
    //版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由 多位数字 组成，可能包含 前导零 。每个版本号至少包含一个字符。修订号从左到右编号，下标从 0 开始，最左边的修订号下标为 0 ，下一个修订号下标为 1 ，以此类推。例如，2.5.33 和 0.1 都是有效的版本号。
    //
    //比较版本号时，请按从左到右的顺序依次比较它们的修订号。比较修订号时，只需比较 忽略任何前导零后的整数值 。也就是说，修订号 1 和修订号 001 相等 。如果版本号没有指定某个下标处的修订号，则该修订号视为 0 。例如，版本 1.0 小于版本 1.1 ，因为它们下标为 0 的修订号相同，而下标为 1 的修订号分别为 0 和 1 ，0 < 1 。
    //
    //返回规则如下：
    //
    //如果 version1 > version2 返回 1，
    //如果 version1 < version2 返回 -1，
    //除此之外返回 0。
    private int compareVersion(String version1, String version2) {
        int n = version1.length(), m = version2.length();
        int i = 0, j = 0;
        while (i < n || j < m) {
            int x = 0;
            for (; i < n && version1.charAt(i) != '.'; ++i) {
                x = x * 10 + version1.charAt(i) - '0';
            }
            ++i; // 跳过点号
            int y = 0;
            for (; j < m && version2.charAt(j) != '.'; ++j) {
                y = y * 10 + version2.charAt(j) - '0';
            }
            ++j; // 跳过点号
            if (x != y) {
                return x > y ? 1 : -1;
            }
        }
        return 0;
    }


    @Test
    public void testCompress() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(100);
            char[] ch1 = s.toCharArray();
            char[] ch2 = s.toCharArray();
            assert Compress.compress(ch1) == compress(ch2);
            for (int j = 0; j < ch2.length; j++) {
                assert ch1[j] == ch2[j];
            }
        }
    }

    // 给你一个字符数组 chars ，请使用下述算法压缩：
    //
    //从一个空字符串 s 开始。对于 chars 中的每组 连续重复字符 ：
    //
    //如果这一组长度为 1 ，则将字符追加到 s 中。
    //否则，需要向 s 追加字符，后跟这一组的长度。
    //压缩后得到的字符串 s 不应该直接返回 ，需要转储到字符数组 chars 中。需要注意的是，如果组长度为 10 或 10 以上，则在 chars 数组中会被拆分为多个字符。
    //
    //请在 修改完输入数组后 ，返回该数组的新长度。
    //
    //你必须设计并实现一个只使用常量额外空间的算法来解决此问题。
    private int compress(char[] cs) {
        int n = cs.length;
        int right = 0, left = 0;
        while (right < n) {
            int index = right;
            while (index < n && cs[index] == cs[right]) index++;
            int cnt = index - right;
            cs[left++] = cs[right];
            if (cnt > 1) {
                int start = left, end = start;
                while (cnt != 0) {
                    cs[end++] = (char) ((cnt % 10) + '0');
                    cnt /= 10;
                }
                reverse(cs, start, end - 1);
                left = end;
            }
            right = index;
        }
        return left;
    }

    private void reverse(char[] cs, int start, int end) {
        while (start < end) {
            char t = cs[start];
            cs[start] = cs[end];
            cs[end] = t;
            start++;
            end--;
        }
    }


    @Test
    public void testFindLongestWord() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(100);
            char[] chs = s.toCharArray();
            int dictSize = new Random(System.nanoTime()).nextInt(10) + 2;
            List<String> dictionary = new ArrayList<>();
            for (int j = 0; j < dictSize; j++) {
                if (new Random(System.nanoTime()).nextInt(10) < 4) {
                    char[] cs = new char[new Random(System.nanoTime()).nextInt(chs.length) + 1];
                    int skip = chs.length - cs.length;
                    int index = 0;
                    for (int k = 0; k < cs.length; k++) {
                        if (skip > 0 && new Random(System.nanoTime()).nextInt(2) == 0) {
                            index++;
                            skip--;
                        } else {
                            cs[k] = chs[index++];
                        }
                    }
                    dictionary.add(String.valueOf(cs));
                } else {
                    dictionary.add(generateRandomString(100));
                }
            }

            assert FindLongest.findLongestWord(s, dictionary).equals(findLongestWord(s, dictionary));
        }
    }

    // 给你一个字符串 s 和一个字符串数组 dictionary ，找出并返回 dictionary 中最长的字符串，该字符串可以通过删除 s 中的某些字符得到。
    //
    //如果答案不止一个，返回长度最长且字母序最小的字符串。如果答案不存在，则返回空字符串。
    private String findLongestWord(String s, List<String> dictionary) {
        String res = "";
        for (String t : dictionary) {
            int index1 = 0, index2 = 0;
            while (index1 < t.length() && index2 < s.length()) {
                if (t.charAt(index1) == s.charAt(index2)) {
                    ++index1;
                }
                ++index2;
            }
            if (index1 == t.length()) {
                if (t.length() > res.length() || (t.length() == res.length() && t.compareTo(res) < 0)) {
                    res = t;
                }
            }
        }
        return res;
    }

    @Test
    public void testKMP() {
        for (int i = 0; i < 1000; i++) {
            String str = generateRandomString(100);
            String match = generateRandomString(100);
            str = str + match + generateRandomString(100);
            String copiedStr = copyStr(str);
            String copiedMatch = copyStr(match);
            assert KMP.getIndexOf(str, match) == getIndexOf(copiedStr, copiedMatch);
        }

        for (int i = 0; i < 1000; i++) {
            String str = generateRandomString(100);
            String match = generateRandomString(100);
            String copiedStr = copyStr(str);
            String copiedMatch = copyStr(match);
            assert KMP.getIndexOf(str, match) == getIndexOf(copiedStr, copiedMatch);
        }
    }


    // 步骤如下：
    // 1.生成match字符串的nextArr数组，这个数组的长度与match字符串的长度一样
    //   nextArr[i]的含义是在match[i]之前的字符串match[0...i-1]中，必须以match[i-1]结尾的后缀子串（不能包含match[0]）
    //   与必须以match[0]开头的前缀子串（不能包含match[i-1]）最大匹配长度是多少，这个长度就是nextArr[i]的值
    // 2.假设从str[i]字符出发开始依次匹配match中的字符，匹配到j位置的字符发现与match中的字符不一致，停止匹配
    //   因为现在已经有了nextArr数组，nextArr[j-1]的值表示match[0...j-i-1]这一段字符串前缀与后缀的最长匹配
    //   设前缀这一段为a，后缀这一段为b，a段的下一个字符为match[k]
    // 3.下一次匹配时，不再回退到str[i+1]重新开始与match[0]匹配，而是直接让str[j]与match[k]进行匹配
    private int getIndexOf(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }

        char[] ss = s.toCharArray();
        char[] ms = m.toCharArray();
        int si = 0;
        int mi = 0;
        int[] next = getNextArray(ms);
        while (si < ss.length && mi < ms.length) {
            if (ss[si] == ms[mi]) {
                si++;
                mi++;
            } else if (next[mi] == -1) {
                si++;
            } else {
                mi = next[mi];
            }
        }
        return mi == ms.length ? si - mi : -1;
    }


    // 规定nextArr[0]=-1。nextArr[1]按照定义为0
    // 对于i>1，nextArr[i]求解过程如下：
    // 1.通过nextArr[i-1]的值可知match[0...i-2]这部分的前缀部分和后缀部分，看前缀部分的下一个字符和后缀部分下一个字符是否相等
    //   后缀部分下一个就是match[i-1]
    // 2.如果相等，那么nextArr[i]=nextArr[i-1]+1
    // 3.如果不相等，设前缀部分下一个字符是match[cn]，查看nextArr[cn]的前缀和后缀部分，重复以上过程
    private int[] getNextArray(char[] ms) {
        if (ms.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int pos = 2;
        int cn = 0; // cn表示拿哪个位置和i-1位置比，同时表示最大匹配长度，即next[i-1]的值
        while (pos < next.length) {
            if (ms[pos - 1] == ms[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn]; // 下一个最大匹配长度由cn位置的next[cn]确定
            } else {
                next[pos++] = 0;
            }
        }
        return next;
    }


    @Test
    public void testShortestPalindrome() {
        for (int i = 0; i < 1000; i++) {
            String str = generateRandomString(5);
            String reversedStr = reverse(str);
            str = str + reversedStr + generateRandomString(5);
            String copiedStr = copyStr(str);
            assert ShortestPalindrome.shortestPalindrome(str).equals(shortestPalindrome(copiedStr));
        }
    }


    // 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
    private String shortestPalindrome(String s) {
        int n = s.length();
        if (n == 0) {
            return "";
        }
        // 字符串拼接为 s + "#" + _s
        int max = getNext(s + "#" + new StringBuilder(s).reverse());
        String last = (max == n - 1 ? "" : s.substring(max + 1));
        return new StringBuilder(last).reverse() + s;
    }

    // 求 next 数组
    private int getNext(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        int[] next = new int[n];
        next[0] = -1;
        int j = 0, k = -1;
        while (j < n - 1) {
            if (k == -1 || arr[j] == arr[k]) {
                next[++j] = ++k;

            } else {
                k = next[k];
            }
        }
        return next[n - 1];
    }


    @Test
    public void testRepeatedStringMatch() {
        for (int i = 0; i < 1000; i++) {
            String a = generateRandomString(20);
            String b = generateRandomString(100);
            String copiedA = copyStr(a);
            String copiedB = copyStr(b);
            assert RepeatedStringMatch.repeatedStringMatch(a, b) == repeatedStringMatch(copiedA, copiedB);
        }
        for (int i = 0; i < 1000; i++) {
            String a = generateRandomString(10);
            int repeat = new Random(System.nanoTime()).nextInt(10) + 1;
            StringBuilder sb = new StringBuilder();
            while (repeat > 0) {
                sb.append(a);
                repeat--;
            }
            int start = new Random(System.nanoTime()).nextInt(sb.length());
            int end = new Random(System.nanoTime()).nextInt(sb.length() - start) + start + 1;
            String b = sb.substring(start, end);
            String copiedA = copyStr(a);
            String copiedB = copyStr(b);
            assert RepeatedStringMatch.repeatedStringMatch(a, b) == repeatedStringMatch(copiedA, copiedB);
        }
    }


    // 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
    //
    //注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
    private int repeatedStringMatch(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int ans = 0;
        while (sb.length() < b.length() && ++ans > 0) sb.append(a);
        sb.append(a);
        int idx = strStr(sb.toString(), b);
        if (idx == -1) return -1;
        return idx + b.length() > a.length() * ans ? ans + 1 : ans;
    }

    private int strStr(String ss, String pp) {
        if (pp.isEmpty()) return 0;

        // 分别读取原串和匹配串的长度
        int n = ss.length(), m = pp.length();
        // 原串和匹配串前面都加空格，使其下标从 1 开始
        ss = " " + ss;
        pp = " " + pp;

        char[] s = ss.toCharArray();
        char[] p = pp.toCharArray();

        // 构建 next 数组，数组长度为匹配串的长度（next 数组是和匹配串相关的）
        int[] next = new int[m + 1];
        // 构造过程 i = 2，j = 0 开始，i 小于等于匹配串长度 【构造 i 从 2 开始】
        for (int i = 2, j = 0; i <= m; i++) {
            // 匹配不成功的话，j = next(j)
            while (j > 0 && p[i] != p[j + 1]) j = next[j];
            // 匹配成功的话，先让 j++
            if (p[i] == p[j + 1]) j++;
            // 更新 next[i]，结束本次循环，i++
            next[i] = j;
        }

        // 匹配过程，i = 1，j = 0 开始，i 小于等于原串长度 【匹配 i 从 1 开始】
        for (int i = 1, j = 0; i <= n; i++) {
            // 匹配不成功 j = next(j)
            while (j > 0 && s[i] != p[j + 1]) j = next[j];
            // 匹配成功的话，先让 j++，结束本次循环后 i++
            if (s[i] == p[j + 1]) j++;
            // 整一段匹配成功，直接返回下标
            if (j == m) return i - m;
        }
        return -1;
    }

    @Test
    public void testManacher() {
        for (int i = 0; i < 1000; i++) {
            String str = generateRandomString(100);
            String copiedStr = copyStr(str);
            assert Manacher.manacher(str) == manacher(copiedStr);
        }
    }



    // 基本思路就是遍历每个字符，找到每个字符的回文半径。不过Manacher算法对这个过程有加速。步骤如下：
    // 1.先将字符串变换成"#a#b#c#a#c#"的形式（每两个字符间插入特殊符号，保证长度无论是奇数还是偶数都能用同样方式处理）
    // 2.引入几个概念：
    //   pArr数组，pArr[i]的意义是以i位置上的字符作为回文中心，最大回文半径是多少
    //   变量r，表示遍历过程中所有回文半径中最右的位置
    //   变量c，表示最右半径对应的回文中心（显然这个变量会跟r一起更新）
    // 3.遍历过程中，记当前遍历到的位置是i，右边界是r，r对应的中心是c，那么i关于c的对称点是i`，左边界是l。有以下几种情况：
    //   3.1 i超过了r。比如一开始r为-1，那么0位置肯定在r的外部。此时只能老老实实向左右扩
    //   3.2 i`的半径在l以内，那么i的半径一定也不超过r，此时不用更新
    //   3.3 i`的半径超过了l，此时也不用更新
    //   3.4 i`的半径刚好落在l上，此时i需要从r的下一个位置开始比较
    // 4.基本思路如上，但是在实现时这样实现：
    //   由于3.2和3.3都不用扩，3.4也有一部分不用考虑，是直接从r外面开始判断，因此在代码实现上不需要分成4个条件分支来做
    //   可以先计算出不用考虑的那部分回文半径，然后从下一位开始判断。对于不需要再判断的那些情况，会直接跳出循环
    private int manacher(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = manacherString(str);
        int[] pArr = new int[chars.length];
        // 当前最大右边界对应的回文中心
        int c = -1;
        // 当前最大回文右边界的下一个位置
        // 含义实际上与"最大回文右边界"是一样的，只是代码实现更方便
        // 这样可以保证r-i表示的刚好就是r以内i的最大半径长度，而不需要+1
        // r==i时表示i已经超过了先前的最大半径范围，因此pArr[i]要初始化为1
        int r = -1;
        int max = Integer.MIN_VALUE; // 返回结果（变换后的字符串的最大回文半径-1就是原字符串的最大回文子串长度）
        for (int i = 0; i < chars.length; i++) {
            // 2*c-i就是i`的位置，pArr[2*c-i]就是i`的半径
            // i`的半径与r-i相比，取较小值，就是不用比较的部分。这涵盖了3.2、3.3、3.4这几种情况
            // 如果i不在r以内，则pArr[i]先初始化为1（自身的1个字符）。这表示情况3.1
            pArr[i] = r > i ? Math.min(pArr[2 * c - i], r - i) : 1;

            // 不越界的情况下从之前不用比较的位置之外开始比较
            // 情况3.2和3.3会直接break。情况3.1和3.4会不断比较
            while (i + pArr[i] < chars.length && i - pArr[i] > -1) {
                if (chars[i + pArr[i]] == chars[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            // i+pArr[i]表示i位置的右边界，如果超过了r，就更新r和c
            if (i + pArr[i] > r) {
                r = i + pArr[i];
                c = i;
            }

            // 更新最大半径
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    private char[] manacherString(String str) {
        char[] chars = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : chars[index++];
        }
        return res;
    }

    @Test
    public void testShortestEnd() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(100);
            String copiedS = copyStr(s);
            assert Manacher.shortestEnd(s).equals(shortestEnd(copiedS));
        }
    }


    // Manacher进阶问题：在字符串的最后添加最少字符，使整个字符串都成为回文串
    // 思路：查找必须要包含最后一个字符的情况下，最长的回文子串是什么，之前不是最长回文子串的部分逆序后添加在末尾就构成了结果
    // 在Manacher算法的思路下，从左到右计算回文半径时，关注回文半径最右即将到达的位置r，一旦发现已经到达最后，直接退出检查
    public String shortestEnd(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char[] chars = manacherString(str);
        int[] pArr = new int[chars.length];
        int c = -1;
        int r = -1;
        int maxContainsEnd = -1;
        for (int i = 0; i < chars.length; i++) {
            pArr[i] = r > i ? Math.min(pArr[2 * c - i], r - i) : 1;
            while (i + pArr[i] < chars.length && i - pArr[i] > -1) {
                if (chars[i + pArr[i]] == chars[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            if (i + pArr[i] > r) {
                r = i + pArr[i];
                c = i;
            }

            if (r == chars.length) {
                maxContainsEnd = pArr[i];
                break;
            }
        }

        // maxContainsEnd-1就是原字符串包含最后一个字符时的最长回文子串长度
        char[] res = new char[str.length() - (maxContainsEnd - 1)];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = chars[i * 2 + 1];
        }
        return String.valueOf(res);
    }


    @Test
    public void testLongestPalindrome() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(100);
            String copyS = copyStr(s);
            String res1 = LongestPalindrome.longestPalindrome(s);
            String res2 = longestPalindrome(copyS);
            if (!res1.equals(res2)) {
                assert res1.length() == res2.length();
                assert getIndexOf(s, res1) != -1;
                assert getIndexOf(s, res2) != -1;
            }
        }
    }



    // 给你一个字符串 s，找到 s 中最长的回文子串。
    private String longestPalindrome(String s) {
        int start = 0, end = -1;
        StringBuffer t = new StringBuffer("#");
        for (int i = 0; i < s.length(); ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        t.append('#');
        s = t.toString();

        List<Integer> arm_len = new ArrayList<Integer>();
        int right = -1, j = -1;
        for (int i = 0; i < s.length(); ++i) {
            int cur_arm_len;
            if (right >= i) {
                int i_sym = j * 2 - i;
                int min_arm_len = Math.min(arm_len.get(i_sym), right - i);
                cur_arm_len = expand(s, i - min_arm_len, i + min_arm_len);
            } else {
                cur_arm_len = expand(s, i, i);
            }
            arm_len.add(cur_arm_len);
            if (i + cur_arm_len > right) {
                j = i;
                right = i + cur_arm_len;
            }
            if (cur_arm_len * 2 + 1 > end - start) {
                start = i - cur_arm_len;
                end = i + cur_arm_len;
            }
        }

        StringBuffer ans = new StringBuffer();
        for (int i = start; i <= end; ++i) {
            if (s.charAt(i) != '#') {
                ans.append(s.charAt(i));
            }
        }
        return ans.toString();
    }

    private int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            --left;
            ++right;
        }
        return (right - left - 2) / 2;
    }


}
