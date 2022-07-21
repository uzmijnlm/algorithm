package org.example.arr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 给定一个整型数组，找到其中出现次数大于一半的数，如果没有则打印提示信息
 * 要求：时间复杂度O(N)，额外空间复杂度O(1)
 */
public class HalfMajor {

    // 核心思路：一次在数组中删掉K个不同的数，不停地删除，直到剩下数的种类不足K就停止删除
    // 如果一个数在数组中出现的次数大于N/K，则这个数最后一定会被剩下来
    // 如果是找到出现次数大于一半的数，则K=2
    // 具体实现方式很有意思：
    // 1.创建两个变量，cand表示候选，times表示这个候选出现的次数（可以想像成候选的血量）
    // 2.times等于0时说明没有候选，则把当前值当成候选，同时times加1
    // 3.遇到与当前候选不同值时，与候选发生一次相互抵消，times减1
    // 4.直到times为0时，回到一开始的情况
    public void printHalfMajor(int[] arr) {
        int cand = 0;
        int times = 0;

        // 这个循环中已经完成了"每次删除两个元素"这个过程，并且已经选出了候选值
        for (int i = 0; i != arr.length; i++) {
            if (times == 0) {
                cand = arr[i];
                times = 1;
            } else if (arr[i] == cand) {
                times++;
            } else {
                times--;
            }
        }

        // 再次从头到尾遍历一次数组，统计候选值出现次数
        times = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == cand) {
                times++;
            }
        }
        if (times > arr.length / 2) {
            System.out.println(cand);
        } else {
            System.out.println("no such number.");
        }
    }


    // 进阶问题：给定一个数组，再给定一个整数K，找出所有出现次数大于N/K的数，如果没有则打印提示信息
    // 要求：时间复杂度O(N*K)，额外空间复杂度O(K)
    // 具体实现方式是用map来维护cands，map大小没有到K-1时，就往里放候选值，如果达到了K-1，则每遇到不同值就集体减1
    public void printKMajor(int[] arr, int K) {
        if (K < 2) {
            System.out.println("invalid");
            return;
        }

        // 这个循环中已经完成了"每次删除K个元素"这个过程，并且已经选出了候选值
        Map<Integer, Integer> cands = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (cands.containsKey(arr[i])) {
                cands.put(arr[i], cands.get(arr[i]) + 1);
            } else {
                if (cands.size() == K - 1) {
                    allCandsMinusOne(cands);
                } else {
                    cands.put(arr[i], 1);
                }
            }
        }

        // 统计次数
        Map<Integer, Integer> reals = getReals(arr, cands);

        boolean hasPrint = false;
        for (Map.Entry<Integer, Integer> entry : cands.entrySet()) {
            Integer key = entry.getKey();
            if (reals.get(key) > arr.length / K) {
                hasPrint = true;
                System.out.println(key + " ");
            }
        }
        System.out.println(hasPrint ? "" : "no such number.");
    }

    private void allCandsMinusOne(Map<Integer, Integer> cands) {
        List<Integer> removeList = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : cands.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            cands.put(key, value - 1);
        }
        for (Integer key : removeList) {
            cands.remove(key);
        }
    }

    public Map<Integer, Integer> getReals(int[] arr, Map<Integer, Integer> cands) {
        Map<Integer, Integer> reals = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int curNum = arr[i];
            if (cands.containsKey(curNum)) {
                if (reals.containsKey(curNum)) {
                    reals.put(curNum, reals.get(curNum) + 1);
                } else {
                    reals.put(curNum, 1);
                }
            }
        }
        return reals;
    }

}
