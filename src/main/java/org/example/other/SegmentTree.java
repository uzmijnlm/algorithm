package org.example.other;

/**
 * 实现一个数据结构，同时包含以下三个方法：
 * void add(L, R, V, arr)：arr[L...R]范围上每个数加V
 * void update(L, R, V, arr)：arr[L...R]范围上每个数更新成V
 * int getSum(L, R, arr]：返回arr[L...R]累加和
 * 要求以上三个方法达到O(logN)
 */
public class SegmentTree {

    // 将数组长度补成2^n的长度。下标从1开始。比如长度为8，那么记录1~8的信息，记录1~4、5~8的信息，再这样二分下去
    // 若N=2^n，那么总共需要2*N-1的空间将所有二分信息装下。对于任意一个数N，准备4N的空间一定够用（N不一定是2^n）
    // 准备一个sum数组，0位置弃用，从左到右依次从顶部信息开始放
    // 例如：arr[3,2,5,7,]。1~4的和是17，它的左孩子是1~2范围，右孩子是3~4范围，和分别是5、12，以此类推
    //      sum数组为[0,17,5,12,3,2,5,7]，每一个位置i左孩子下标是2*i，右孩子是2*i+1


    private int MAX_N;
    private int[] arr;
    private int[] sum;
    private int[] lazy; // 累加懒惰标记
    private int[] change; // 为更新的值
    private boolean[] update; // 为更新懒惰标记 

    public SegmentTree(int[] origin) {
        MAX_N = origin.length + 1;
        arr = new int[MAX_N];
        for (int i = 1; i < MAX_N; i++) {
            arr[i] = origin[i - 1];
        }
        sum = new int[MAX_N << 2];
        lazy = new int[MAX_N << 2];
        change = new int[MAX_N << 2];
        update = new boolean[MAX_N << 2];
    }

    // 用左右孩子的信息累加中自身的值
    private void pushUp(int rt) {
        sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
    }


    /**
     * 在初始化阶段，先把sum数组填好
     *
     * @param left  在arr[left...right]范围上去build
     * @param right 在arr[left...right]范围上去build
     * @param rt    这个范围在sum中的位置
     */
    public void build(int left, int right, int rt) {
        if (left == right) {
            sum[rt] = arr[left];
            return;
        }
        int mid = (left + right) / 2;
        build(left, mid, rt << 1);
        build(mid + 1, right, rt << 1 | 1);
        pushUp(rt);
    }


    /**
     * L...R范围上加上C
     *
     * @param L  L...R 任务范围
     * @param R  L...R 任务范围
     * @param C  所有位置累加上C
     * @param l  l...r 表达的范围
     * @param r  l...r 表达的范围
     * @param rt l...r 表达的范围保存在lazy中的位置
     */
    public void add(int L, int R, int C, int l, int r, int rt) {
        // 任务的范围彻底覆盖了当前表达的范围
        if (L <= l && r <= R) {
            sum[rt] += C * (r - l + 1); // 更新累加和
            lazy[rt] += C; // 更新要加的数
            return;
        }

        // 要把任务下发。任务L,R没有把本身表达范围l,r彻底包住
        int mid = (l + r) / 2;
        pushDown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            add(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            add(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        pushUp(rt);
    }

    /**
     * 将之前懒住的任务下发
     *
     * @param rt
     * @param ln 左子树元素节点个数
     * @param rn 右子树元素节点个数
     */
    private void pushDown(int rt, int ln, int rn) {
        if (update[rt]) {
            update[rt << 1] = true;
            update[rt << 1 | 1] = true;
            change[rt << 1] = change[rt];
            change[rt << 1 | 1] = change[rt];
            lazy[rt << 1] = 0;
            lazy[rt << 1 | 1] = 0;
            sum[rt << 1] = change[rt] * ln;
            sum[rt << 1 | 1] = change[rt] * rn;
            update[rt] = false;
        }
        if (lazy[rt] != 0) { // 父节点lazy信息不为0，需要下发给左右孩子
            lazy[rt << 1] += lazy[rt];
            sum[rt << 1] += lazy[rt] * ln;
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1 | 1] += lazy[rt] * rn;
            lazy[rt] = 0;
        }
    }


    public void update(int L, int R, int C, int l, int r, int rt) {
        if (L <= l && r <= R) {
            update[rt] = true;
            change[rt] = C;
            sum[rt] = C * (r - l + 1);
            lazy[rt] = 0;
            return;
        }

        int mid = (l + r) / 2;
        pushDown(rt, mid - 1 + 1, r - mid);
        if (L <= mid) {
            update(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            update(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        pushUp(rt);
    }

    public long query(int L, int R, int l, int r, int rt) {
        if (L <= l && r <= R) {
            return sum[rt];
        }
        int mid = (l + r) / 2;
        pushDown(rt, mid - l + 1, r - mid);
        long ans = 0;
        if (L <= mid) {
            ans += query(L, R, l, mid, rt << 1);
        }
        if (R > mid) {
            ans += query(L, R, mid + 1, r, rt << 1 | 1);
        }
        return ans;
    }


}
