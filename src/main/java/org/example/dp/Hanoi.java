package org.example.dp;

/**
 * 给定一个整数n，代表n个圆盘，假设开始时所有圆盘都放在左边的柱子上，想按照汉诺塔游戏的要求把所有的圆盘都移到右边的柱子上。打印最优移动轨迹
 */
public class Hanoi {


    // 假设有from柱子、mid柱子和to柱子，都从from的圆盘1~i完全完全移动到to，最优过程为：
    // 1.为圆盘1~i-1从from移动到mid
    // 2.为单独把圆盘i从from移动到to
    // 3.为把圆盘1~i-1从mid移动到to。如果圆盘只有1个，直接把这个圆盘从from移动到to即可
    // 直接用递归函数打印这个过程
    public void hanoi(int n) {
        if (n > 0) {
            func(n, "left", "mid", "right");
        }
    }

    /**
     * 将圆盘1~n按照上小下大地整体从from移动到to，other表示剩下那个柱子
     *
     * @param n     操作圆盘1~n
     * @param from  将圆盘从from移动
     * @param other 除了from和to的另一个柱子
     * @param to    将圆盘移动到to
     */
    private void func(int n, String from, String other, String to) {
        if (n == 1) {
            System.out.println("move from " + from + " to " + to);
        } else {
            func(n - 1, from, to, other); // 步骤1
            func(1, from, other, to); // 步骤2
            func(n - 1, other, from, to); // 步骤3
        }
    }


    // 进阶问题：给定一个整型数组arr，其中只含有1、2和3，代表所有圆盘目前的状态，1代表左柱，2代表中柱，3代表右柱
    // arr[i]代表第i+1个圆盘的位置。如果arr代表的状态是最优移动轨迹过程中出现的状态，返回arr这种状态是第几个状态，如果不是则返回-1
    // 要求：时间复杂度为O(N)，额外空间复杂度为O(1)
    // 对于圆盘1~i来说，如果目标为从from到to，那么情况有三种：
    // a.圆盘i在from上，需要继续考察圆盘1~i-1的状况，圆盘1~i-1的目标为从from到other
    // b.圆盘i在to上，说明起码走完了2^i-1步，剩下的步骤怎么确定还得继续考察圆盘1~i-1的状况，圆盘1~i-1的目标为从other到to
    // c.圆盘i在other上，直接返回-1
    // 解释：在每一步中，无论from、other、to代表左中右的哪一个，对于这一步要操作的圆盘1~i来说，圆盘i要么在from要么在to，否则返回-1
    //      因为最优过程就是把1~i从from移动到to
    public int step(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        // 一开始将from、other、to分别设置为左中右
        int from = 1;
        int other = 2;
        int to = 3;

        int i = arr.length - 1;
        int res = 0;
        int tmp = 0;
        while (i >= 0) {
            if (arr[i] != from && arr[i] != to) {
                return -1; // 情况c
            }
            if (arr[i] == to) { // 情况b
                res += 1 << i; // 已经走完了2^i-1步
                tmp = from;
                from = other;
            } else { // 情况a
                tmp = to;
                to = other;
            }
            other = tmp;
            i--;
        }
        return res;
    }
}
