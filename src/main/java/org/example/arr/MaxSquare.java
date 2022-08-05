package org.example.arr;

/**
 * 给定一个N*N的矩阵，其中只有0和1，返回边框全是1的最大正方形的边长长度
 * 要求：时间复杂度O(N^3)
 */
public class MaxSquare {

    // 基本步骤：
    // 1.遍历每个位置（O(N^2)）
    // 2.对每个位置依次检查它是否可以作为边长为n(1<=n<=N)的正方形的左上角（O(N)）
    // 3.对于每个n进行检查。这一步要做到O(1)，需要对矩阵进行预处理：
    //   根据矩阵matrix得到两个矩阵right和down
    //   right[i][j]表示从位置(i,j)出发向右，有多少个连续的1
    //   down[i][j]表示从(i,j)出发向下，有多少个连续的1
    public int getMaxSize(int[][] m) {
        int[][] right = new int[m.length][m[0].length];
        int[][] down = new int[m.length][m[0].length];
        initBorderMap(m, right, down);

        for (int size = Math.min(m.length, m[0].length); size != 0; size--) {
            if (hasSizeOfBorder(size, right, down)) {
                return size;
            }
        }
        return 0;
    }

    // 对每个位置检查它是否可以作为边长为size的正方形的左上角
    private boolean hasSizeOfBorder(int size, int[][] right, int[][] down) {
        for (int i = 0; i != right.length - size + 1; i++) {
            for (int j = 0; j != right[0].length - size + 1; j++) {
                if (right[i][j] >= size
                        && down[i][j] >= size
                        && right[i + size - 1][j] >= size
                        && down[i][j + size - 1] >= size) {
                    return true;
                }
            }
        }
        return false;
    }

    // 先计算矩阵的右下角
    // 从右下角开始往上计算，即在matrix最后一列上计算
    private void initBorderMap(int[][] m, int[][] right, int[][] down) {
        int r = m.length;
        int c = m[0].length;
        // 初始化右下角
        if (m[r - 1][c - 1] == 1) {
            right[r - 1][c - 1] = 1;
            down[r - 1][c - 1] = 1;
        }
        // 初始化最后一列
        for (int i = r - 2; i != -1; i--) {
            if (m[i][c - 1] == 1) {
                right[i][c - 1] = 1;
                down[i][c - 1] = down[i + 1][c - 1] + 1;
            }
        }
        // 初始化最后一行
        for (int i = c - 2; i != -1; i--) {
            if (m[r - 1][i] == 1) {
                right[r - 1][i] = right[r - 1][i + 1] + 1;
                down[r - 1][i] = 1;
            }
        }
        // 填充剩余位置
        for (int i = r - 2; i != -1; i--) {
            for (int j = c - 2; j != -1; j--) {
                if (m[i][j] == 1) {
                    right[i][j] = right[i][j + 1] + 1;
                    down[i][j] = down[i + 1][j] + 1;
                }
            }
        }
    }


}
