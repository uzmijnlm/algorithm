package org.example.binarytree;

import java.util.*;

public class Basic {

    public static List<Integer> preOrderRecur(Node node) {
        List<Integer> res = new ArrayList<>();
        return preOrderRecur(node, res);
    }

    private static List<Integer> preOrderRecur(Node node, List<Integer> res) {
        if (node == null) {
            return res;
        }
        res.add(node.value);
        res = preOrderRecur(node.left, res);
        res = preOrderRecur(node.right, res);
        return res;
    }

    // 前序遍历就是一种深度优先遍历
    public static List<Integer> preOrderIter(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        List<Integer> res = new ArrayList<>();
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            res.add(cur.value);
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        return res;
    }

    public static List<Integer> inOrderRecur(Node node) {
        List<Integer> res = new ArrayList<>();
        return inOrderRecur(node, res);
    }

    private static List<Integer> inOrderRecur(Node node, List<Integer> res) {
        if (node == null) {
            return res;
        }
        res = inOrderRecur(node.left, res);
        res.add(node.value);
        res = inOrderRecur(node.right, res);
        return res;
    }

    public static List<Integer> inOrderIter(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<Integer> res = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        Node cur = node.left;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.pop();
            res.add(cur.value);
            cur = cur.right;
        }
        return res;
    }

    public static List<Integer> postOrderRecur(Node node) {
        List<Integer> res = new ArrayList<>();
        return postOrderRecur(node, res);
    }

    private static List<Integer> postOrderRecur(Node node, List<Integer> res) {
        if (node == null) {
            return res;
        }
        res = postOrderRecur(node.left, res);
        res = postOrderRecur(node.right, res);
        res.add(node.value);
        return res;
    }

    public static List<Integer> postOrderIter(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<Integer> res = new ArrayList<>();
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.push(node);
        while (!stack1.isEmpty()) {
            Node cur = stack1.pop();
            if (cur.left != null) {
                stack1.push(cur.left);
            }
            if (cur.right != null) {
                stack1.push(cur.right);
            }
            stack2.push(cur);
        }

        while (!stack2.isEmpty()) {
            res.add(stack2.pop().value);
        }
        return res;
    }

    public static Node buildFromPreAndIn(int[] preOrder, int[] inOrder) {
        if (preOrder == null || inOrder == null) {
            return null;
        }
        if (preOrder.length != inOrder.length) {
            return null;
        }

        return buildFromPreAndIn(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);
    }

    private static Node buildFromPreAndIn(int[] preOrder, int preL, int preR, int[] inOrder, int inL, int inR) {
        if (preL > preR || inL > inR) {
            return null;
        }

        int midValue = preOrder[preL];
        int midIndex = inL;
        while (true) {
            if (midIndex > inR) {
                throw new RuntimeException("无法构建二叉树");
            }
            if (inOrder[midIndex] == midValue) {
                break;
            }
            midIndex++;
        }
        int leftLength = midIndex - inL;
        Node node = new Node(midValue);
        node.left = buildFromPreAndIn(preOrder, preL + 1, preL + leftLength, inOrder, inL, midIndex - 1);
        node.right = buildFromPreAndIn(preOrder, preL + leftLength + 1, preR, inOrder, midIndex + 1, inR);
        return node;
    }

    public static Node buildFromPostAndIn(int[] postOrder, int[] inOrder) {
        if (postOrder == null || inOrder == null) {
            return null;
        }
        if (postOrder.length != inOrder.length) {
            return null;
        }

        return buildFromPostAndIn(postOrder, 0, postOrder.length - 1, inOrder, 0, inOrder.length - 1);
    }

    private static Node buildFromPostAndIn(int[] postOrder, int postL, int postR, int[] inOrder, int inL, int inR) {
        if (postL > postR || inL > inR) {
            return null;
        }

        int midValue = postOrder[postR];
        int midIndex = inL;
        while (true) {
            if (midIndex > inR) {
                throw new RuntimeException("无法构建二叉树");
            }
            if (inOrder[midIndex] == midValue) {
                break;
            }
            midIndex++;
        }

        int leftLength = midIndex - inL;
        Node node = new Node(midValue);
        node.left = buildFromPostAndIn(postOrder, postL, postL + leftLength - 1, inOrder, inL, midIndex - 1);
        node.right = buildFromPostAndIn(postOrder, postL + leftLength, postR - 1, inOrder, midIndex + 1, inR);
        return node;
    }

    // 无法用前序和后序还原唯一的二叉树。在leetcode中只需返回其中一个答案即可
    public static Node buildFromPreAndPost(int[] preOrder, int[] postOrder) {
        if (preOrder == null || postOrder == null) {
            return null;
        }
        if (preOrder.length != postOrder.length) {
            return null;
        }

        return buildFromPreAndPost(preOrder, 0, preOrder.length - 1, postOrder, 0, postOrder.length - 1);
    }

    private static Node buildFromPreAndPost(int[] preOrder, int preL, int preR, int[] postOrder, int postL, int postR) {
        if (preL > preR || postL > postR) {
            return null;
        }

        Node node = new Node(preOrder[preL]);
        if (preL == preR || postL == postR) {
            return node;
        }
        int lastLeftIndex = postL;
        while (true) {
            if (lastLeftIndex > postR) {
                throw new RuntimeException("无法构建二叉树");
            }
            if (postOrder[lastLeftIndex] == preOrder[preL + 1]) {
                break;
            }
            lastLeftIndex++;
        }

        int leftLength = lastLeftIndex - postL + 1;
        node.left = buildFromPreAndPost(preOrder, preL + 1, preL + leftLength, postOrder, postL, lastLeftIndex);
        node.right = buildFromPreAndPost(preOrder, preL + leftLength + 1, preR, postOrder, lastLeftIndex + 1, postR - 1);
        return node;
    }

    public static List<Integer> traverseBreadthFirst(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<Integer> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            res.add(cur.value);
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return res;
    }

    // 与前序遍历完全一致
    public static List<Integer> traverseDepthFirst(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }

        List<Integer> res = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            res.add(cur.value);
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        return res;
    }

    static class NodeWithStat {
        public int max = Integer.MIN_VALUE;
        public int min = Integer.MAX_VALUE;
        public boolean isSBT = true;
    }

    public static boolean isSearchBinaryTree(Node node) {
        if (node == null) {
            return true;
        }

        NodeWithStat leftStat = isSBT(node.left);
        NodeWithStat rightStat = isSBT(node.right);
        return node.value > leftStat.max && node.value < rightStat.min && leftStat.isSBT && rightStat.isSBT;
    }

    private static NodeWithStat isSBT(Node node) {
        NodeWithStat stat = new NodeWithStat();
        if (node == null) {
            return stat;
        }

        NodeWithStat leftStat = isSBT(node.left);
        NodeWithStat rightStat = isSBT(node.right);

        boolean isSBT = node.value > leftStat.max && node.value < rightStat.min && leftStat.isSBT && rightStat.isSBT;
        stat.min = Math.min(Math.min(leftStat.min, rightStat.min), node.value);
        stat.max = Math.max(Math.max(leftStat.max, rightStat.max), node.value);
        stat.isSBT = isSBT;
        return stat;
    }

    static class NodeWithHeight {
        public boolean isBBT = true;
        public int height = 0;
    }

    public static boolean isBalancedBinaryTree(Node node) {
        if (node == null) {
            return true;
        }

        NodeWithHeight leftInfo = isBBT(node.left);
        NodeWithHeight rightInfo = isBBT(node.right);

        return leftInfo.isBBT && rightInfo.isBBT && Math.abs(leftInfo.height - rightInfo.height) <= 1;
    }

    private static NodeWithHeight isBBT(Node node) {
        NodeWithHeight info = new NodeWithHeight();
        if (node == null) {
            return info;
        }

        NodeWithHeight leftInfo = isBBT(node.left);
        NodeWithHeight rightInfo = isBBT(node.right);

        info.isBBT = leftInfo.isBBT && rightInfo.isBBT && Math.abs(leftInfo.height - rightInfo.height) <= 1;
        info.height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return info;
    }

    public static boolean isCompletedBinaryTree(Node node) {
        if (node == null) {
            return true;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        boolean meetLastComplete = false;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left == null && cur.right != null) {
                return false;
            }
            if (meetLastComplete) {
                if (cur.left != null || cur.right != null) {
                    return false;
                }
            } else {
                if ((cur.left != null && cur.right == null) || (cur.left == null && cur.right == null)) {
                    meetLastComplete = true;
                }
            }
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
        return true;
    }

}
