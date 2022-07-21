package org.example.dp;

/**
 * 有排成一行的N个位置，记为1～N，N>=2。开始时机器人在M位置
 * 机器人可以向左或向右走，但不能越界。规定必须走K步，求最终能来到P位置的方法有多少种
 * 要求：时间复杂度O(N*K)
 */
public class RobotWalk {


    // 暴力递归
    public int ways1(int N, int M, int K, int P) {
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        return walk(N, M, K, P);
    }

    /**
     * @param N    总共N个位置，固定参数
     * @param cur  当前位置，可变参数
     * @param rest 剩余步数，可变参数
     * @param P    最终到达P，固定参数
     * @return 从cur走rest步到达P的方法数
     */
    private int walk(int N, int cur, int rest, int P) {
        if (rest == 0) {
            return cur == P ? 1 : 0;
        }
        if (cur == 1) {
            return walk(N, 2, rest - 1, P);
        }
        if (cur == N) {
            return walk(N, N - 1, rest - 1, P);
        }
        return walk(N, cur + 1, rest - 1, P) + walk(N, cur - 1, rest - 1, P);
    }


    // 动态规划
    public int ways2(int N, int M, int K, int P) {
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }

        int[][] dp = new int[K + 1][N];
        dp[0][P - 1] = 1;
        for (int row = 1; row <= K; row++) {
            for (int col = 0; col < N; col++) {
                if (col == 0) {
                    dp[row][col] = dp[row-1][1];
                } else if (col == N-1) {
                    dp[row][col] = dp[row-1][N-2];
                } else {
                    dp[row][col] = dp[row-1][col-1] + dp[row-1][col+1];
                }
            }
        }
        return dp[K][M-1];
    }


    // 动态规划+空间压缩
    public int ways3(int N, int M, int K, int P) {
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }

        int[] dp = new int[N];
        dp[P-1] = 1;
        for (int row = 1; row <= K; row++) {
            int leftUp = dp[1];
            for (int col = 0; col < N; col++) {
                int tmp = dp[col];
                if (col==0) {
                    dp[col] = dp[col+1];
                } else if (col==N-1) {
                    dp[col] = leftUp;
                } else {
                    dp[col] = leftUp + dp[col+1];
                }
                leftUp = tmp;
            }
        }
        return dp[M-1];
    }
}
