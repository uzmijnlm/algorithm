package org.example.other;

/**
 * 给定一个非负整数N，返回N!结果末尾0的个数
 */
public class ZeroNum {

    public int zeroNum(int num) {
        if (num < 0) {
            return 0;
        }
        int res = 0;
        while (num != 0) {
            res += num / 5; // 依次计算有多少个5、多少个25、多少个125……
            num /= 5;
        }
        return res;
    }


    // 进阶问题：给定一个非负整数N，如果用二进制数表达N!的结果，返回最低位的1在哪个位置，认为最右的位置为位置0
    // 思路：最低位的1在哪个位置，完全取决于有多少个因子2，每存在一个，1就向左移动一位
    public int rightOne(int num) {
        if (num < 1) {
            return -1;
        }
        int res = 0;
        while (num != 0) {
            num >>>= 1;
            res += num;
        }
        return res;
    }
}
