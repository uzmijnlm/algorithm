package org.example.arr;

/**
 * 给定一个无序整型数组，找到其中最小的k个数
 */
public class KMin {


    public static int[] getKMin(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return null;
        }

        int[] heap = new int[k];
        for (int i = 0; i < k; i++) {
            heap[i] = nums[i];
            heapInsert(heap, i);
        }

        for (int i = k; i < nums.length; i++) {
            if (nums[i] < heap[0]) {
                heap[0] = nums[i];
                heapify(heap, 0, heap.length);
            }
        }

        return heap;
    }

    private static void heapInsert(int[] arr, int index) {
        while (arr[(index - 1) / 2] < arr[index]) {
            swap(arr, (index - 1) / 2, index);
            index = (index - 1) / 2;
        }
    }

    private static void heapify(int[] arr, int index, int size) {
        while (index * 2 + 1 < size) {
            int biggerIndex;
            if (index * 2 + 2 < size && arr[index * 2 + 2] > arr[index * 2 + 1]) {
                biggerIndex = index*2+2;
            } else {
                biggerIndex = index*2+1;
            }

            if (arr[biggerIndex] > arr[index]) {
                swap(arr, biggerIndex, index);
                index = biggerIndex;
            } else {
                break;
            }
        }
    }

    private static void swap(int[] arr, int index1, int index2) {
        int tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

}
