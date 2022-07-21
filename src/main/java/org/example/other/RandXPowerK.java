package org.example.other;

/**
 * 假设函数Math.random()等概率随机返回一个在[0,1)范围上的数，那么在[0,x)区间上的数出现的概率为x（0<x<=1)
 * 给定一个大于0的整数k，并且可以使用Math.random()函数，实现一个函数依然返回在[0,1)范围上的数，但在[0,x)区间上的数出现的概率为x^k
 */
public class RandXPowerK {

    // 调用k次Math.random()，返回最大的那个数即可
    public double randXPowerK(int k) {
        if (k<1) {
            return 0;
        }
        double res = -1;
        for (int i = 0; i < k; i++) {
            res = Math.max(res, Math.random());
        }
        return res;
    }

}
