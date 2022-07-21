package org.example.other;

/**
 * 回文数的概念如下：
 * 1.如果一个非负数左右完全对应，则该数是回文数
 * 2.如果一个负数的绝对值左右完全对应，也是回文数
 * 给定一个32位整数num，判断num是否是回文数
 */
public class Palindrome {


    // 步骤如下：
    // 1.假设判断的数字为非负数n，定义变量help，一开始help=1
    // 2.用help不停乘以10，直到变得与num的位数一样
    // 3.num/help的结果就是最高位的数字，num%10就是最低位的数字，由此进行比较
    // 4.如果相等，则令num=(num%help)/10，就是除去最高位和最低位后的值，help /= 100，继续比较，直到num==0
    // 5.对于负数，先看是不是Integer.MIN_VALUE，是的话直接返回false，否则求绝对值进行判断
    public boolean isPalindrome(int n) {
        if (n == Integer.MIN_VALUE) {
            return false;
        }

        n = Math.abs(n);
        int help = 1;
        while (n / help >= 10) {
            help *= 10;
        }
        while (n != 0) {
            if (n / help != n % 10) {
                return false;
            }
            n = (n % help) / 10;
            help /= 100;
        }
        return true;
    }
}
