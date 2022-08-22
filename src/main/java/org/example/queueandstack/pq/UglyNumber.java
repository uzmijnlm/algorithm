package org.example.queueandstack.pq;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 丑数问题
 */
public class UglyNumber {


    // 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
    // 丑数 就是只包含质因数 2、3 和/或 5 的正整数。
    // 第一个丑数是1
    // 核心思路：
    // 构造三元组(val, i, idx)，val表示丑数的值，i表示它属于哪个队列，idx表示val是第几个丑数乘以primes[i]得到的
    public static int nthUglyNumber(int n) {
        int[] primes = new int[]{2, 3, 5};
        Queue<Integer[]> pq = new PriorityQueue<>(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[0] - o2[0];
            }
        });
        for (int i = 0; i < primes.length; i++) {
            pq.add(new Integer[]{primes[i], i, 0});
        }

        int[] ans = new int[n];
        ans[0] = 1;
        int index = 1;
        while (index < n) {
            Integer[] poll = pq.poll();
            int val = poll[0];
            int i = poll[1];
            int idx = poll[2];
            if (ans[index - 1] != val) {
                ans[index++] = val;
            }
            pq.add(new Integer[]{ans[idx + 1] * primes[i], i, idx + 1});
        }
        return ans[n - 1];
    }


    // 超级丑数 是一个正整数，并满足其所有质因数都出现在质数数组 primes 中。
    //
    //给你一个整数 n 和一个整数数组 primes ，返回第 n 个 超级丑数 。
    //
    //题目数据保证第 n 个 超级丑数 在 32-bit 带符号整数范围内。
    public static int nthSuperUglyNumber(int n, int[] primes) {
        Queue<Integer[]> pq = new PriorityQueue<>(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[0] - o2[0];
            }
        });
        for (int i = 0; i < primes.length; i++) {
            pq.add(new Integer[]{primes[i], i, 0});
        }

        int[] ans = new int[n];
        ans[0] = 1;
        int index = 1;
        while (index < n) {
            Integer[] poll = pq.poll();
            int val = poll[0];
            int i = poll[1];
            int idx = poll[2];
            if (ans[index - 1] != val) {
                ans[index++] = val;
            }
            pq.add(new Integer[]{ans[idx + 1] * primes[i], i, idx + 1});
        }
        return ans[n - 1];
    }
}
