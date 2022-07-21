package org.example.other;

import java.util.HashSet;
import java.util.Set;

/**
 * 每条边不是平行于x轴就是平行于y轴的矩形，可以用左下角点和右上角点来表示，比如{1,2,3,4}表示(1,2)和(3,4)点组成的矩形
 * 给定一个N行4列的二维数组，表示N个每条边不是平行于x轴就是平行于y轴的矩形
 * 想知道所有的矩形能否组成一个大的完美矩形
 * 完美矩形是指拼出的整体图案是矩形，既不缺任何块儿，也没有重合的部分
 */
public class RectangleCover {

    // 需要同时满足两个标准
    // 标准1：面积能对上
    // 标准2：除大矩形的4个顶点只出现1次之外，其他任何小矩形的顶点都必须出现偶数次
    public boolean isRectangleCover(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int mostLeft = Integer.MAX_VALUE;
        int mostRight = Integer.MIN_VALUE;
        int mostDown = Integer.MAX_VALUE;
        int mostUp = Integer.MIN_VALUE;
        Set<String> set = new HashSet<>();
        int area = 0;
        for (int[] rect : matrix) {
            mostLeft = Math.min(rect[0], mostLeft);
            mostDown = Math.min(rect[1], mostDown);
            mostRight = Math.max(rect[2], mostRight);
            mostUp = Math.max(rect[3], mostUp);
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
            // 4个顶点
            String leftDown = rect[0] + "_" + rect[1];
            String leftUp = rect[0] + "_" + rect[3];
            String rightDown = rect[2] + "_" + rect[1];
            String rightUp = rect[2] + "_" + rect[3];
            // 遍历完以后，应当是有且仅有4个顶点都在set中
            if (!set.add(leftDown)) {
                set.remove(leftDown);
            }
            if (!set.add(leftUp)) {
                set.remove(leftUp);
            }
            if (!set.add(rightUp)) {
                set.remove(rightUp);
            }
            if (!set.add(rightDown)) {
                set.remove(rightDown);
            }
        }
        if (!set.contains(mostLeft + "_" + mostDown)
                || !set.contains(mostLeft + "_" + mostUp)
                || !set.contains(mostRight + "_" + mostDown)
                || !set.contains(mostRight + "_" + mostUp)
                || set.size() != 4) {
            return false;
        }
        return area == (mostRight - mostLeft) * (mostUp - mostDown);
    }

}
