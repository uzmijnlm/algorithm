package org.example.arr;

import java.util.*;

public class BaseTest {

    protected int[] generateRandomArr(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;
        int max = random.nextInt(maxBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = random.nextInt(max);
        }
        return arr;
    }

    protected int[] generateSortedArr(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;
        int max = random.nextInt(maxBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = random.nextInt(max);
        }

        Arrays.sort(arr);
        return arr;
    }

    protected int[] generateSortedArrWithoutDup(int lengthBound, int maxBound) {
        int step = maxBound / lengthBound;

        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            if (j == 0) {
                arr[j] = random.nextInt(step) + 1;
            } else {
                arr[j] = arr[j - 1] + random.nextInt(step) + 1;
            }
        }
        return arr;
    }

    protected int[] generateFixedLengthSortedArr(int length, int maxBound) {
        int max = new Random(System.nanoTime()).nextInt(maxBound) + 1;
        int[] arr = new int[length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Random(System.nanoTime()).nextInt(max);
        }
        Arrays.sort(arr);
        return arr;
    }

    protected int[] copyArr(int[] arr) {
        int[] arr1 = new int[arr.length];
        System.arraycopy(arr, 0, arr1, 0, arr.length);
        return arr1;
    }

    protected void shuffle(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int num : arr) {
            list.add(num);
        }
        Collections.shuffle(list, new Random(System.nanoTime()));
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
    }

    protected int[] shift(int[] arr, int shift) {
        shift = shift % arr.length;
        int[] newArr = new int[arr.length];
        int index = shift;
        for (int i = 0; i < newArr.length; i++) {
            if (index < arr.length) {
                newArr[i] = arr[index++];
            } else {
                newArr[i] = arr[index++ - arr.length];
            }
        }
        return newArr;
    }

    protected int[][] generateRandomMatrix(int rowBound, int colBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int row = random.nextInt(rowBound) + 1;
        int col = random.nextInt(colBound) + 1;
        int max = random.nextInt(maxBound) + 1;

        int[][] arr = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                arr[i][j] = random.nextInt(max);
            }
        }
        return arr;
    }

    protected int[][] generateSortedMatrix(int rowBound, int colBound, int maxBound) {
        int row = new Random(System.nanoTime()).nextInt(rowBound) + 1;
        int col = new Random(System.nanoTime()).nextInt(colBound) + 1;
        int step = maxBound / (rowBound * colBound);

        int[][] matrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            if (i == 0) {
                for (int j = 0; j < col; j++) {
                    if (j == 0) {
                        matrix[i][j] = new Random(System.nanoTime()).nextInt(step);
                    } else {
                        matrix[i][j] = matrix[i][j - 1] + new Random(System.nanoTime()).nextInt(step);
                    }
                }
            } else {
                for (int j = 0; j < col; j++) {
                    if (j == 0) {
                        matrix[i][j] = matrix[i - 1][col - 1] + new Random(System.nanoTime()).nextInt(step);
                    } else {
                        matrix[i][j] = matrix[i][j - 1] + new Random(System.nanoTime()).nextInt(step);
                    }
                }
            }
        }
        return matrix;
    }

    protected int[][] generateRowColSortedMatrix(int rowBound, int colBound, int maxBound) {
        int row = new Random(System.nanoTime()).nextInt(rowBound) + 1;
        int col = new Random(System.nanoTime()).nextInt(colBound) + 1;
        int step = maxBound / (rowBound * colBound);

        int[][] matrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            if (i == 0) {
                for (int j = 0; j < col; j++) {
                    if (j == 0) {
                        matrix[i][j] = new Random(System.nanoTime()).nextInt(step);
                    } else {
                        matrix[i][j] = matrix[i][j - 1] + new Random(System.nanoTime()).nextInt(step);
                    }
                }
            } else {
                for (int j = 0; j < col; j++) {
                    if (j == 0) {
                        matrix[i][j] = matrix[i - 1][j] + new Random(System.nanoTime()).nextInt(step);
                    } else {
                        matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]) + new Random(System.nanoTime()).nextInt(step);
                    }
                }
            }
        }
        return matrix;
    }


    protected int[][] copyMatrix(int[][] matrix) {
        int[][] copiedMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copiedMatrix[i], 0, matrix[0].length);
        }
        return copiedMatrix;
    }
}
