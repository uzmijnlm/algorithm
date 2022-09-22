package org.example.dp;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个数组，其中可能有正、有负、有零
 * 将数组任意切分，求异或和为0的子数组最多能有多少个
 * 要求时间复杂度O(N)
 */
public class MostEOR {


    // 核心思路：用一个map维护上一次出现某个异或和的位置
    // 用dp[i]表示在arr[0...i]上做分割，异或和为0的子数组最多有几个
    public int mostEOR(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int eor = 0; // 从1位置开始到i位置的异或和
        int[] dp = new int[arr.length];
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
            if (map.containsKey(eor)) {
                int preEorIndex = map.get(eor);
                dp[i] = preEorIndex == -1 ? 1 : (dp[preEorIndex] + 1);
            }
            if (i > 0) {
                dp[i] = Math.max(dp[i - 1], dp[i]);
            }
            map.put(eor, i);
        }
        return dp[dp.length - 1];
    }


}
