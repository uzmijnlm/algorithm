package org.example.string;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 给定一个字符串类型的数组strs
 * 请找到一种拼接顺序，使得将所有的字符串拼接起来组成的字符串是所有可能性中字典顺序最小的，并返回这个字符串
 */
public class LowestString {

    private static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    // 核心思路：用x.y表示两个字符串拼接的结果，如果a.b小于b.a，那么最终结果中a一定在b的前面。数学证明略
    // 整个过程实际就是排序的过程，时间复杂度为O(NlogN)
    public String lowestString(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        Arrays.sort(strs, new MyComparator());
        StringBuilder res = new StringBuilder();
        for (String str : strs) {
            res.append(str);
        }
        return res.toString();
    }


}
