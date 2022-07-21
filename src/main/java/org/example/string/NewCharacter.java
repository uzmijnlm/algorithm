package org.example.string;

/**
 * 新类型字符定义如下：
 * 1.新类型字符是长度为1或2的字符串
 * 2.表现形式可以仅是小写字母，也可以是大写字母+小写字母，还可以是大写字母+大写字母
 * 给定一个字符串str，str一定是若干新类型字符正确组合的结果。例如"eaCCBi"由"e"、"a"、"CC"、"Bi"组成
 * 再给定一个一个整数k，代表str中的位置。请返回被k位置指定的新类型字符
 */
public class NewCharacter {

    // 从k-1位置开始，向左统计连续出现的大写字母的数量记为uNum，遇到小写字母就停止
    // 如果uNum为奇数，str[k-1...k]是被指定的新类型字符
    // 如果为偶数且str[k]是大写字母，则str[k...k+1]是被指定的新类型字符
    // 如果为偶数且str[k]是小写字母，则str[k]是被指定的新类型字符
    public String pointNewCharacter(String s, int k) {
        if (s == null || s.equals("") || k < 0 || k >= s.length()) {
            return "";
        }
        char[] chars = s.toCharArray();
        int uNum = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (!Character.isUpperCase(chars[i])) {
                break;
            }
            uNum++;
        }
        if ((uNum & 1) == 1) {
            return s.substring(k - 1, k + 1);
        }
        if (Character.isUpperCase(chars[k])) {
            return s.substring(k, k + 2);
        }
        return String.valueOf(chars[k]);
    }
}
