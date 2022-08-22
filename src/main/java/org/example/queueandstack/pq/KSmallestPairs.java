package org.example.queueandstack.pq;

import java.util.*;

/**
 * 给定两个以 升序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。
 * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
 * 请找到和最小的 k 个数对 (u1,v1),  (u2,v2) ... (uk,vk) 。
 */
public class KSmallestPairs {


    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        Queue<Integer[]> pq = new PriorityQueue<>(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return nums1[o1[0]] + nums2[o1[1]] - (nums1[o2[0]] + nums2[o2[1]]);
            }
        });

        for (int i = 0; i < nums1.length; i++) {
            pq.add(new Integer[]{i, 0});
        }

        List<List<Integer>> ans = new ArrayList<>();
        while (k-- > 0 && !pq.isEmpty()) {
            Integer[] poll = pq.poll();
            ans.add(Arrays.asList(nums1[poll[0]], nums2[poll[1]]));

            if (poll[1] + 1 < nums2.length) {
                poll[1]++;
                pq.add(poll);
            }
        }
        return ans;
    }
}
