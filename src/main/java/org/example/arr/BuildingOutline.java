package org.example.arr;

import java.util.*;

/**
 * 给定一个N*3的矩阵matrix表示大楼轮廓，如：
 * matrix = {
 *     {2,5,6},
 *     {1,7,4},
 *     {4,6,7},
 *     {3,6,5},
 *     {10,13,2},
 *     {9,11,3},
 *     {12,14,4},
 *     {10,12,5}
 * }
 * 其中每个小数组如{2,5,6}，arr[0]=2表示大楼左边界，arr[1]=5表示大楼右边界，arr[2]=6表示大楼高度
 * 大楼都在第一象限，高度一定大于0，大楼之间会有重叠
 * 请返回整体轮廓线
 * 例如对于上面的大楼，轮廓线为：
 * {
 *     {1,2,4},
 *     {2,4,6},
 *     {4,6,7},
 *     {6,7,4},
 *     {9,10,3},
 *     {10,12,5},
 *     {12,14,4}
 * }
 */
public class BuildingOutline {

    static class Node {
        public int x;
        public boolean isAdd;
        public int h;

        public Node(int x, boolean isAdd, int h) {
            this.x = x;
            this.isAdd = isAdd;
            this.h = h;
        }
    }

    static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.x != o2.x) {
                return o1.x - o2.x;
            }
            // 必须将升高放前面，防止(7,7,9)这种大楼，导致降低在升高前面
            if (o1.isAdd != o2.isAdd) {
                return o1.isAdd ? -1 : 1;
            }
            return 0;
        }
    }


    // 核心思路：
    // 1.先对每个楼的轮廓生成两个Node对象，表示高度变化，对这些所有对象进行排序
    // 2.维护两个有序表，map1是高度次数表，key表示高度，value表示出现次数；map2是坐标高度表，key表示坐标，value表示这个坐标最大高度
    // 3.遍历坐标高度表直接得到结果
    public List<List<Integer>> buildingOutline(int[][] matrix) {
        Node[] nodes = new Node[matrix.length * 2];
        // 每一个大楼轮廓数组产生两个描述高度变化的对象
        for (int i = 0; i < matrix.length; i++) {
            nodes[i * 2] = new Node(matrix[i][0], true, matrix[i][2]); // 在matrix[i][0]处升高
            nodes[i * 2 + 1] = new Node(matrix[i][1], false, matrix[i][2]); // 在matrix[i][1]处降低
        }
        // 排序该对象
        Arrays.sort(nodes, new NodeComparator());

        TreeMap<Integer, Integer> mapHeightTimes = new TreeMap<>();
        Map<Integer, Integer> mapXvalueHeight = new TreeMap<>();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].isAdd) {
                if (!mapHeightTimes.containsKey(nodes[i].h)) {
                    mapHeightTimes.put(nodes[i].h, 1);
                } else {
                    mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) + 1);
                }
            } else {
                if (mapHeightTimes.get(nodes[i].h) == 1) {
                    mapHeightTimes.remove(nodes[i].h);
                } else {
                    mapHeightTimes.put(nodes[i].h, mapHeightTimes.get(nodes[i].h) - 1);
                }
            }

            if (mapHeightTimes.isEmpty()) {
                mapXvalueHeight.put(nodes[i].x, 0);
            } else {
                mapXvalueHeight.put(nodes[i].x, mapHeightTimes.lastKey()); // lastKey()方法返回最大的高度
            }
        }

        // 利用坐标高度表直接得到结果
        List<List<Integer>> res = new ArrayList<>();
        int start = 0;
        int preHeight = 0;
        for (Map.Entry<Integer, Integer> entry : mapXvalueHeight.entrySet()) {
            int curX = entry.getKey();
            int curMaxHeight = entry.getValue();
            if (preHeight != curMaxHeight) {
                if (preHeight != 0) {
                    res.add(new ArrayList<>(Arrays.asList(start, curX, preHeight)));
                }
                start = curX;
                preHeight = curMaxHeight;
            }
        }
        return res;
    }

}
