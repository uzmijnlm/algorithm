package org.example.other;

/**
 * 有一台机器按自然数顺序吐出球（1号球、2号球……）。你有一个袋子，袋子最多只能装下K个球
 * 设计一种选择方式，使得当机器吐出第N号球时（N>K），袋子中的球数是K个，同时保证从1号球到N号球中每一个被选进袋子的概率都是K/N
 * 已经吐出的球被选中的概率也动态地发生变化
 */
public class KNumsRand {

    // 蓄水池算法。步骤如下：
    // 1.处理1~k号球，直接放进袋子
    // 2.处理第i号球时（i>k），以k/i的概率决定是否将第i号球放进袋子
    //   如果不放进，直接扔掉；否则从袋子里随机扔掉一个，然后把这个球放进去
    // 3.后面的球重复这个过程
    public int[] getKNumsRand(int k, int max) {
        if (max < 1 || k < 1) {
            return null;
        }
        int[] res = new int[Math.min(k, max)];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        for (int i = k + 1; i < max + 1; i++) {
            if (rand(i) <= k) {
                res[rand(k) - 1] = i;
            }
        }
        return res;
    }

    private int rand(int i) {
        return (int) (Math.random() * i) + 1;
    }


}
