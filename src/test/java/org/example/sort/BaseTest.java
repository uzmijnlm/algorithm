package org.example.sort;

import java.util.Random;

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

    protected int[] copyArr(int[] arr) {
        int[] arr1 = new int[arr.length];

        for (int j = 0; j < arr.length; j++) {
            arr1[j] = arr[j];
        }
        return arr1;
    }
}
