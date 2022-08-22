package org.example.dp;

/**
 * 给你一个整数数组 nums 和一个整数 target 。
 * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
 * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
 * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
 */
public class FindTargetSumWays {

    public static int findTargetSumWays(int[] nums, int s) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        if (Math.abs(s) > Math.abs(sum)) {
            return 0;
        }

        int len = nums.length;
        int range = sum * 2 + 1;
        int[][] dp = new int[len][range];
        dp[0][sum + nums[0]] += 1;
        dp[0][sum - nums[0]] += 1;
        for (int i = 1; i < len; i++) {
            for (int j = -sum; j <= sum; j++) {
                if (j + nums[i] > sum) {
                    dp[i][j + sum] = dp[i - 1][j - nums[i] + sum];
                } else if (j - nums[i] < -sum) {
                    dp[i][j + sum] = dp[i - 1][j + nums[i] + sum];
                } else {
                    dp[i][j + sum] = dp[i - 1][j - nums[i] + sum] + dp[i - 1][j + nums[i] + sum];
                }
            }
        }
        return dp[len - 1][sum + s];
    }
}
