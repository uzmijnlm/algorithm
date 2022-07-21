package org.example.dp;

/**
 * 给定数组arr，返回arr的最长递增子序列
 * 要求：时间复杂度O(NlogN)
 */
public class LIS {


    // 二分查找
    public int[] lis(int[] arr) {
        if (arr == null | arr.length == 0) {
            return null;
        }

        int[] dp = getDP(arr);
        return generateLIS(arr, dp);
    }

    private int[] generateLIS(int[] arr, int[] dp) {
        int len = 0;
        int index = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] > len) {
                len = dp[i];
                index = i;
            }
        }
        int[] lis = new int[len];
        lis[--len] = arr[index];
        for (int i = index; i >= 0; i--) {
            if (arr[i] < arr[index] && dp[i] == dp[index] - 1) {
                lis[--len] = arr[i];
                index = i;
            }
        }
        return lis;
    }

    // dp[i]的含义是，子序列必须以arr[i]结尾的情况下，最长递增子序列的长度。dp[0]=1
    // 时间复杂度O(N^2)的做法是从左到右遍历，遍历到每一位时，去它前面看比它小的数中哪一个dp[j]最大，它的值就是dp[i]=dp[j]+1
    // 用二分查找可以加速。增加一个ends数组。一开始ends数组都在无效区
    // 如果ends[i]是有效区位置，它表示所有长度为i+1的递增子序列中最小结尾值是多少
    // 更新ends的步骤如下：
    // 1.遍历arr时，在ends数组有效区中找大于arr[i]最左的位置，用arr[i]的值替换它
    // 2.看这个数在ends数组中左侧有多少个数，就是dp[i]的值
    private int[] getDP(int[] arr) {
        int[] dp = new int[arr.length];
        int[] ends = new int[arr.length];
        ends[0] = arr[0];
        dp[0] = 1;
        int rightBorder = 0; // 表示有效区的右边界

        int left, right, mid;
        for (int i = 1; i < arr.length; i++) {
            // 在ends数组有效区中找到大于arr[i]最左的位置。最终的left就是那个位置
            left = 0;
            right = rightBorder;
            while (left <= right) {
                mid = (left + right) / 2;
                if (arr[i] > ends[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            // 更新ends及右边界
            rightBorder = Math.max(rightBorder, left);
            ends[left] = arr[i];

            // 更新dp
            dp[i] = left + 1;
        }
        return dp;
    }


}
