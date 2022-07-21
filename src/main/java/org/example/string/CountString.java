package org.example.string;

/**
 * 返回str的统计字符串
 * 例如"aaabbadddffc"的统计字符串为"a_3_b_2_a_1_d_3_f_2_c_1"
 */
public class CountString {


    public String getCountString(String str) {
        if (str == null || str.equals("")) {
            return "";
        }

        char[] chars = str.toCharArray();
        String res = String.valueOf(chars[0]);
        int num = 1;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != chars[i - 1]) {
                res = concat(res, String.valueOf(num), String.valueOf(chars[i]));
            } else {
                num += 1;
            }
        }
        return concat(res, String.valueOf(num), "");
    }

    private String concat(String res, String num, String nextChar) {
        return res + "_" + num + ((nextChar.equals("")) ? "" : "_" + nextChar);
    }


    // 补充问题：给定统计字符串和一个整数index，返回原始字符串index位置的字符
    public char getCharAt(String str, int index) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] chars = str.toCharArray();
        boolean meetLetter = true;

        char cur = 0; // 当前字符，初始化为0表示空字符
        int num = 0; // 当前字符的计数
        int count = 0; // 相当于原字符串的位置

        for (char c : chars) {
            if (c == '_') {
                meetLetter = !meetLetter;
            } else if (meetLetter) {
                count += num;
                if (count > index) {
                    return cur;
                }
                num = 0;
                cur = c;
            } else {
                num = num * 10 + c - '0';
            }
        }

        // 单独统计最后一个字符
        count += num;
        if (count > index) {
            return cur;
        } else {
            return 0;
        }
    }
}
