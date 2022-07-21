package org.example.string;

/**
 * 给定一个字符类型的数组chars[]，请在单词间做逆序调整
 * 例如：
 * "dog loves pig"，调整成"pig loves dog"
 * "I'm a student"，调整成"student. a I'm"
 */
public class RotateWord {

    // 先将整体逆序，再对每个单词逆序
    public void rotateWord(char[] chars) {
        if (chars == null || chars.length == 0) {
            return;
        }

        reverse(chars, 0, chars.length - 1);
        int l = -1;
        int r = -1;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ' ') {
                if (i == 0 || chars[i - 1] == ' ') {
                    l = i;
                }
                if (i == chars.length - 1 || chars[i + 1] == ' ') {
                    r = i;
                }
                if (l != -1 && r != -1) {
                    reverse(chars, l, r);
                    l = -1;
                    r = -1;
                }
            }
        }
    }

    private void reverse(char[] chars, int l, int r) {
        char tmp;
        while (l < r) {
            tmp = chars[l];
            chars[l] = chars[r];
            chars[r] = tmp;
            l++;
            r--;
        }
    }

    // 补充问题：给定一个字符数组chars[]和一个整数size，请把大小为size的左半区整体移到右半区，右半区整体移到左半区
    public void rotate(char[] chars, int size) {
        if (chars == null || size <= 0 || size >= chars.length) {
            return;
        }

        reverse(chars, 0, size - 1); // 前半部分逆序
        reverse(chars, size, chars.length - 1); // 后半部分逆序
        reverse(chars, 0, chars.length-1); // 整体逆序
    }

}
