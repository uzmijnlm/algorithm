package org.example.arr.binarysearch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给你一个区间数组 intervals ，其中 intervals[i] = [starti, endi] ，且每个 starti 都 不同 。
 * 区间 i 的 右侧区间 可以记作区间 j ，并满足 startj >= endi ，且 startj 最小化 。
 * 返回一个由每个区间 i 的 右侧区间 在 intervals 中对应下标组成的数组。如果某个区间 i 不存在对应的 右侧区间 ，则下标 i 处的值设为 -1 。
 */
public class FindRightInterval {


    public static int[] findRightInterval(int[][] intervals) {
        Map<Integer, Integer> value2Index = new HashMap<>();
        int[] starts = new int[intervals.length];
        for (int i = 0; i<intervals.length; i++) {
            starts[i] = intervals[i][0];
            value2Index.put(starts[i], i);
        }

        Arrays.sort(starts);

        int[] res = new int[intervals.length];
        for (int i = 0; i< intervals.length; i++) {
            int index = findHigherIndex(starts, intervals[i][1]);
            res[i] = index == -1 ? -1 : value2Index.get(starts[index]);
        }
        return res;
    }

    private static int findHigherIndex(int[] starts, int value) {
        if (value > starts[starts.length - 1]) {
            return -1;
        }
        int left = 0;
        int right = starts.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (starts[mid] < value) {
                left = mid + 1;
            } else if (starts[mid] > value){
                right = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
