package org.example.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * 括号问题
 */
public class Parentheses {


    // 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
    public static List<String> generateParenthesis(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }
        List<List<String>> allList = new ArrayList<>();

        List<String> list0 = new ArrayList<>();
        list0.add("");
        List<String> list1 = new ArrayList<>();
        list1.add("()");

        allList.add(list0);
        allList.add(list1);

        for (int i = 2; i <= n; i++) {
            List<String> tmp = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                List<String> l1 = allList.get(j);
                List<String> l2 = allList.get(i - j - 1);
                for (String p1 : l1) {
                    for (String p2 : l2) {
                        String p = "(" + p1 + ")" + p2;
                        tmp.add(p);
                    }
                }
            }
            allList.add(tmp);
        }
        return allList.get(n);
    }


    // 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
    public static int longestValidParentheses(String s) {
        if (s.length() < 2) {
            return 0;
        }
        int[] dp = new int[s.length()];
        if (s.charAt(1) == ')' && s.charAt(0) == '(') {
            dp[1] = 2;
        }

        int max = dp[1];
        for (int i = 2; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = dp[i - 2] + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
