package org.example.dp;

/**
 * 给定数组arr，其中所有值都是正数且不重复。每个值表示一种面值的货币，每种面值的货币可以使用任意张
 * 给定一个整数aim，表示要找的钱数，求组成aim的最少货币数
 */
public class MinCoin {

    // 暴力递归
    public int minCoins1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }
        return process(arr, 0, aim);
    }

    // 面值从左到右考虑，i表示当前考虑的面值是arr[i]。rest表示剩下的aim
    // 返回从arr[i]时开始考虑时，拼凑出rest最少的张数
    // 返回-1表示找不出任何一种方法
    private int process(int[] arr, int i, int rest) {
        if (i == arr.length) {
            return rest == 0 ? 0 : -1;
        }
        int res = -1;
        // 一次尝试使用当前面值arr[i] 0张、1张……
        for (int j = 0; j * arr[i] <= rest; j++) {
            int next = process(arr, i + 1, rest - j * arr[i]);
            if (next != -1) {
                res = res == -1 ? next + j : Math.min(res, next + j);
            }
        }
        return res;
    }


    // 暴力递归优化成动态规划
    public int minCoins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return -1;
        }

        int[][] dp = new int[arr.length + 1][aim + 1];
        // 初始化最后一行
        for (int col = 1; col <= aim; col++) {
            dp[arr.length][col] = -1;
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[i][rest] = -1;
                if (dp[i + 1][rest] != -1) {
                    dp[i][rest] = dp[i + 1][rest];
                }
                if (rest - arr[i] >= 0 && dp[i][rest - arr[i]] != -1) {
                    if (dp[i][rest] == -1) {
                        dp[i][rest] = dp[i][rest - arr[i]] + 1;
                    } else {
                        dp[i][rest] = Math.min(dp[i][rest], dp[i][rest - arr[i]] + 1);
                    }
                }
            }
        }

        return dp[0][aim];
    }


}
