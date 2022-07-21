package org.example.sort;

public class BucketSort {


    public static void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        int maxBits = getMaxBits(arr);
        int[] count = new int[10];

        for (int i = 0; i < maxBits; i++) {
            for (int j = 0; j < arr.length; j++) {
                int divided = (int) Math.pow(10, i);
                int mod = (arr[j] / divided) % 10;
                count[mod]++;
            }
            for (int j = 1; j < 10; j++) {
                count[j] = count[j] + count[j - 1];
            }

            int[] tmp = new int[arr.length];

            for (int j = arr.length - 1; j >= 0; j--) {
                int divided = (int) Math.pow(10, i);
                int mod = (arr[j] / divided) % 10;
                tmp[count[mod] - 1] = arr[j];
                count[mod]--;
            }

            for (int j = 0; j < arr.length; j++) {
                arr[j] = tmp[j];
            }

            count = new int[10];


        }


    }

    private static int getMaxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            max = Math.max(num, max);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }


}
