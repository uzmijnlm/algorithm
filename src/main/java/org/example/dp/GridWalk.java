package org.example.dp;

public class GridWalk {

    // 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
    //
    //机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
    //
    //问总共有多少条不同的路径？
    public static int uniquePaths(int m, int n) {
        int shorter = Math.min(m, n);
        int longer = Math.max(m, n);
        int[] dp = new int[shorter];
        for (int i = 0; i < shorter; i++) {
            dp[i] = 1;
        }
        for (int i = 1; i < longer; i++) {
            for (int j = 1; j < shorter; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        return dp[shorter - 1];
    }


    // 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
    //
    //机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
    //
    //现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
    //
    //网格中的障碍物和空位置分别用 1 和 0 来表示。
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        dp[0][0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        for (int i = 0; i < obstacleGrid.length; i++) {
            if (obstacleGrid[i][0] == 0) {
                dp[i][0] = 1;
            } else {
                break;
            }
        }
        for (int i = 0; i < obstacleGrid[0].length; i++) {
            if (obstacleGrid[0][i] == 0) {
                dp[0][i] = 1;
            } else {
                break;
            }
        }

        for (int i = 1; i < obstacleGrid.length; i++) {
            for (int j = 1; j < obstacleGrid[0].length; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }


    // 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
    //
    //说明：每次只能向下或者向右移动一步。
    public static int minPathSum(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < grid.length; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < grid[0].length; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }

        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[dp.length - 1][dp[0].length - 1];
    }
}
