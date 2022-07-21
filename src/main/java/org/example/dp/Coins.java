package org.example.dp;

/**
 * 给定数组arr，其中所有值都为正数且不重复，每个值代表一种面值的货币
 * 每种面值的货币可以使用任意张，再给定一个整数aim，求有多少种方式可以组成aim
 */
public class Coins {

    // 暴力递归
    public int coins1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process1(arr, 0, aim);
    }

    /**
     * @param arr    货币面值数组，固定参数
     * @param index  arr的索引
     * @param remain 剩余的额度
     * @return 从index开始选择货币，有多少种方式可以组成remain
     */
    private int process1(int[] arr, int index, int remain) {
        int res = 0;
        if (index == arr.length) {
            res = remain == 0 ? 1 : 0;
        } else {
            for (int i = 0; arr[index] * i <= remain; i++) { // index位置的货币选i张
                res += process1(arr, index + 1, remain - arr[index] * i);
            }
        }
        return res;
    }


    // 记忆化搜索
    public int coins2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] map = new int[arr.length + 1][aim + 1];
        return process2(arr, 0, aim, map);
    }

    private int process2(int[] arr, int index, int remain, int[][] map) {
        int res = 0;
        if (index == arr.length) {
            res = remain == 0 ? 1 : 0;
        } else {
            int mapValue;
            for (int i = 0; arr[index] * i <= remain; i++) {
                mapValue = map[index + 1][remain - arr[index] * i];
                if (mapValue != 0) {
                    res += mapValue == -1 ? 0 : mapValue;
                } else {
                    res += process2(arr, index + 1, remain - arr[index] * i, map);
                }
            }
        }
        map[index][remain] = res == 0 ? -1 : res; // -1表示计算过这个值，但是返回值是0。用以区分还没有计算过的位置
        return res;
    }


    // 动态规划
    public int coins3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; arr[0] * i <= aim; i++) {
            dp[0][arr[0] * i] = 1;
        }
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                num = 0;
                for (int k = 0; j - arr[i] * k >= 0; k++) {
                    num += dp[i - 1][j - arr[i] * k];
                }
                dp[i][j] = num;
            }
        }
        return dp[arr.length - 1][aim];
    }

    public int coins4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; arr[0] * j <= aim; j++) {
            dp[0][arr[0] * j] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }
        return dp[arr.length - 1][aim];
    }

    public int coins5(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int[] dp = new int[aim + 1];
        for (int j = 0; arr[0] * j <= aim; j++) {
            dp[arr[0] * j] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[j] += j - arr[i] >= 0 ? dp[j - arr[i]] : 0;
            }
        }
        return dp[aim];
    }

}
