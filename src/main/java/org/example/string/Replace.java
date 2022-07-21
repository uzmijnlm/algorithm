package org.example.string;

/**
 * 给定一个字符数组chars[]，右半区全是空字符，左半区不含空字符
 * 现在想将左半区中所有的空格字符替换成"%20"，假设右半区足够大，可以满足替换所需要的空间
 * 例如："a b  c"替换为"a%20b%20%20c"
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class Replace {

    public void replace(char[] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }

        int num = 0; // 左半区空格数
        int len = 0;
        for (len = 0; len < chars.length && chars[len] != 0; len++) {
            if (chars[len] == ' ') {
                num++;
            }
        }
        int j = len + num * 2 - 1; // 替换后最后的索引位置
        for (int i = len - 1; i > -1; i--) {
            if (chars[i] != ' ') {
                chars[j--] = chars[i];
            } else {
                chars[j--] = '0';
                chars[j--] = '2';
                chars[j--] = '%';
            }
        }
    }

    // 补充问题：chars[]中只含有数字字符和'*'字符。现在把所有'*'字符挪到chars左边，数字字符挪到chars右边
    public void modify(char[] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }

        int j = chars.length - 1;
        for (int i = chars.length - 1; i > -1; i--) {
            if (chars[i] != '*') {
                chars[j--] = chars[i];
            }
        }
        while (j > -1) {
            chars[j--] = '*';
        }
    }


}
