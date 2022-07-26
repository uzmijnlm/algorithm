package org.example.binarytree;

public class CommonUtil {

    public static void preOrderPrint(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.value + " ");
        preOrderPrint(node.left);
        preOrderPrint(node.right);
    }

    public static void inOrderPrint(Node node) {
        if (node == null) {
            return;
        }
        inOrderPrint(node.left);
        System.out.print(node.value + " ");
        inOrderPrint(node.right);
    }

    public static void postOrderPrint(Node node) {
        if (node == null) {
            return;
        }
        postOrderPrint(node.left);
        postOrderPrint(node.right);
        System.out.print(node.value + " ");
    }

    public static Node copyBinaryTree(Node node) {
        if (node == null) {
            return null;
        }

        Node copiedNode = new Node(node.value);
        copiedNode.left = copyBinaryTree(node.left);
        copiedNode.right = copyBinaryTree(node.right);
        return copiedNode;
    }
}
