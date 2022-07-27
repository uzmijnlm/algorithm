package org.example.binarytree;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class BasicTest extends BaseTest {

    @Test
    public void testPreOrder() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            List<Integer> res1 = Basic.preOrderRecur(node);
            List<Integer> res2 = Basic.preOrderIter(node);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res1.get(j).equals(res2.get(j));
            }
        }
    }

    @Test
    public void testInOrder() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            List<Integer> res1 = Basic.inOrderRecur(node);
            List<Integer> res2 = Basic.inOrderIter(node);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res1.get(j).equals(res2.get(j));
            }
        }
    }

    @Test
    public void testPostOrder() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            List<Integer> res1 = Basic.postOrderRecur(node);
            List<Integer> res2 = Basic.postOrderIter(node);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res1.get(j).equals(res2.get(j));
            }
        }
    }

    @Test
    public void testBuildFromPreAndIn() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateBinaryTreeWithoutDup(100, 200);
            List<Integer> preOrderList = Basic.preOrderIter(node);
            int[] preOrder = new int[preOrderList.size()];
            for (int j = 0; j < preOrder.length; j++) {
                preOrder[j] = preOrderList.get(j);
            }
            List<Integer> inOrderList = Basic.inOrderIter(node);
            int[] inOrder = new int[inOrderList.size()];
            for (int j = 0; j < inOrder.length; j++) {
                inOrder[j] = inOrderList.get(j);
            }
            Node buildNode = Basic.buildFromPreAndIn(preOrder, inOrder);
            assertNodeEqual(node, buildNode);
        }
    }

    @Test
    public void testBuildFromPostAndIn() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateBinaryTreeWithoutDup(100, 200);
            List<Integer> postOrderList = Basic.postOrderIter(node);
            int[] postOrder = new int[postOrderList.size()];
            for (int j = 0; j < postOrder.length; j++) {
                postOrder[j] = postOrderList.get(j);
            }
            List<Integer> inOrderList = Basic.inOrderIter(node);
            int[] inOrder = new int[inOrderList.size()];
            for (int j = 0; j < inOrder.length; j++) {
                inOrder[j] = inOrderList.get(j);
            }
            Node buildNode = Basic.buildFromPostAndIn(postOrder, inOrder);
            assertNodeEqual(node, buildNode);
        }
    }

    @Test
    public void testBuildFromPreAndPost() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateBinaryTreeWithoutDup(100, 200);
            List<Integer> preOrderList = Basic.preOrderRecur(node);
            int[] preOrder = new int[preOrderList.size()];
            for (int j = 0; j < preOrder.length; j++) {
                preOrder[j] = preOrderList.get(j);
            }
            List<Integer> postOrderList = Basic.postOrderRecur(node);
            int[] postOrder = new int[postOrderList.size()];
            for (int j = 0; j < postOrder.length; j++) {
                postOrder[j] = postOrderList.get(j);
            }

            Node buildNode = Basic.buildFromPreAndPost(preOrder, postOrder);
            preOrderList = Basic.preOrderRecur(node);
            preOrder = new int[preOrderList.size()];
            for (int j = 0; j < preOrder.length; j++) {
                preOrder[j] = preOrderList.get(j);
            }
            postOrderList = Basic.postOrderRecur(node);
            postOrder = new int[postOrderList.size()];
            for (int j = 0; j < postOrder.length; j++) {
                postOrder[j] = postOrderList.get(j);
            }
            Node reBuildNode = Basic.buildFromPreAndPost(preOrder, postOrder);
            assertNodeEqual(buildNode, reBuildNode);
        }
    }

    @Test
    public void testTraverseBreadthFirst() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            List<Integer> res1 = traverseBreadthFirst(node);
            List<Integer> res2 = Basic.traverseBreadthFirst(node);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res1.get(j).equals(res2.get(j));
            }
        }
    }

    private List<Integer> traverseBreadthFirst(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        List<Node> nextLevelNodes = new ArrayList<>();
        nextLevelNodes.add(node);
        return traverseBreadthFirst(res, nextLevelNodes);
    }

    private List<Integer> traverseBreadthFirst(List<Integer> res, List<Node> nextLevelNodes) {
        if (nextLevelNodes.isEmpty()) {
            return res;
        }
        List<Node> tmp = new ArrayList<>();
        for (Node node : nextLevelNodes) {
            res.add(node.value);
            if (node.left != null) {
                tmp.add(node.left);
            }
            if (node.right != null) {
                tmp.add(node.right);
            }
        }
        nextLevelNodes = tmp;
        return traverseBreadthFirst(res, nextLevelNodes);
    }

    @Test
    public void testTraverDepthFirst() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            List<Integer> res1 = traverseDepthFirst(node);
            List<Integer> res2 = Basic.traverseDepthFirst(node);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res1.get(j).equals(res2.get(j));
            }
        }
    }

    private List<Integer> traverseDepthFirst(Node node) {
        if (node == null) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        return traverseDepthFirst(node, res);
    }

    private List<Integer> traverseDepthFirst(Node node, List<Integer> res) {
        res.add(node.value);
        if (node.left != null) {
            traverseDepthFirst(node.left, res);
        }
        if (node.right != null) {
            traverseDepthFirst(node.right, res);
        }
        return res;
    }

    @Test
    public void testIsSearchBinaryTree() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateSearchBinaryTree(100, 100);
            assert Basic.isSearchBinaryTree(node);
        }

        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            List<Integer> inOrder = Basic.inOrderIter(node);
            boolean isSBT = true;
            for (int j = 1; j < inOrder.size(); j++) {
                if (inOrder.get(j) <= inOrder.get(j - 1)) {
                    isSBT = false;
                    break;
                }
            }
            assert Basic.isSearchBinaryTree(node) == isSBT;
        }
    }

    @Test
    public void testIsBalancedBinaryTree() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(10, 100);
            assert Basic.isBalancedBinaryTree(node) == isBalancedBinaryTree(node);
        }
    }

    private boolean isBalancedBinaryTree(Node node) {
        if (node == null) {
            return true;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            int leftHeight = getHeight(cur.left);
            int rightHeight = getHeight(cur.right);
            if (Math.abs(leftHeight - rightHeight) > 1) {
                return false;
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
            if (cur.right != null) {
                stack.push(cur.right);
            }

        }
        return true;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private int getMinHeight(Node node) {
        if (node == null) {
            return 0;
        }
        int leftMinHeight = getMinHeight(node.left);
        int rightMinHeight = getMinHeight(node.right);
        return Math.min(leftMinHeight, rightMinHeight) + 1;
    }

    @Test
    public void testIsCompletedBinaryTree() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateCompletedBinaryTree(100, 200);
            assert Basic.isCompletedBinaryTree(node);
        }

        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(10, 200);
            assert Basic.isCompletedBinaryTree(node) == isCompletedBinaryTree(node);
        }
    }

    private boolean isCompletedBinaryTree(Node node) {
        if (node == null) {
            return true;
        }

        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            int leftHeight = getHeight(cur.left);
            int rightHeight = getHeight(cur.right);
            if (rightHeight > leftHeight || leftHeight - rightHeight > 1) {
                return false;
            }
            if (cur.left == null && cur.right != null) {
                return false;
            }
            int leftMinHeight = getMinHeight(cur.left);
            if (leftMinHeight < rightHeight) {
                return false;
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
            if (cur.right != null) {
                stack.push(cur.right);
            }
        }
        return true;
    }

    @Test
    public void testTraverseDepthFirstAndPreOrder() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 200);
            List<Integer> res1 = Basic.traverseDepthFirst(node);
            List<Integer> res2 = Basic.preOrderIter(node);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res1.get(j).equals(res2.get(j));
            }
        }
    }
}
