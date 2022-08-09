package org.example.arr.binarysearch;

/**
 * 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 * 每行中的整数从左到右按升序排列。
 * 每行的第一个整数大于前一行的最后一个整数。
 */
public class SearchMatrix {


    public static boolean searchMatrix(int[][] matrix, int target) {
        int startRow = 0;
        int endRow = matrix.length - 1;
        int targetRow = -1;
        while (startRow < endRow) {
            int mid = (startRow + endRow) / 2;
            if (matrix[mid][matrix[0].length - 1] == target) {
                targetRow = mid;
                break;
            }

            if (matrix[mid][matrix[0].length - 1] < target) {
                startRow = mid + 1;
            } else {
                endRow = mid;
            }
        }

        if (targetRow == -1) {
            targetRow = startRow;
        }

        if (matrix[targetRow][matrix[0].length - 1] < target) {
            return false;
        }

        int left = 0;
        int right = matrix[0].length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (matrix[targetRow][mid] == target) {
                return true;
            }

            if (matrix[targetRow][mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return matrix[targetRow][left] == target;
    }
}
