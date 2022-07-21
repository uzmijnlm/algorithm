package org.example.string;

/**
 * 给定字符数组，判断是否其中每个元素只出现一次
 * 要求：额外空间复杂度O(1)，时间复杂度尽量低（答案是基于非递归的堆排序）
 */
public class IsUnique {

    public boolean isUnique(char[] chars) {
        if (chars == null) {
            return false;
        }
        heapSort(chars);
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == chars[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private void heapSort(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            heapInsert(chars, i);
        }
        for (int i = chars.length-1; i > 0; i--) {
            swap(chars, 0, i);
            heapify(chars, 0, i);
        }
    }


    private void heapInsert(char[] chars, int index) {
        while (chars[index] > chars[(index - 1) / 2]) {
            swap(chars, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(char[] chars, int index, int heapSize) {
        while (index * 2 + 1 < heapSize) {
            int biggerIndex;
            if (index * 2 + 2 < heapSize && chars[index * 2 + 2] > chars[index * 2 + 1]) {
                biggerIndex = index * 2 + 2;
            } else {
                biggerIndex = index * 2 + 1;
            }
            if (chars[index] <= chars[biggerIndex]) {
                swap(chars, index, biggerIndex);
                index = biggerIndex;
            } else {
                break;
            }
        }
    }

    private void swap(char[] chars, int index1, int index2) {
        char tmp = chars[index1];
        chars[index1] = chars[index2];
        chars[index2] = tmp;
    }
}
