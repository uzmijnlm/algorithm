package org.example.binarytree;

import org.junit.Test;

public class CommonUtilTest extends BaseTest {

    @Test
    public void testCopyBinaryTree() {
        for (int i = 0; i < 1000; i++) {
            Node node = generateRandomBinaryTree(100, 100);
            Node copiedNode = CommonUtil.copyBinaryTree(node);
            assertNodeEqual(node, copiedNode);
        }
    }


}
