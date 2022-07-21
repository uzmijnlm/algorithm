package org.example.arr;

/**
 * 给定一个整型数组，返回不包含本位置值的累乘数组
 * 例如：arr=[2,3,1,4]，返回[12,8,24,6]，即除自己外，其他位置上的累乘
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class Product {

    public int[] product1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }

        int countOfZero = 0;
        int all = 1; // 所有不为0的数的乘积
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                all *= arr[i];
            } else {
                countOfZero++;
            }
        }
        int[] res = new int[arr.length];
        // 如果没有0，则直接用除法可以计算
        // 如果有1个0，则0位置上的值为all，其余都是0
        // 如果有大于1个0，则所有位置都是0
        if (countOfZero == 0) {
            for (int i = 0; i < arr.length; i++) {
                res[i] = all / arr[i];
            }
        }
        if (countOfZero == 1) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 0) {
                    res[i] = all;
                }
            }
        }
        return res;
    }


    // 进阶为题：不能用除法。时间复杂度O(N)，空间复杂度O(1)
    // 思路：
    // 1.生成两个长度和arr一样的新数组lr[]和rl[]。lr[i]表示arr[0...i]上的累乘，rl[i]表示arr[i...N-1]上的累乘
    // 2.一个位置上除去自己值的累乘res[i]=lr[i-1]*rl[i+1]。同时res[0]=rl[1]，res[N-1]=lr[N-2]
    // 3.复用结果数组res[]分别作为lr[]和rl[]，这样就节省了额外空间
    public int[] product2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }

        int[] res = new int[arr.length];
        // 将res数组作为lr数组
        res[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            res[i] = res[i - 1] * arr[i];
        }
        // 从右往左遍历arr，用变量tmp作为rl[i+1]的值
        int tmp = 1;
        for (int i = arr.length - 1; i > 0; i--) {
            res[i] = res[i - 1] * tmp;
            tmp *= arr[i];
        }
        res[0] = tmp;
        return res;
    }

}
