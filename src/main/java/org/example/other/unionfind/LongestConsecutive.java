package org.example.other.unionfind;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 */
class LongestConsecutive {

    public int longestConsecutive(int[] nums) {
        UnionFindSet uf = new UnionFindSet(nums);
        for (int num : nums) {
            if (uf.contains(num - 1)) {
                uf.union(num, num - 1);
            }
            if (uf.contains(num + 1)) {
                uf.union(num, num + 1);
            }
        }
        return uf.getMaxRank();
    }

    static class Element {
        int val;

        public Element(int val) {
            this.val = val;
        }
    }

    static class UnionFindSet {
        private final Map<Integer, Element> elementMap;
        private final Map<Element, Element> fatherMap;
        private final Map<Element, Integer> rankMap;

        public UnionFindSet(int[] nums) {
            elementMap = new HashMap<>();
            fatherMap = new HashMap<>();
            rankMap = new HashMap<>();
            for (int num : nums) {
                Element ele = new Element(num);
                elementMap.put(num, ele);
                fatherMap.put(ele, ele);
                rankMap.put(ele, 1);
            }
        }

        public boolean contains(int num) {
            return elementMap.containsKey(num);
        }

        public void union(int num1, int num2) {
            Element ele1 = elementMap.get(num1);
            Element ele2 = elementMap.get(num2);
            Element f1 = findHead(ele1);
            Element f2 = findHead(ele2);
            if (f1 != f2) {
                Element larger = rankMap.get(f1) > rankMap.get(f2) ? f1 : f2;
                Element smaller = rankMap.get(f1) > rankMap.get(f2) ? f2 : f1;
                fatherMap.put(smaller, larger);
                rankMap.put(larger, rankMap.get(smaller) + rankMap.get(larger));
                rankMap.remove(smaller);
            }
        }

        public int getMaxRank() {
            int ans = 0;
            for (int size : rankMap.values()) {
                ans = Math.max(ans, size);
            }
            return ans;
        }

        private Element findHead(Element ele) {
            Stack<Element> stack = new Stack<>();
            while (ele != fatherMap.get(ele)) {
                stack.push(ele);
                ele = fatherMap.get(ele);
            }
            while (!stack.isEmpty()) {
                fatherMap.put(stack.pop(), ele);
            }
            return ele;
        }
    }
}
