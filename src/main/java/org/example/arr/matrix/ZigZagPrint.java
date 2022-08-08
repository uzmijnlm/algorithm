package org.example.arr.matrix;

/**
 * 给定一个矩阵，按zigzag方式打印
 * 例如：
 * 1  2  3  4
 * 5  6  7  8
 * 9 10 11 12
 * 打印结果为：1 2 5 9 9 6 3 4 7 10 11 8 12
 */
public class ZigZagPrint {


    // 步骤如下：
    // 1.(tR, rC)初始为(0, 0)，先沿着矩阵第一行移动，当到达第一行最右边的元素后，再沿着矩阵最后一列移动
    // 2.(dR, dC)初始为(0, 0)，先沿着矩阵第一列移动，当到达第一列最下边的元素后，再沿着矩阵最后一行移动
    // 3.坐标是同时移动的，每次移动后两个坐标的连线就是矩阵中一条斜线，打印斜线上的元素即可，来回变换打印顺序
    public void printMatrixZigZag(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = 0;
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean flag = false;
        while (tR != endR + 1) {
            printLevel(matrix, tR, tC, dR, dC, flag);
            tR = tC == endC ? tR + 1 : tR;
            tC = tC == endC ? tC : tC + 1;
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            flag = !flag;
        }
    }

    private void printLevel(int[][] matrix, int tR, int tC, int dR, int dC, boolean flag) {
        if (flag) {
            while (tR != dR + 1) {
                System.out.println(matrix[tR++][tC--] + " ");
            }
        } else {
            while (dR != tR - 1) {
                System.out.println(matrix[dR--][dC++] + " ");
            }
        }
    }
}

