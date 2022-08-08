package org.example.arr;

import java.util.*;

public class BaseTest {

    protected int[] generateRandomArr(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound);
        int max = random.nextInt(maxBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = random.nextInt(max);
        }
        return arr;
    }

    protected int[] generateSortedArr(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound);
        int max = random.nextInt(maxBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = random.nextInt(max);
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

    protected int[][] copyMatrix(int[][] matrix) {
        int[][] copiedMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, copiedMatrix[i], 0, matrix[0].length);
        }
        return copiedMatrix;
    }
}
