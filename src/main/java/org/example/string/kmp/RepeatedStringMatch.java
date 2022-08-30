package org.example.string.kmp;

/**
 * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
 * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
 */
public class RepeatedStringMatch {


    public static int repeatedStringMatch(String a, String b) {
        if (a == null || b == null || a.equals("")) {
            return -1;
        }

        int ans = 1;
        StringBuilder sb = new StringBuilder(a);
        while (sb.length() < b.length()) {
            sb.append(a);
            ans++;
        }
        sb.append(a);

        int index = indexOf(sb.toString(), b);
        if (index == -1) {
            return -1;
        }

        if (index + b.length() > a.length() * ans) {
            return ans + 1;
        } else {
            return ans;
        }

    }

    private static int indexOf(String str, String match) {
        char[] sc = str.toCharArray();
        char[] mc = match.toCharArray();
        int si = 0;
        int mi = 0;
        int[] next = getNextArray(mc);
        while (si < str.length() && mi < match.length()) {
            if (sc[si] == mc[mi]) {
                si++;
                mi++;
            } else if (mi > 0) {
                mi = next[mi];
            } else {
                si++;
            }
        }
        return mi == match.length() ? si - mi : -1;
    }

    private static int[] getNextArray(char[] match) {
        if (match.length < 2) {
            return new int[]{-1};
        }

        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < next.length) {
            if (match[i-1] == match[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }
}
