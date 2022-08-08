package org.example.arr.matrix;

/**
 * 给定一个整型矩阵，按照转圈的方式打印它
 * 例如：
 * 1   2   3   4
 * 5   6   7   8
 * 9   10  11  12
 * 13  14  15  16
 * 打印结果为：1 2 3 4 8 12 16 15 14 13 9 5 6 7 11 10
 * 要求：额外空间复杂度O(1)
 */
public class SpiralOrderPrint {


    // 难点在于设计一种易于理解的遍历方式。一种常见思路是分圈处理
    // 用左上角坐标(tR, rC)和右下角坐标(dR, dC)表示一个子矩阵，从外圈打印到内圈
    public void spiralOrderPrint(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    private void printEdge(int[][] matrix, int tR, int tC, int dR, int dC) {
        if (tR == dR) { // 子矩阵只有一行
            for (int i = tC; i <= dC; i++) {
                System.out.println(matrix[tR][i] + " ");
            }
        } else if (tC == dC) { // 子矩阵只有一列
            for (int i = tR; i <= dR; i++) {
                System.out.println(matrix[i][tC] + " ");
            }
        } else {
            int curC = tC;
            int curR = tR;
            while (curC != dC) {
                System.out.println(matrix[tR][curC] + " ");
                curC++;
            }
            while (curR != dR) {
                System.out.println(matrix[curR][dC] + " ");
                curR++;
            }
            while (curC != tC) {
                System.out.println(matrix[dR][curC] + " ");
                curC--;
            }
            while (curR != tR) {
                System.out.println(matrix[curR][tC] + " ");
                curR--;
            }
        }
    }
}
