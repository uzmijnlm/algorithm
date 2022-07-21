package org.example.string;

/**
 * 给定字符串str，其中绝对不含'.'和'*'
 * 再给定字符串exp，其中可以含有'.'或'*'，'*'不能是exp的首字符，并且任意两个'*'字符不相邻
 * '.'代表任何一个字符，'*'代表其前一个字符可以有0个或多个
 * 写一个函数，判断str能否被exp匹配
 */
public class IsMatch {

    // 验证str和exp的有效性
    private boolean isValid(char[] s, char[] e) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '*' || s[i] == '.') {
                return false;
            }
        }
        for (int i = 0; i < e.length; i++) {
            if (e[i] == '*' && (i == 0 || e[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }


    public boolean isMatch(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        if (isValid(s, e)) {
            return process(s, e, 0, 0);
        } else {
            return false;
        }
    }

    // 递归判断str[si...sLen]是否能被exp[ei...eLen]匹配
    private boolean process(char[] s, char[] e, int si, int ei) {
        // 情况1：ei到了最后位置，根据si是否是最后位置判断是否匹配
        if (ei == e.length) {
            return si == s.length;
        }

        // 情况2：ei下一个位置不是'*'（ei+1==e.length表示没有ei+1位置，当然也意味着ei下一个位置不是'*'）
        if (ei + 1 == e.length || e[ei + 1] != '*') {
            return si != s.length // si有字符，否则肯定配不出来
                    && (e[ei] == s[si] || e[ei] == '.') // ei和si能配上
                    && process(s, e, si + 1, ei + 1); // str[si+1...sLen]和exp[ei+1...eLen]能配上
        }

        // 情况3：ei下一个位置是'*'
        // 这个循环的含义是用'*'抵消掉一个个相等的s[si]，直到不相等
        while (si != s.length && (e[ei] == s[si] || e[ei] == '.')) {
            // 情况3.1：ei和si位置能匹配，判断str[si...sLen]和exp[ei+2...eLen]能配上（即把*当成0个）
            // 如果能匹配上，则直接返回true；否则用*抵消一个s[si]，让si来到下一个位置继续循环
            if (process(s, e, si, ei + 2)) {
                return true;
            }
            si++;
        }

        // 情况3.3：ei和si位置不能匹配，'*'当成0个，直接判断str[si...sLen]与exp[ei+2...eLen]能否匹配
        return process(s, e, si, ei + 2);
    }


    // 动态规划
    // 本题难点在于初始值不全（根据递归无法填充齐全初始值），要依靠理解题意来初始化dp
    public boolean isMatchDP(String str, String exp) {
        if (str == null || exp == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] e = exp.toCharArray();
        if (!isValid(s, e)) {
            return false;
        }

        boolean[][] dp = initDP(s, e);

        // 已经初始化了右边两列和最后一行，剩下的从下往上、从右往左填充即可
        // 根据递归的几种情况进行对应。其中情况1已经初始化好了
        for (int i = s.length - 1; i > -1; i--) {
            for (int j = e.length - 2; j > -1; j--) {
                if (e[j + 1] != '*') { // 情况2
                    dp[i][j] = (s[i] == e[j] || e[j] == '.') && dp[i + 1][j + 1];
                } else {
                    int si = i;
                    // 情况3
                    while (si != s.length && (s[si] == e[j] || e[j] == '.')) {
                        // 情况3.1
                        if (dp[si][j + 2]) {
                            dp[i][j] = true;
                            break;
                        }
                        si++;
                    }
                    // 情况3.2
                    if (!dp[i][j]) {
                        dp[i][j] = dp[si][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }

    // 因为一个普通位置的dp[i][j]依赖dp[i+1][j+1]和dp[si][j+2]其中si>=i
    // 即它右下角的格子和j+2这一列中i行及以下所有行的格子
    // 因此，初始化时要初始化最右边两列和最下面一行
    private boolean[][] initDP(char[] s, char[] e) {
        int sLen = s.length;
        int eLen = e.length;
        boolean[][] dp = new boolean[sLen + 1][eLen + 1];
        dp[sLen][eLen] = true; // ei和si都耗尽，都匹配上了，填true

        // 最后一行，从右往左判断，如果范式是类似"...a*b*"，则每隔一列为true，直到范式断了，前面的都填false
        for (int j = eLen - 2; j > -1; j = j - 2) {
            if (e[j] != '*' && e[j + 1] == '*') {
                dp[sLen][j] = true;
            } else {
                break;
            }
        }

        // s和e都剩一个字符，则对这一个字符进行判断，能匹配上则为true，否则为false
        if (sLen > 0 && eLen > 0) {
            if ((e[eLen - 1] == '.') || s[sLen - 1] == e[eLen - 1]) {
                dp[sLen - 1][eLen - 1] = true;
            }
        }
        return dp;
    }


}
