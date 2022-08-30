package org.example.string.kmp;

/**
 * 给定两个字符串str和match，长度分别为N和M
 * 实现一个算法，如果str中含有子串match，则返回match在str中的开始位置，不含邮则返回-1
 * 要求：如果M>N，可直接返回-1。如果N>=M，要求时间复杂度O(N)
 */
public class KMP {


    // 步骤如下：
    // 1.生成match字符串的nextArr数组，这个数组的长度与match字符串的长度一样
    //   nextArr[i]的含义是在match[i]之前的字符串match[0...i-1]中，必须以match[i-1]结尾的后缀子串（不能包含match[0]）
    //   与必须以match[0]开头的前缀子串（不能包含match[i-1]）最大匹配长度是多少，这个长度就是nextArr[i]的值
    // 2.假设从str[i]字符出发开始依次匹配match中的字符，匹配到j位置的字符发现与match中的字符不一致，停止匹配
    //   因为现在已经有了nextArr数组，nextArr[j-1]的值表示match[0...j-i-1]这一段字符串前缀与后缀的最长匹配
    //   设前缀这一段为a，后缀这一段为b，a段的下一个字符为match[k]
    // 3.下一次匹配时，不再回退到str[i+1]重新开始与match[0]匹配，而是直接让str[j]与match[k]进行匹配
    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || s.length() < m.length() || m.length() < 1) {
            return -1;
        }

        char[] sc = s.toCharArray();
        char[] mc = m.toCharArray();
        int[] next = getNextArray(mc);
        int si = 0;
        int mi = 0;
        while (si < s.length() && mi < m.length()) {
            if (sc[si] == mc[mi]) {
                si++;
                mi++;
            } else if (mi > 0) {
                mi = next[mi];
            } else {
                si++;
            }
        }
        return mi == m.length() ? si - mi : -1;
    }


    // 规定nextArr[0]=-1。nextArr[1]按照定义为0
    // 对于i>1，nextArr[i]求解过程如下：
    // 1.通过nextArr[i-1]的值可知match[0...i-2]这部分的前缀部分和后缀部分，看前缀部分的下一个字符和后缀部分下一个字符是否相等
    //   后缀部分下一个就是match[i-1]
    // 2.如果相等，那么nextArr[i]=nextArr[i-1]+1
    // 3.如果不相等，设前缀部分下一个字符是match[cn]，查看nextArr[cn]的前缀和后缀部分，重复以上过程
    private static int[] getNextArray(char[] match) {
        if (match.length < 2) {
            return new int[]{-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < match.length) {
            if (match[i - 1] == match[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                i++;
            }
        }
        return next;
    }


}
