package org.example.string.slidingwindow;

/**
 * 给定一个全是小写字母的字符串，删除多余字符，使得每种字符只保留一个，并让最终结果字典序最小
 */
public class RemoveDupLetters {

    // str长度为N，有K种不同的字符，那么结果res的长度就是K
    // 核心思路是考虑如何从str中从左到右挑选res[0]、res[1]……使得字典序最小
    public static String removeDuplicateLetters(String s) {
        char[] str = s.toCharArray();

        // 词频统计
        int[] map = new int[26];
        for (char c : str) {
            map[c - 'a']++;
        }

        char[] res = new char[26];
        int index = 0; // res的索引
        int L = 0;
        int R = 0;

        // 滑动窗口
        while (R < str.length) {
            if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
                R++;
            } else {
                int pick = -1; // 要挑选的位置
                // L到R范围上，挑选字典序最小的位置
                for (int i = L; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
                        pick = i;
                    }
                }
                res[index++] = str[pick];

                // 恢复pick+1到R范围上的词频，因为之后还要在这个范围上找。而前面的则不会再找了
                for (int i = pick + 1; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1) {
                        map[str[i] - 'a']++;
                    }
                }

                map[str[pick] - 'a'] = -1; // 这个字符不再考虑
                L = pick + 1;
                R = L;
            }
        }
        return String.valueOf(res, 0, index);

    }
}
