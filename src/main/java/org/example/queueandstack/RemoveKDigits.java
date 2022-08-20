package org.example.queueandstack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 给你一个以字符串表示的非负整数 num 和一个整数 k ，移除这个数中的 k 位数字，使得剩下的数字最小。
 * 请你以字符串形式返回这个最小的数字。
 */
public class RemoveKDigits {

    public static String removeKDigits(String num, int k) {
        char[] chs = num.toCharArray();
        Deque<Character> deque = new LinkedList<>();
        for (int i = 0; i < chs.length; i++) {
            while (!deque.isEmpty() && k > 0 && chs[i] < deque.peekLast()){
                deque.pollLast();
                k--;
            }
            deque.addLast(chs[i]);
        }

        for (int i = 0; i < k; i++) {
            deque.pollLast();
        }

        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        while (!deque.isEmpty()) {
            char c = deque.pollFirst();
            if (leadingZero) {
                if (c != '0') {
                    leadingZero = false;
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}
