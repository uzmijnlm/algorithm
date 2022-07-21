package org.example.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class MergeSortTest extends BaseTest {

    @Test
    public void test() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 100);
            int[] arr1 = copyArr(arr);
            int[] arr2 = copyArr(arr);
            Arrays.sort(arr1);
            MergeSort.sort(arr2);
            for (int j = 0; j < arr.length; j++) {
                assert arr1[j] == arr2[j];
            }
        }
    }
}
