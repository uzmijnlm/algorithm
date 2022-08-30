package org.example.other;

/**
 * 与int转换相关的问题
 */
public class IntConvert {

    // 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
    // 函数 myAtoi(string s) 的算法如下：
    // 读入字符串并丢弃无用的前导空格
    // 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。
    // 如果两者都不存在，则假定结果为正。
    // 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
    // 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。
    // 必要时更改符号（从步骤 2 开始）。
    // 如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。
    // 具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
    // 返回整数作为最终结果。
    // 注意：
    // 本题中的空白字符只包括空格字符 ' ' 。
    // 除前导空格或数字后的其余字符串外，请勿忽略 任何其他字符。
    public int myAtoi(String str) {
        if (str == null) {
            return 0;
        }
        int n = str.length();
        int i = 0;
        int res = 0;
        boolean is_negative = false;
        int maxValueDiv10 = Integer.MAX_VALUE / 10;
        int minValueMod10 = -(Integer.MIN_VALUE % 10);
        int maxValueMod10 = Integer.MAX_VALUE % 10;
        //第一步，跳过前面若干个空格
        while (i < n && str.charAt(i) == ' ') {
            ++i;
        }
        //如果字符串全是空格直接返回
        if (i == n) {
            return 0;
        }
        //第二步，判断正负号
        if (str.charAt(i) == '-') {
            is_negative = true;
        }
        //如果是正负号，还需要将指针i，跳过一位
        if (str.charAt(i) == '-' || str.charAt(i) == '+') {
            ++i;
        }
        //第三步，循环判断字符是否在 0~9之间
        while (i < n && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            int tmp = str.charAt(i) - '0';
            if (!is_negative && (res > maxValueDiv10 || (res == maxValueDiv10 && tmp > maxValueMod10))) {
                return Integer.MAX_VALUE;
            }
            if (is_negative && (res > maxValueDiv10 || (res == maxValueDiv10 && tmp > minValueMod10))) {
                return Integer.MIN_VALUE;
            }
            res = res * 10 + tmp;
            ++i;
        }
        //如果有负号标记则返回负数
        if (is_negative) {
            return -res;
        }
        return res;
    }


    // 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
    // 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
    // 例如，121 是回文，而 123 不是。
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int res = 0;
        int origin = x;
        int maxValueDiv10 = Integer.MAX_VALUE / 10;
        int minValueMod10 = Integer.MIN_VALUE % 10;
        int maxValueMod10 = Integer.MAX_VALUE % 10;
        while (x != 0) {
            int tmp = x % 10;

            if (res < -maxValueDiv10 || (res == -maxValueDiv10 && tmp < minValueMod10)) {
                return false;
            }
            if (res > maxValueDiv10 || (res == maxValueDiv10 && tmp > maxValueMod10)) {
                return false;
            }

            res = res * 10 + tmp;
            x /= 10;
        }
        return res == origin;
    }


    // 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
    // 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
    // 假设环境不允许存储 64 位整数（有符号或无符号）。
    public int reverse(int x) {
        int res = 0;
        int maxValueDiv10 = Integer.MAX_VALUE / 10;
        int minValueMod10 = Integer.MIN_VALUE % 10;
        int maxValueMod10 = Integer.MAX_VALUE % 10;
        while (x != 0) {
            int tmp = x % 10;

            if (res < -maxValueDiv10 || (res == -maxValueDiv10 && tmp < minValueMod10)) {
                return 0;
            }
            if (res > maxValueDiv10 || (res == maxValueDiv10 && tmp > maxValueMod10)) {
                return 0;
            }

            res = res * 10 + tmp;
            x /= 10;
        }
        return res;
    }
}
