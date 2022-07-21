package org.example.arr;

/**
 * 给定一个N*M的整型矩阵matrix和一个整数K，matrix每一行每一列都是有序的，判断K是否在matrix中
 * 要求：时间复杂度O(N+M)，额外空间复杂度O(1)
 */
public class FindNumber {

    // 步骤如下：
    // 1.从矩阵最右上角的数开始找
    // 2.比较当前数matrix[row][col]与K的关系
    //   a.如果与K相等，返回true
    //   b.如果比K大，则没有必要在这一列上找，令col=col-1然后重复步骤2
    //   c.如果比K小，则没有必要在这一行上找，令row=row+1然后重复步骤2
    // 3.如果直到最后都没有找到，返回false
    public boolean isContains(int[][] matrix, int K) {
        int row = 0;
        int col = matrix[0].length - 1;
        while (row < matrix.length && col > -1) {
            if (matrix[row][col] == K) {
                return true;
            } else if (matrix[row][col] > K) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }


}
