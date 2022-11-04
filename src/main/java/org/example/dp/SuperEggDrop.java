package org.example.dp;

/**
 * 给你 k 枚相同的鸡蛋，并可以使用一栋从第 1 层到第 n 层共有 n 层楼的建筑。
 * 已知存在楼层 f ，满足 0 <= f <= n ，任何从 高于 f 的楼层落下的鸡蛋都会碎，从 f 楼层或比它低的楼层落下的鸡蛋都不会破。
 * 每次操作，你可以取一枚没有碎的鸡蛋并把它从任一楼层 x 扔下（满足 1 <= x <= n）。
 * 如果鸡蛋碎了，你就不能再次使用它。如果某枚鸡蛋扔下后没有摔碎，则可以在之后的操作中 重复使用 这枚鸡蛋。
 * 请你计算并返回要确定 f 确切的值 的 最小操作次数 是多少？
 */
public class SuperEggDrop {

    // 思路：
    // dp[i][j]表示给定i个鸡蛋，要确定j层楼，最坏情况下的最小操作次数
    // dp[1][0]显然是0，dp[0][j]定义为0
    // dp[1][j]均为1，因为只有一个鸡蛋，只能一层层试
    // 一般思路是从第1层开始试。因为鸡蛋要么碎，要么不碎，所以次数的累加只能从两种情况选择其一，而且选择其中更大的，表示最坏情况：
    // dp[i][j] = max(dp[i-1][x-1], dp[i][j-x]) + 1
    // 遍历x，选择最小的dp[i][j]，就是最坏情况下的最小操作次数
    // 但是这种做法会超时，所以可以用二分法决定从哪层楼开始试
    public static int superEggDrop(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[1][i] = i;
        }
        for (int i = 2; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                int count = Integer.MAX_VALUE;
                int left = 1;
                int right = j;
                while (left < right) {
                    int mid = (left + right) / 2;
                    int broken = dp[i - 1][mid - 1]; // 如果鸡蛋碎了，[1, mid-1]层需要的次数
                    int not_broken = dp[i][j - mid]; // 如果鸡蛋没碎，[mid+1, j]层需要的次数
                    if (broken > not_broken) { // 这里也可以是>=
                        right = mid;
                        count = Math.min(count, broken + 1);
                    } else {
                        left = mid + 1;
                        count = Math.min(count, not_broken + 1);
                    }
                }
                int broken = dp[i-1][left - 1];
                int not_broken = dp[i][j-left];
                if (broken > not_broken) {
                    count = Math.min(count, broken + 1);
                } else {
                    count = Math.min(count, not_broken + 1);
                }
                dp[i][j] = count;
            }
        }
        return dp[k][n];
    }
}
