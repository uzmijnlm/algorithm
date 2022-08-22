package org.example.queueandstack;

import org.example.string.twopointer.RemoveDupLetters;
import org.junit.Test;

import java.util.*;

public class QueueAndStackTest extends BaseTest {


    @Test
    public void testMaxSlidingWindow() {
        for (int i = 0; i < 1000; i++) {
            int[] nums = generateRandomArr(100, 200);
            int k = new Random(System.nanoTime()).nextInt(nums.length) + 1;
            int[] copiedNums = copyArr(nums);
            int[] res1 = MaxSlidingWindow.maxSlidingWindow(nums, k);
            int[] res2 = maxSlidingWindow(copiedNums, k);
            assert res1.length == res2.length;
            for (int j = 0; j < res1.length; j++) {
                assert res1[j] == res2[j];
            }
        }
    }


    // 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
    // 你只可以看到在滑动窗口内的 k 个数字。
    // 滑动窗口每次只向右移动一位。
    //返回 滑动窗口中的最大值 。
    private int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < k; ++i) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        int[] ans = new int[n - k + 1];
        ans[0] = nums[deque.peekFirst()];
        for (int i = k; i < n; ++i) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            ans[i - k + 1] = nums[deque.peekFirst()];
        }
        return ans;
    }

    @Test
    public void testMaxSubArrayCircular() {
        for (int i = 0; i < 1000; i++) {
            int[] nums = generateRandomArr(100, -100, 100);
            int[] copiedNums = copyArr(nums);
            assert MaxSubArraySumCircular.maxSubarraySumCircular(nums) == maxSubarraySumCircular(copiedNums);
        }
    }

    // 给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
    //
    //环形数组 意味着数组的末端将会与开头相连呈环状。
    // 形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
    //
    //子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。
    // 形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
    private int maxSubarraySumCircular(int[] A) {
        int N = A.length;

        // Compute P[j] = B[0] + B[1] + ... + B[j-1]
        // for fixed array B = A+A
        int[] P = new int[2 * N + 1];
        for (int i = 0; i < 2 * N; ++i)
            P[i + 1] = P[i] + A[i % N];

        // Want largest P[j] - P[i] with 1 <= j-i <= N
        // For each j, want smallest P[i] with i >= j-N
        int ans = A[0];
        // deque: i's, increasing by P[i]
        Deque<Integer> deque = new ArrayDeque();
        deque.offer(0);

        for (int j = 1; j <= 2 * N; ++j) {
            // If the smallest i is too small, remove it.
            if (deque.peekFirst() < j - N)
                deque.pollFirst();

            // The optimal i is deque[0], for cand. answer P[j] - P[i].
            ans = Math.max(ans, P[j] - P[deque.peekFirst()]);

            // Remove any i1's with P[i2] <= P[i1].
            while (!deque.isEmpty() && P[j] <= P[deque.peekLast()])
                deque.pollLast();

            deque.offerLast(j);
        }

        return ans;
    }

    @Test
    public void testLongestSubArray() {
        for (int i = 0; i < 1000; i++) {
            int[] nums = generateRandomArr(100, 200);
            int[] copiedNums = copyArr(nums);
            int limit = new Random(System.nanoTime()).nextInt(100) + 20;
            assert LongestSubArray.longestSubarray(nums, limit) == longestSubarray(copiedNums, limit);
        }
    }


    // 给你一个整数数组 nums ，和一个表示限制的整数 limit，
    // 请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。
    //
    //如果不存在满足条件的子数组，则返回 0 。
    private int longestSubarray(int[] nums, int limit) {
        Deque<Integer> queMax = new LinkedList<>();
        Deque<Integer> queMin = new LinkedList<>();
        int n = nums.length;
        int left = 0, right = 0;
        int ret = 0;
        while (right < n) {
            while (!queMax.isEmpty() && queMax.peekLast() < nums[right]) {
                queMax.pollLast();
            }
            while (!queMin.isEmpty() && queMin.peekLast() > nums[right]) {
                queMin.pollLast();
            }
            queMax.offerLast(nums[right]);
            queMin.offerLast(nums[right]);
            while (!queMax.isEmpty() && !queMin.isEmpty() && queMax.peekFirst() - queMin.peekFirst() > limit) {
                if (nums[left] == queMin.peekFirst()) {
                    queMin.pollFirst();
                }
                if (nums[left] == queMax.peekFirst()) {
                    queMax.pollFirst();
                }
                left++;
            }
            ret = Math.max(ret, right - left + 1);
            right++;
        }
        return ret;
    }


    @Test
    public void testSimplifyPath() {
        for (int i = 0; i < 1000; i++) {
            String path = generatePath(10);
            String copiedPath = copyStr(path);
            assert SimplifyPath.simplifyPath(path).equals(simplifyPath(copiedPath));
        }
    }


    // 给你一个字符串 path ，表示指向某一文件或目录的 Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
    //
    //在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..） 表示将目录切换到上一级（指向父目录）；
    // 两者都可以是复杂相对路径的组成部分。任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。
    // 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
    //
    //请注意，返回的 规范路径 必须遵循下述格式：
    //
    //始终以斜杠 '/' 开头。
    //两个目录名之间必须只有一个斜杠 '/' 。
    //最后一个目录名（如果存在）不能 以 '/' 结尾。
    //此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
    //返回简化后得到的 规范路径 。
    private String simplifyPath(String path) {
        String[] names = path.split("/");
        Deque<String> stack = new ArrayDeque<>();
        for (String name : names) {
            if ("..".equals(name)) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else if (name.length() > 0 && !".".equals(name)) {
                stack.offerLast(name);
            }
        }
        StringBuffer ans = new StringBuffer();
        if (stack.isEmpty()) {
            ans.append('/');
        } else {
            while (!stack.isEmpty()) {
                ans.append('/');
                ans.append(stack.pollFirst());
            }
        }
        return ans.toString();
    }


    @Test
    public void testLargestRectArea() {
        int[] heights = generateRandomArr(100, 200);
        int[] copiedHeights = copyArr(heights);
        assert LargestRectArea.largestRectangleArea(heights) == largestRectangleArea(copiedHeights);
    }


    // 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
    //
    //求在该柱状图中，能够勾勒出来的矩形的最大面积。
    private int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return heights[0];
        }

        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>(len);
        for (int i = 0; i < len; i++) {
            // 这个 while 很关键，因为有可能不止一个柱形的最大宽度可以被计算出来
            while (!stack.isEmpty() && heights[i] < heights[stack.peekLast()]) {
                int curHeight = heights[stack.pollLast()];
                while (!stack.isEmpty() && heights[stack.peekLast()] == curHeight) {
                    stack.pollLast();
                }

                int curWidth;
                if (stack.isEmpty()) {
                    curWidth = i;
                } else {
                    curWidth = i - stack.peekLast() - 1;
                }

                // System.out.println("curIndex = " + curIndex + " " + curHeight * curWidth);
                res = Math.max(res, curHeight * curWidth);
            }
            stack.addLast(i);
        }

        while (!stack.isEmpty()) {
            int curHeight = heights[stack.pollLast()];
            while (!stack.isEmpty() && heights[stack.peekLast()] == curHeight) {
                stack.pollLast();
            }
            int curWidth;
            if (stack.isEmpty()) {
                curWidth = len;
            } else {
                curWidth = len - stack.peekLast() - 1;
            }
            res = Math.max(res, curHeight * curWidth);
        }
        return res;
    }


    @Test
    public void testRemoveDupLetters() {
        for (int i = 0; i < 1000; i++) {
            String s = generateRandomString(100);
            String copyS = copyStr(s);
            assert RemoveDupLetters.removeDuplicateLetters(s).equals(removeDuplicateLetters(copyS));
        }
    }


    // 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。
    // 需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
    private String removeDuplicateLetters(String s) {
        boolean[] vis = new boolean[26];
        int[] num = new int[26];
        for (int i = 0; i < s.length(); i++) {
            num[s.charAt(i) - 'a']++;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!vis[ch - 'a']) {
                while (sb.length() > 0 && sb.charAt(sb.length() - 1) > ch) {
                    if (num[sb.charAt(sb.length() - 1) - 'a'] > 0) {
                        vis[sb.charAt(sb.length() - 1) - 'a'] = false;
                        sb.deleteCharAt(sb.length() - 1);
                    } else {
                        break;
                    }
                }
                vis[ch - 'a'] = true;
                sb.append(ch);
            }
            num[ch - 'a'] -= 1;
        }
        return sb.toString();
    }


    @Test
    public void testMaxNumber() {
        for (int i = 0; i < 1000; i++) {
            int[] nums1 = generateRandomArr(100, 200);
            int[] nums2 = generateRandomArr(100, 200);
            int k = new Random(System.nanoTime()).nextInt(nums1.length + nums2.length) + 1;
            int[] copiedNums1 = copyArr(nums1);
            int[] copiedNums2 = copyArr(nums2);

            int[] res1 = MaxNumber.maxNumber(nums1, nums2, k);
            int[] res2 = maxNumber(copiedNums1, copiedNums2, k);
            assert res1.length == res2.length;
            for (int j = 0; j < res1.length; j++) {
                assert res1[j] == res2[j];
            }
        }
    }

    // 给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，表示两个自然数各位上的数字。
    // 现在从这两个数组中选出 k (k <= m + n) 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
    //
    //求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
    //
    //说明: 请尽可能地优化你算法的时间和空间复杂度。
    private int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] maxSubsequence = new int[k];
        int start = Math.max(0, k - n), end = Math.min(k, m);
        for (int i = start; i <= end; i++) {
            int[] subsequence1 = maxSubsequence(nums1, i);
            int[] subsequence2 = maxSubsequence(nums2, k - i);
            int[] curMaxSubsequence = merge(subsequence1, subsequence2);
            if (compare(curMaxSubsequence, 0, maxSubsequence, 0) > 0) {
                System.arraycopy(curMaxSubsequence, 0, maxSubsequence, 0, k);
            }
        }
        return maxSubsequence;
    }

    private int[] maxSubsequence(int[] nums, int k) {
        int length = nums.length;
        int[] stack = new int[k];
        int top = -1;
        int remain = length - k;
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            while (top >= 0 && stack[top] < num && remain > 0) {
                top--;
                remain--;
            }
            if (top < k - 1) {
                stack[++top] = num;
            } else {
                remain--;
            }
        }
        return stack;
    }

    private int[] merge(int[] subsequence1, int[] subsequence2) {
        int x = subsequence1.length, y = subsequence2.length;
        if (x == 0) {
            return subsequence2;
        }
        if (y == 0) {
            return subsequence1;
        }
        int mergeLength = x + y;
        int[] merged = new int[mergeLength];
        int index1 = 0, index2 = 0;
        for (int i = 0; i < mergeLength; i++) {
            if (compare(subsequence1, index1, subsequence2, index2) > 0) {
                merged[i] = subsequence1[index1++];
            } else {
                merged[i] = subsequence2[index2++];
            }
        }
        return merged;
    }

    private int compare(int[] subsequence1, int index1, int[] subsequence2, int index2) {
        int x = subsequence1.length, y = subsequence2.length;
        while (index1 < x && index2 < y) {
            int difference = subsequence1[index1] - subsequence2[index2];
            if (difference != 0) {
                return difference;
            }
            index1++;
            index2++;
        }
        return (x - index1) - (y - index2);
    }

    @Test
    public void testRemoveKDigits() {
        for (int i = 0; i < 1000; i++) {
            String num = generateRandomNumString(100);
            String copiedNum = copyStr(num);
            int k = new Random(System.nanoTime()).nextInt(num.length());
            assert RemoveKDigits.removeKDigits(num, k).equals(removeKDigits(copiedNum, k));
        }
    }

    // 给你一个以字符串表示的非负整数 num 和一个整数 k ，移除这个数中的 k 位数字，使得剩下的数字最小。
    // 请你以字符串形式返回这个最小的数字。
    private String removeKDigits(String num, int k) {
        Deque<Character> deque = new LinkedList<>();
        int length = num.length();
        for (int i = 0; i < length; ++i) {
            char digit = num.charAt(i);
            while (!deque.isEmpty() && k > 0 && deque.peekLast() > digit) {
                deque.pollLast();
                k--;
            }
            deque.offerLast(digit);
        }

        for (int i = 0; i < k; ++i) {
            deque.pollLast();
        }

        StringBuilder ret = new StringBuilder();
        boolean leadingZero = true;
        while (!deque.isEmpty()) {
            char digit = deque.pollFirst();
            if (leadingZero && digit == '0') {
                continue;
            }
            leadingZero = false;
            ret.append(digit);
        }
        return ret.length() == 0 ? "0" : ret.toString();
    }


    // 给你一个整数数组 nums ，数组中共有 n 个整数。
    // 132 模式的子序列 由三个整数 nums[i]、nums[j] 和 nums[k] 组成，
    // 并同时满足：i < j < k 和 nums[i] < nums[k] < nums[j] 。
    //
    //如果 nums 中存在 132 模式的子序列 ，返回 true ；否则，返回 false 。
    private boolean find132pattern(int[] nums) {
        int n = nums.length;
        Deque<Integer> d = new ArrayDeque<>();
        int k = Integer.MIN_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] < k) return true;
            while (!d.isEmpty() && d.peekLast() < nums[i]) {
                // 事实上，k 的变化也具有单调性，直接使用 k = pollLast() 也是可以的
                k = Math.max(k, d.pollLast());
            }
            d.addLast(nums[i]);
        }
        return false;
    }


    @Test
    public void testNextGreaterElement() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArrWithoutDup(100, 200);
            int[] nums2 = copyArr(arr);
            int[] nums1 = getRandomSubArray(arr);
            shuffle(nums1);

            int[] copiedNums2 = copyArr(nums2);
            int[] copiedNums1 = copyArr(nums1);

            int[] res1 = NextGreaterElement.nextGreaterElement(nums1, nums2);
            int[] res2 = nextGreaterElement(copiedNums1, copiedNums2);

            assert res1.length == res2.length;
            for (int j = 0; j < res1.length; j++) {
                assert res1[j] == res2[j];
            }
        }
    }


    // nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
    //
    //给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
    //
    //对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，
    // 并且在 nums2 确定 nums2[j] 的 下一个更大元素 。如果不存在下一个更大元素，那么本次查询的答案是 -1 。
    //
    //返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
    private int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = nums2.length - 1; i >= 0; --i) {
            int num = nums2[i];
            while (!stack.isEmpty() && num >= stack.peek()) {
                stack.pop();
            }
            map.put(num, stack.isEmpty() ? -1 : stack.peek());
            stack.push(num);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; ++i) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }


    @Test
    public void testNextGreaterElementsCircular() {
        for (int i = 0; i < 1000; i++) {
            int[] nums = generateRandomArr(100, 200);
            int[] copiedNums = copyArr(nums);
            int[] res1 = NextGreaterElementCircular.nextGreaterElements(nums);
            int[] res2 = nextGreaterElements(copiedNums);
            assert res1.length == res2.length;
            for (int j = 0; j < res1.length; j++) {
                assert res1[j] == res2[j];
            }
        }
    }

    // 给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。
    //
    //数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，
    // 这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
    private int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ret = new int[n];
        Arrays.fill(ret, -1);
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < n * 2 - 1; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % n]) {
                ret[stack.pop()] = nums[i % n];
            }
            stack.push(i % n);
        }
        return ret;
    }


    @Test
    public void testMaxChunks() {
        for (int i = 0; i < 1000; i++) {
            int length = new Random(System.nanoTime()).nextInt(100) + 1;
            int[] arr = new int[length];
            for (int j = 0; j < length; j++) {
                arr[j] = j;
            }
            shuffle(arr);
            int[] copiedArr = copyArr(arr);
            assert MaxChunksToSorted.maxChunksToSorted(arr) == maxChunksToSorted(copiedArr);
        }

        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            assert MaxChunksToSorted.maxChunksToSorted(arr) == maxChunksToSorted2(copiedArr);
        }
    }


    // 给定一个长度为 n 的整数数组 arr ，它表示在 [0, n - 1] 范围内的整数的排列。
    //
    //我们将 arr 分割成若干 块 (即分区)，并对每个块单独排序。将它们连接起来后，使得连接的结果和按升序排序后的原数组相同。
    //
    //返回数组能分成的最多块数量。
    private int maxChunksToSorted(int[] arr) {
        Deque<Integer> stack = new LinkedList<>();
        stack.addLast(0);

        for (int i = 1; i < arr.length; i++) {
            if (arr[stack.peekLast()] < arr[i]) {
                stack.addLast(i);
                continue;
            }

            int max = stack.peekLast();

            while (!stack.isEmpty() && arr[stack.peekLast()] > arr[i]) {
                stack.removeLast();
            }

            stack.addLast(max);
        }

        return stack.size();
    }


    // 这个问题和“最多能完成排序的块”相似，但给定数组中的元素可以重复，输入数组最大长度为2000，其中的元素最大为10**8。
    //
    //arr是一个可能包含重复元素的整数数组，我们将这个数组分割成几个“块”，并将这些块分别进行排序。
    // 之后再连接起来，使得连接的结果和按升序排序后的原数组相同。
    //
    //我们最多能将数组分成多少块？
    private int maxChunksToSorted2(int[] arr) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int num : arr) {
            if (stack.isEmpty() || num >= stack.peek()) {
                stack.push(num);
            } else {
                int mx = stack.pop();
                while (!stack.isEmpty() && stack.peek() > num) {
                    stack.pop();
                }
                stack.push(mx);
            }
        }
        return stack.size();
    }

}
