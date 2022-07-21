package org.example.arr;

/**
 * 给定一个double类型的数组，返回子数组累乘的最大乘积
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class MaxProduct {

    // 求出以每一个位置结尾的子数组最大的累乘积，选出最大的那个
    // 关键在于如何快速求出以每一个位置结尾的子数组最大乘积
    // 假设以arr[i-1]结尾的最小累乘积为min，最大累乘积为max，那么以arr[i]结尾的最大累乘积有以下三种可能：
    //  a.可能是max*arr[i]（比如max和arr[i]都为正数）
    //  b.可能是min*arr[i]（比如min和arr[i]都为负数）
    //  c.可能仅是arr[i]（比如max和min均为大于0小于1的数）
    // 将以上三个值最大的作为arr[i]的max，最小的作为arr[i]的min，以此类推
    public double maxProduct(double[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        double max = arr[0];
        double min = arr[0];
        double res = arr[0];
        double maxEnd;
        double minEnd;
        for (int i = 1; i < arr.length; i++) {
            maxEnd = max * arr[i];
            minEnd = min * arr[i];
            max = Math.max(Math.max(maxEnd, minEnd), arr[i]);
            min = Math.min(Math.min(maxEnd, minEnd), arr[i]);
            res = Math.max(res, max);
        }
        return res;
    }
}
