package org.example.string;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 公式字符串求值。给定一个字符串str，表示一个公式，公式里可能有整数、加减乘除符号和左右括号，返回计算结果
 * 无需进行公式有效性检查。如果是负数就需要用括号括起来，如果负数是公式或括号的开头，则可以没有括号
 * 不用考虑溢出
 */
public class GetValue {

    // 整个过程是递归的
    // 从左到右遍历str，开始遍历或者遇到字符'('时，就进行递归过程
    // str遍历完或遇到')'字符时，递归过程结束
    // 由于'('和')'是递归开始和结束的条件，因此整个过程就不用考虑括号了
    public int getValue(String exp) {
        return value(exp.toCharArray(), 0)[0];
    }

    /**
     * @param chars 要遍历的数组
     * @param i 遍历到到位置
     * @return 长度为2，第一位表示递归结果，第二位表示遍历递归过程遍历到的位置，接下来会从下一个位置继续遍历
     */
    private int[] value(char[] chars, int i) {
        Deque<String> deq = new LinkedList<>();
        int pre = 0; // 记录当前形成的数值
        int[] bra;
        while (i < chars.length && chars[i] != ')') { // 只要没到最后一个字符或没遇到')'，就继续遍历
            if (chars[i] >= '0' && chars[i] <= '9') { // 如果是数字字符，就跟前面的数字字符一起形成新的数值
                pre = pre * 10 + chars[i++] - '0';
            } else if (chars[i] != '(') { // 如果是四则运算符号，则把先前的数和运算符号放入栈中，并将pre清零
                addNum(deq, pre); // 将先前形成的数值放入到双向队列中（在放数的过程中相当于是一个栈）
                deq.addLast(String.valueOf(chars[i++])); // 将符号放入栈中
                pre = 0;
            } else { // 如果是'(' ，则从下一个字符开始递归调用
                bra = value(chars, i + 1);
                pre = bra[0];
                i = bra[1] + 1;
            }
        }
        addNum(deq, pre);
        return new int[]{getNum(deq), i};
    }

    // 从队列头部开始弹出元素，依次进行加减计算
    private int getNum(Deque<String> deq) {
        int res = 0;
        boolean add = true;
        String cur;
        int num;
        while (!deq.isEmpty()) {
            cur = deq.pollFirst();
            if (cur.equals("+")) {
                add = true;
            } else if (cur.equals("-")) {
                add = false;
            } else {
                num = Integer.parseInt(cur);
                res += add ? num : (-num);
            }
        }
        return res;
    }

    // 将数值按顺序放入栈中。放入时判断前一个符号是否是乘除，如果是的话则先弹出运算（以此体现乘除优先级高于加减），再把结果放入栈中
    private void addNum(Deque<String> deq, int num) {
        if (!deq.isEmpty()) {
            int cur;
            String top = deq.pollLast();
            if (top.equals("+") || top.equals("-")) {
                deq.addLast(top);
            } else {
                cur = Integer.parseInt(deq.pollLast());
                num = top.equals("*") ? (cur * num) : (cur / num);
            }
        }
        deq.addLast(String.valueOf(num));
    }


}
