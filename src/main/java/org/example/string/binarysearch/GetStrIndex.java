package org.example.string.binarysearch;

/**
 * 给定一个字符串数组strs[]，其中有些位置是null，但在不为null的位置是有序的
 * 给定一个字符串str，返回str在数组中出现的最左的位置，不存在或str为null时返回-1
 */
public class GetStrIndex {

    // 二分查找
    public static int getIndex(String[] strs, String str) {
        if (strs == null || strs.length == 0 || str == null) {
            return -1;
        }

        int res = -1;
        int left = 0;
        int right = strs.length - 1;
        int mid = 0;
        int i = 0;
        while (left < right) {
            mid = left + (right - left) / 2;
            if (strs[mid] != null && strs[mid].equals(str)) { // 情况1：中间的字符串刚好与结果相等，则记录这个位置，并继续在左半区找
                res = mid;
                right = mid;
            } else if (strs[mid] != null) { // 情况2：中间的字符串与结果不等，则继续在左半区或右半区找
                if (strs[mid].compareTo(str) < 0) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            } else { // 情况3：中间的元素为null，则先向左找到第一个不为null的元素
                i = mid;
                while (true) {
                    if (i < 0 || i < left) {
                        break;
                    }
                    if (strs[i] != null) {
                        break;
                    }
                    i--;
                }
                if (i < left || strs[i].compareTo(str) < 0) { // 情况3.1：找到的第一个不为null的元素小于结果，或越界，则去右半区找
                    left = mid + 1;
                } else { // 情况3.2：如果找到的元素与结果相等，则记录这个位置，否则不记录。然后去左半区继续找
                    res = strs[i].equals(str) ? i : res;
                    right = i;
                }
            }
        }

        if (strs[left] != null && strs[left].equals(str)) {
            return left;
        }
        return res;
    }

}
