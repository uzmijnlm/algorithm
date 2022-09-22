package org.example.other;

/**
 * 一个char类型的数组chars，其中所有的字符都不同
 * 例如：
 * chars=['A','B','C',...,'Z']，则字符串与整数对应关系是：
 * A,B,...,Z,AA,AB,...,AZ,BA,BB,...,ZZ,AAA,...,ZZZ,...
 * 1,2,...,26,27,28,...,52,53,54,...,702,703,...,18278,...
 * chars=['A','B','C']，则对应关系为：
 * A,B,C,AA,AB,...,CC,AAA,...,CCC,...
 * 1,2,3,4,5,...,12,13,...,39,...
 * 给定一个数组chars，实现根据对应关系完成字符串与整数相互转换的两个函数
 */
public class StringNumMapping {


    // 分析从数字到字符串。以chars=['A','B','C']，十进制数72为例：
    // 1.chars长度为3，从低位到高位依次为1,3,9,27,81……
    // 2.从1开始减，72减1等于71；71减3等于68……最后32减81时不够减了，此时知道需要使用前4位
    // 3.剩下32，能用1个27，加上之前的1个27，一共是2个27，即最高位是B
    // 4.剩下32%27=5，能用0个9，加上之前的1个9，一共是1个9，即第二位是A
    // 5.剩下5%9=5，能用1个3，加上之前的1个3，一共是2个3，即第三位是B
    // 6.剩下5%3=2，能用2个1，加上之前的1个1，一共是3个1，即第四位是C
    // 7.结果是"BABC"
    public String getString(char[] chars, int n) {
        if (chars == null || chars.length == 0 || n < 1) {
            return "";
        }

        int cur = 1;
        int base = chars.length;
        int len = 0;
        while (n >= cur) {
            len++;
            n -= cur;
            cur *= base;
        }

        char[] res = new char[len];
        int index = 0;
        int nCur;
        do {
            cur /= base;
            nCur = n / cur;
            res[index++] = getKthCharAtChs(chars, nCur + 1);
            n %= cur;
        } while (index != res.length);
        return String.valueOf(res);
    }

    private char getKthCharAtChs(char[] chars, int k) {
        if (k < 1 || k > chars.length) {
            return 0;
        }
        return chars[k - 1];
    }


    // 分析从字符串得到对应的数字。例如以chars=['A','B','C']，字符串是"ABBA"为例：
    // chars长度位3，从低位到高位就是1,3,9,27……
    // 字符串长度为4，表示从高到低用到27、9、3、1这4位，分别用了1个27、2个9、2个3、1个1
    // 对应的数字就是52
    public int getNum(char[] chars, String str) {
        if (chars == null || chars.length == 0) {
            return 0;
        }

        char[] strc = str.toCharArray();
        int base = chars.length;
        int cur = 1;
        int res = 0;
        for (int i = strc.length - 1; i != -1; i--) {
            res += getNthFromChar(chars, strc[i]) * cur;
            cur *= base;
        }
        return res;
    }

    private int getNthFromChar(char[] chars, char c) {
        int res = -1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == c) {
                res = i + 1;
                break;
            }
        }
        return res;
    }


}
