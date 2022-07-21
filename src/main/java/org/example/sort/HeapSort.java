package org.example.sort;

import static org.example.sort.CommonUtil.swap;

public class HeapSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }

        for (int i = arr.length-1; i >=0 ; i--) {
            swap(arr, 0, i);
            heapify(arr, 0, i);
        }

    }

    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index-1)/2]) {
            swap(arr, index, (index-1)/2);
            index = (index-1)/2;
        }
    }

    private static void heapify(int[] arr, int index, int size) {
        while (index * 2 +1<size) {
            int biggerIndex;
            if (index*2+2<size && arr[index*2+2] > arr[index*2+1]) {
                biggerIndex = index*2+2;
            } else {
                biggerIndex = index*2+1;
            }

            if(arr[index] < arr[biggerIndex]) {
                swap(arr, index, biggerIndex);
                index = biggerIndex;
            } else {
                break;
            }
        }
    }





}
