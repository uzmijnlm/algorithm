package org.example.arr;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 用一个N*M的整型矩阵表示一个网络，1代表有路，0代表无路，每一个位置只要不越界，都有上下左右4个方向
 * 求从最左上角到最右下角的最短通路值
 * 要求：时间复杂度O(N*M)，额外空间复杂度O(N*M)
 */
public class MinPathValue {

    // 宽度优先遍历，步骤如下：
    // 1.生成map矩阵，map[i][j]表示从(0,0)位置走到(i,j)位置最短的路径值，然后将左上角位置(0,0)的行坐标与列坐标放入行队列rQ和列队列cQ
    // 2.不断从队列弹出一个位置(r,c)，然后看这个位置上下左右4个方向哪些在matrix上的值是1
    // 3.将那些能走的位置设置好格子在map中的值，即map[r][c]+1。同时将这些位置加入rQ和cQ
    // 4.重复以上步骤走到终点，注意不要重复走。如果走不到终点，说明没有通路，返回0
    public int minPathValue(int[][] m) {
        if (m == null || m.length == 0 || m[0].length == 0 || m[0][0] != 1 || m[m.length - 1][m[0].length - 1] != 1) {
            return 0;
        }

        int[][] map = new int[m.length][m[0].length];
        map[0][0] = 1;
        Queue<Integer> rQ = new LinkedList<>();
        Queue<Integer> cQ = new LinkedList<>();
        rQ.add(0);
        cQ.add(0);
        int r;
        int c;
        while (!rQ.isEmpty()) {
            r = rQ.poll();
            c = cQ.poll();
            if (r == m.length - 1 && c == m[0].length - 1) {
                return map[r][c];
            }
            walkTo(map[r][c], r - 1, c, m, map, rQ, cQ);
            walkTo(map[r][c], r + 1, c, m, map, rQ, cQ);
            walkTo(map[r][c], r, c - 1, m, map, rQ, cQ);
            walkTo(map[r][c], r, c + 1, m, map, rQ, cQ);
        }
        return 0;
    }

    private void walkTo(int pre, int toR, int toC, int[][] m, int[][] map, Queue<Integer> rQ, Queue<Integer> cQ) {
        if (toR < 0 || toR == m.length || toC < 0 || toC == m[0].length // 越界
                || m[toR][toC] != 1 // 不是通路
                || map[toR][toC] != 0 // 已经走过
        ) {
            return;
        }
        map[toR][toC] = pre + 1;
        rQ.add(toR);
        cQ.add(toC);
    }
}
