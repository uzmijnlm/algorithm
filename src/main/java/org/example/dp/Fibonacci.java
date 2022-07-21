package org.example.dp;

public class Fibonacci {


    // O(N^2)
    public int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n - 1) + f1(n - 2);
    }

    // O(N)
    public int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int res = 1;
        int pre = 1;
        int tmp;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    // O(logN) 利用矩阵二阶递推式直接求解
    public int f3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixPower(base, n - 2); // 公式：base矩阵的n-2次方
        return res[0][0] + res[1][0];
    }

    // 矩阵的p次方
    private int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        // 先将res设为单位矩阵，相当于整数求次幂时初始化为1
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = multiMatrix(res, tmp);
            }
            tmp = multiMatrix(tmp, tmp);
        }
        return res;
    }

    // 矩阵相乘
    private int[][] multiMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }


    // 补充问题1：n个台阶，每次只能走1个或2个，返回一共有多少种走法

    // O(N^2)
    public int s1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        return s1(n - 1) + s1(n - 2);
    }

    // O(N)
    public int s2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }

        int res = 2;
        int pre = 1;
        int tmp;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    // O(logN)
    public int s3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }

        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }


    // 补充问题2：母牛每年只会生1头小母牛，并且用永远不会死
    // 第一年有一只成熟的母牛。第二年开始生小牛。每只小母牛3年之后又可以生小母牛
    // 给定整数n，求n年后牛的数量

    // O(N^2)
    public int c1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return c1(n - 1) + c1(n - 3);
    }

    // O(N)
    public int c2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int res = 3;
        int pre = 2;
        int prePre = 1;
        int tmp1;
        int tmp2;
        for (int i = 4; i <= n; i++) {
            tmp1 = res;
            tmp2 = pre;
            res = res + prePre;
            pre = tmp1;
            prePre = tmp2;
        }
        return res;
    }

    // O(logN) 三阶递推式
    public int c3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int[][] base = {{1, 1, 0}, {0, 0, 1}, {1, 0, 0}};
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }


}
