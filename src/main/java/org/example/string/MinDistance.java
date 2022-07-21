package org.example.string;

/**
 * 给定一个字符串数组strs，再给定两个字符串str1和str2，返回strs中str1和str2的最小距离
 * 如果str1或str2为null，或不在strs中，返回-1
 */
public class MinDistance {


    public int minDistance(String[] strs, String str1, String str2) {
        if (str1 == null || str2 == null) {
            return -1;
        }

        if (str1.equals(str2)) {
            return 0;
        }

        int last1 = -1; // 记录最近一次出现str1的位置
        int last2 = -1; // 记录最近一次出现str2的位置

        int min = Integer.MAX_VALUE; // 记录最小距离
        for (int i = 0; i != strs.length; i++) {
            if (strs[i].equals(str1)) { // 如果遍历到的字符串等于str1，则i-last2就是当前str1和左边距离它最近的str2的距离
                if (last2 != -1) {
                    min = Math.min(min, i - last2);
                }
                last1 = i;
            }
            if (strs[i].equals(str2)) { // 如果遍历到的字符串等于str2，则i-last1就是当前str2和左边距离它最近的str1的距离
                if (last1 != -1) {
                    min = Math.min(min, i - last1);
                }
                last2 = i;
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }


}
