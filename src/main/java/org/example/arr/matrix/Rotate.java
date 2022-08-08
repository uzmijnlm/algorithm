package org.example.arr.matrix;

/**
 * 给定一个N*N的矩阵，将其顺时针旋转90度
 * 要求：额外空间复杂度O(1)
 */
public class Rotate {

    // 分圈处理
    public void rotate(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR < dR) {
            rotateEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    private void rotateEdge(int[][] matrix, int tR, int tC, int dR, int dC) {
        int times = dC - tC; // 总的组数（一组一组调整）
        int tmp;
        for (int i = 0; i != times; i++) {
            tmp = matrix[tR][tC+i];
            matrix[tR][tC+i] = matrix[dR-i][tC];
            matrix[dR-i][tC] = matrix[dR][dC-i];
            matrix[dR][dC-i] = matrix[tR+i][dC];
            matrix[tR+i][dC] = tmp;
        }
    }


}
