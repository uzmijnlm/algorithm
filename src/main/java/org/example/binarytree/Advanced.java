package org.example.binarytree;

import java.util.*;

public class Advanced {

    // 用前序遍历进行序列化
    public static String serialize(Node node) {
        if (node == null) {
            return "#_";
        }

        String str = node.value + "_";
        str += serialize(node.left);
        str += serialize(node.right);
        return str;
    }


    // 用前序遍历进行反序列化
    public static Node deSerialize(String str) {
        String[] splits = str.split("_");
        Queue<String> queue = new LinkedList<>(Arrays.asList(splits));
        return deSerialize(queue);
    }

    private static Node deSerialize(Queue<String> queue) {
        if (queue.isEmpty()) {
            return null;
        }

        String str = queue.poll();
        if ("#".equals(str)) {
            return null;
        }
        Node node = new Node(Integer.parseInt(str));
        node.left = deSerialize(queue);
        node.right = deSerialize(queue);
        return node;
    }


    // 二叉树最大宽度，不计入null
    public static int GetMaxBreadth(Node node) {
        if (node == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        int max = Integer.MIN_VALUE;
        int curBreadth = 0;
        Node curEnd = node;
        Node nextEnd = null;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            curBreadth++;
            if (cur == curEnd) {
                max = Math.max(max, curBreadth);
                curBreadth = 0;
                curEnd = nextEnd;
                nextEnd = null;
            }
        }
        return max;
    }


    public static Node findLowestCommonAncestor(Node head, Node node1, Node node2) {
        if (head == null || !isSameTree(head, node1, node2)) {
            return null;
        }

        return findLCA(head, node1, node2);
    }

    private static boolean isSameTree(Node head, Node node1, Node node2) {
        boolean meet1 = false;
        boolean meet2 = false;
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur == node1) {
                meet1 = true;
            }
            if (cur == node2) {
                meet2 = true;
            }
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return meet1 && meet2;
    }

    private static Node findLCA(Node head, Node node1, Node node2) {
        if (head == null || head == node1 || head == node2) {
            return head;
        }

        Node left = findLCA(head.left, node1, node2);
        Node right = findLCA(head.right, node1, node2);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return head;
    }


    // 要求时间复杂度低于O(N)
    // 核心思路是看子树能不能到达最大深度
    public static int getSizeOfCompleteBinaryTree(Node node) {
        if (node == null) {
            return 0;
        }
        int depth = getDepth(1, node);
        return getSizeOfCompleteBinaryTree(1, node, depth);
    }

    private static int getSizeOfCompleteBinaryTree(int startLayer, Node node, int depth) {
        if (node == null) {
            return 0;
        }
        if (startLayer == depth) {
            return 1;
        }
        if (getDepth(startLayer + 1, node.right) == depth) {
            int leftSize = (1 << (depth - startLayer)) - 1;
            int rightSize = getSizeOfCompleteBinaryTree(startLayer + 1, node.right, depth);
            return leftSize + rightSize + 1;
        } else {
            int leftSize = getSizeOfCompleteBinaryTree(startLayer + 1, node.left, depth);
            int rightSize = (1 << (depth - startLayer - 1)) - 1;
            return leftSize + rightSize + 1;
        }
    }

    private static int getDepth(int startLayer, Node node) {
        Node cur = node;
        int depth = startLayer;
        while (cur != null) {
            depth++;
            cur = cur.left;
        }
        return depth - 1;
    }

    static class NodePair {
        Node leftEnd;
        Node rightEnd;

        public NodePair(Node left, Node right) {
            this.leftEnd = left;
            this.rightEnd = right;
        }
    }

    // 将二叉树转化成双向链表的形式
    // 要求不能新建节点。最终的头节点和尾节点要相连
    public static Node convertToDoubleNode(Node root) {
        if (root == null) {
            return null;
        }
        NodePair pair = getNodePair(root);
        pair.leftEnd.left = pair.rightEnd;
        pair.rightEnd.right = pair.leftEnd;
        return pair.leftEnd;
    }

    private static NodePair getNodePair(Node node) {
        if (node == null) {
            return null;
        }

        NodePair leftPair = getNodePair(node.left);
        NodePair rightPair = getNodePair(node.right);
        if (leftPair != null) {
            leftPair.rightEnd.right = node;
        }
        if (rightPair != null) {
            rightPair.leftEnd.left = node;
        }
        node.left = leftPair != null ? leftPair.rightEnd : null;
        node.right = rightPair != null ? rightPair.leftEnd : null;
        Node start = node;
        Node end = node;
        if (leftPair != null) {
            start = leftPair.leftEnd;
        }
        if (rightPair != null) {
            end = rightPair.rightEnd;
        }
        return new NodePair(start, end);
    }


    // 求二叉树中累加和为sum的最长路径。路径是指从某个节点开始往下，每次只能最多选择一个子节点所形成的链路
    public static int getMaxLenWithSum(Node node, int k) {
        if (node == null) {
            return 0;
        }
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        return getMaxLenWithSum(node, sum, map, 1, k);
    }

    private static int getMaxLenWithSum(Node node,
                                        int sum,
                                        Map<Integer, Integer> map,
                                        int level,
                                        int target) {
        int maxLen = 0;
        if (node == null) {
            return maxLen;
        }

        sum += node.value;

        if (!map.containsKey(sum)) {
            map.put(sum, level);
        }

        if (map.containsKey(sum - target)) {
            maxLen = level - map.get(sum - target);
        }

        maxLen = Math.max(maxLen, getMaxLenWithSum(node.left, sum, map, level + 1, target));
        maxLen = Math.max(maxLen, getMaxLenWithSum(node.right, sum, map, level + 1, target));

        if (map.get(sum) == level) {
            map.remove(sum);
        }
        return maxLen;
    }


    static class BSTStat {
        Node head;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int size;
    }

    public static Node getMaxBST(Node node) {
        if (node == null) {
            return null;
        }
        BSTStat stat = getBSTStat(node);
        return stat.head;
    }

    private static BSTStat getBSTStat(Node node) {
        BSTStat stat = new BSTStat();
        if (node == null) {
            return stat;
        }

        BSTStat leftStat = getBSTStat(node.left);
        BSTStat rightStat = getBSTStat(node.right);

        if (node.value > leftStat.max && node.value < rightStat.min
                && leftStat.head == node.left && rightStat.head == node.right) {
            stat.head = node;
            stat.size = leftStat.size + rightStat.size + 1;
            stat.max = Math.max(Math.max(leftStat.max, rightStat.max), node.value);
            stat.min = Math.min(Math.min(leftStat.min, rightStat.min), node.value);
        } else {
            stat.head = leftStat.size > rightStat.size ? leftStat.head : rightStat.head;
            stat.size = Math.max(leftStat.size, rightStat.size);
            stat.max = Math.max(Math.max(leftStat.max, rightStat.max), node.value);
            stat.min = Math.min(Math.min(leftStat.min, rightStat.min), node.value);
        }
        return stat;
    }


}
