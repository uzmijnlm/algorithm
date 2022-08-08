package org.example.arr;

import org.example.arr.presum.*;
import org.example.arr.slidingwindow.LongestOnes;
import org.example.arr.slidingwindow.MinSubArrayLen;
import org.example.arr.slidingwindow.SubArraysWithKDistinct;
import org.example.arr.twopointer.*;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ArrTest extends BaseTest {

    @Test
    public void testMaxArea() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            assert MaxArea.maxArea(arr) == maxArea(copiedArr);
        }
    }


    // 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
    //找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
    //返回容器可以储存的最大水量。
    private int maxArea(int[] height) {
        int i = 0, j = height.length - 1, res = 0;
        while (i < j) {
            res = height[i] < height[j] ?
                    Math.max(res, (j - i) * height[i++]) :
                    Math.max(res, (j - i) * height[j--]);
        }
        return res;
    }

    @Test
    public void testThreeSum() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            for (int j = 0; j < arr.length; j++) {
                arr[j] = arr[j] - 100;
            }
            int[] copiedArr = copyArr(arr);
            List<List<Integer>> res1 = ThreeSum.threeSum(arr);
            List<List<Integer>> res2 = threeSum(copiedArr);
            assert res1.size() == res2.size();
            for (List<Integer> list1 : res1) {
                assert list1.size() == 3;
                boolean exist = false;
                for (List<Integer> list2 : res2) {
                    assert list1.size() == list2.size();
                    if (list1.get(0).equals(list2.get(0))
                            && list1.get(1).equals(list2.get(1))
                            && list1.get(2).equals(list2.get(2))) {
                        exist = true;
                        break;
                    }
                }
                assert exist;
            }
            for (List<Integer> list2 : res2) {
                assert list2.size() == 3;
                boolean exist = false;
                for (List<Integer> list1 : res1) {
                    assert list1.size() == list2.size();
                    if (list1.get(0).equals(list2.get(0))
                            && list1.get(1).equals(list2.get(1))
                            && list1.get(2).equals(list2.get(2))) {
                        exist = true;
                        break;
                    }
                }
                assert exist;
            }
        }
    }

    // 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
    //注意：答案中不可以包含重复的三元组。
    private List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        // 枚举 a
        for (int first = 0; first < n; ++first) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            int third = n - 1;
            int target = -nums[first];
            // 枚举 b
            for (int second = first + 1; second < n; ++second) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 需要保证 b 的指针在 c 的指针的左侧
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    @Test
    public void testThreeSumClosest() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            if (arr.length < 3) {
                continue;
            }
            for (int j = 0; j < arr.length; j++) {
                arr[j] = arr[j] - 100;
            }
            int[] copiedArr = copyArr(arr);
            int target = new Random(System.nanoTime()).nextInt(100) - 50;

            assert ThreeSum.threeSumClosest(arr, target) == threeSumClosest(copiedArr, target);
        }
    }

    // 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
    //返回这三个数的和。
    //假定每组输入只存在恰好一个解。
    private int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int best = 10000000;

        // 枚举 a
        for (int i = 0; i < n; ++i) {
            // 保证和上一次枚举的元素不相等
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 使用双指针枚举 b 和 c
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                // 如果和为 target 直接返回答案
                if (sum == target) {
                    return target;
                }
                // 根据差值的绝对值来更新答案
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }
                if (sum > target) {
                    // 如果和大于 target，移动 c 对应的指针
                    int k0 = k - 1;
                    // 移动到下一个不相等的元素
                    while (j < k0 && nums[k0] == nums[k]) {
                        --k0;
                    }
                    k = k0;
                } else {
                    // 如果和小于 target，移动 b 对应的指针
                    int j0 = j + 1;
                    // 移动到下一个不相等的元素
                    while (j0 < k && nums[j0] == nums[j]) {
                        ++j0;
                    }
                    j = j0;
                }
            }
        }
        return best;
    }

    @Test
    public void testFourSum() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            for (int j = 0; j < arr.length; j++) {
                arr[j] = arr[j] - 100;
            }
            int[] copiedArr = copyArr(arr);
            int target = new Random(System.nanoTime()).nextInt(100) - 50;

            List<List<Integer>> res1 = ThreeSum.fourSum(arr, target);
            List<List<Integer>> res2 = fourSum(copiedArr, target);

            assert res1.size() == res2.size();
            for (List<Integer> list1 : res1) {
                assert list1.size() == 4;
                boolean exist = false;
                for (List<Integer> list2 : res2) {
                    assert list1.size() == list2.size();
                    if (list1.get(0).equals(list2.get(0))
                            && list1.get(1).equals(list2.get(1))
                            && list1.get(2).equals(list2.get(2))) {
                        exist = true;
                        break;
                    }
                }
                assert exist;
            }
            for (List<Integer> list2 : res2) {
                assert list2.size() == 4;
                boolean exist = false;
                for (List<Integer> list1 : res1) {
                    assert list1.size() == list2.size();
                    if (list1.get(0).equals(list2.get(0))
                            && list1.get(1).equals(list2.get(1))
                            && list1.get(2).equals(list2.get(2))) {
                        exist = true;
                        break;
                    }
                }
                assert exist;
            }

        }
    }

    // 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
    //0 <= a, b, c, d < n
    //a、b、c 和 d 互不相同
    //nums[a] + nums[b] + nums[c] + nums[d] == target
    //你可以按 任意顺序 返回答案 。
    private List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> quadruplets = new ArrayList<List<Integer>>();
        if (nums == null || nums.length < 4) {
            return quadruplets;
        }
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            if ((long) nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }
            for (int j = i + 1; j < length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                if ((long) nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }
                int left = j + 1, right = length - 1;
                while (left < right) {
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        quadruplets.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return quadruplets;
    }

    @Test
    public void testGetWater() {
        for (int i = 0; i < 1; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            assert GetWater.getWater(arr) == getWater(copiedArr);
        }
    }

    // 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
    private int getWater(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; ++i) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; --i) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }


    @Test
    public void testRemoveDup() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateSortedArr(100, 200);
            int[] copiedArr = copyArr(arr);
            assert RemoveDup.removeDup(arr) == removeDuplicates(copiedArr);
            assert arr.length == copiedArr.length;
            for (int j = 0; j < arr.length; j++) {
                assert arr[j] == copiedArr[j];
            }
        }
    }

    // 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
    //不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
    private int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return n;
        }
        int slow = 2, fast = 2;
        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }

    @Test
    public void testTwoSum() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            for (int j = 0; j < arr.length; j++) {
                arr[j] = arr[j] - 100;
            }
            int[] copiedArr = copyArr(arr);
            int target = new Random(System.nanoTime()).nextInt(100) - 50;

            int[] res1 = ThreeSum.twoSum(arr, target);
            int[] res2 = twoSum(copiedArr, target);
            assert res1.length == res2.length;
            for (int j = 0; j < res1.length; j++) {
                assert res1[j] == res2[j];
            }
        }
    }

    // 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列 ，请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
    //以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
    //你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
    //你所设计的解决方案必须只使用常量级的额外空间。
    private int[] twoSum(int[] numbers, int target) {
        int low = 0, high = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];
            if (sum == target) {
                return new int[]{low + 1, high + 1};
            } else if (sum < target) {
                ++low;
            } else {
                --high;
            }
        }
        return new int[]{-1, -1};
    }

    @Test
    public void testFindDup() {
        for (int i = 0; i < 1000; i++) {
            int n = new Random(System.nanoTime()).nextInt(100) + 1;
            int[] arr = new int[n + 1];
            for (int j = 0; j < n; j++) {
                arr[j] = j;
            }
            arr[n] = new Random(System.nanoTime()).nextInt(n) + 1;
            shuffle(arr);
            int[] copiedArr = copyArr(arr);

            assert FindDup.findDuplicate(arr) == findDuplicate(copiedArr);
        }
    }

    // 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
    //假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
    //你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
    private int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }


    @Test
    public void testFindRadius() {
        for (int i = 0; i < 1000; i++) {
            int[] houses = generateRandomArr(100, 200);
            while (houses.length == 0) {
                houses = generateRandomArr(100, 200);
            }
            int[] heaters = generateRandomArr(100, 200);
            while (heaters.length == 0) {
                heaters = generateRandomArr(100, 200);
            }
            int[] copiedHouses = copyArr(houses);
            int[] copiedHeaters = copyArr(heaters);

            assert FindRadius.findRadius(houses, heaters) == findRadius(copiedHouses, copiedHeaters);
        }
    }

    // 冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
    //
    //在加热器的加热半径范围内的每个房屋都可以获得供暖。
    //
    //现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置，请你找出并返回可以覆盖所有房屋的最小加热半径。
    //
    //说明：所有供暖器都遵循你的半径标准，加热的半径也一样。
    private int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int ans = 0;
        for (int i = 0, j = 0; i < houses.length; i++) {
            int curDistance = Math.abs(houses[i] - heaters[j]);
            while (j < heaters.length - 1 && Math.abs(houses[i] - heaters[j]) >= Math.abs(houses[i] - heaters[j + 1])) {
                j++;
                curDistance = Math.min(curDistance, Math.abs(houses[i] - heaters[j]));
            }
            ans = Math.max(ans, curDistance);
        }
        return ans;
    }

    @Test
    public void testFindPairs() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            int k = new Random(System.nanoTime()).nextInt(100);

            assert FindPairs.findPairs(arr, k) == findPairs(copiedArr, k);
        }
    }

    // 给你一个整数数组 nums 和一个整数 k，请你在数组中找出 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
    //
    //k-diff 数对定义为一个整数对 (nums[i], nums[j]) ，并满足下述全部条件：
    //
    //0 <= i, j < nums.length
    //i != j
    //nums[i] - nums[j] == k
    //注意，|val| 表示 val 的绝对值。
    private int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, y = 0, res = 0;
        for (int x = 0; x < n; x++) {
            if (x == 0 || nums[x] != nums[x - 1]) {
                while (y < n && (nums[y] < nums[x] + k || y <= x)) {
                    y++;
                }
                if (y < n && nums[y] == nums[x] + k) {
                    res++;
                }
            }
        }
        return res;
    }


    @Test
    public void testFindUnsortedSubarray() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            while (arr.length == 0) {
                arr = generateRandomArr(100, 200);
            }
            int[] copiedArr = copyArr(arr);
            assert FindUnsortedSubarray.findUnsortedSubarray(arr) == findUnsortedSubarray(copiedArr);
        }
    }

    // 给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
    //
    //请你找出符合题意的 最短 子数组，并输出它的长度。
    private int findUnsortedSubarray(int[] nums) {
        //初始化
        int len = nums.length;
        int min = nums[len - 1];
        int max = nums[0];
        int begin = 0, end = -1;
        //遍历
        for (int i = 0; i < len; i++) {
            if (nums[i] < max) {      //从左到右维持最大值，寻找右边界end
                end = i;
            } else {
                max = nums[i];
            }

            if (nums[len - i - 1] > min) {    //从右到左维持最小值，寻找左边界begin
                begin = len - i - 1;
            } else {
                min = nums[len - i - 1];
            }
        }
        return end - begin + 1;
    }

    @Test
    public void testTriangleNumber() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            assert TriangleNumber.triangleNumber(arr) == triangleNumber(copiedArr);
        }
    }


    // 给定一个包含非负整数的数组 nums ，返回其中可以组成三角形三条边的三元组个数。
    private int triangleNumber(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i - 1, k = 0; k < j; j--) {
                while (k < j && nums[k] + nums[j] <= nums[i]) k++;
                ans += j - k;
            }
        }
        return ans;
    }


    @Test
    public void testFindClosestElements() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateSortedArr(100, 200);
            while (arr.length == 0) {
                arr = generateSortedArr(100, 200);
            }
            int[] copiedArr = copyArr(arr);
            int k = new Random(System.nanoTime()).nextInt(arr.length) + 1;
            int x = new Random(System.nanoTime()).nextInt(200);
            List<Integer> res1 = FindClosestElements.findClosestElements(arr, k, x);
            List<Integer> res2 = findClosestElements(copiedArr, k, x);
            assert res1.size() == res2.size();
            for (int j = 0; j < res1.size(); j++) {
                assert res1.get(j).equals(res2.get(j));
            }
        }
    }

    // 给定一个 排序好 的数组 arr ，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。返回的结果必须要是按升序排好的。
    //
    //整数 a 比整数 b 更接近 x 需要满足：
    //
    //|a - x| < |b - x| 或者
    //|a - x| == |b - x| 且 a < b
    private List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> ret = Arrays.stream(arr).boxed().collect(Collectors.toList());
        int n = ret.size();
        if (x <= ret.get(0)) {
            return ret.subList(0, k);
        } else if (ret.get(n - 1) <= x) {
            return ret.subList(n - k, n);
        } else {
            int index = Collections.binarySearch(ret, x);
            if (index < 0)
                index = -index - 1;
            int low = Math.max(0, index - k - 1), high = Math.min(ret.size() - 1, index + k - 1);

            while (high - low > k - 1) {
                if ((x - ret.get(low)) <= (ret.get(high) - x))
                    high--;
                else if ((x - ret.get(low)) > (ret.get(high) - x))
                    low++;
                else
                    System.out.println("unhandled case: " + low + " " + high);
            }
            return ret.subList(low, high + 1);
        }
    }

    @Test
    public void testSmallestDistPair() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            while (arr.length < 10) {
                arr = generateRandomArr(100, 200);
            }
            int length = arr.length;
            int kBound = length * (length - 1) / 2;
            int k = new Random(System.nanoTime()).nextInt(kBound) + 1;

            int[] copiedArr = copyArr(arr);

            assert SmallestDistPair.smallestDistancePair(arr, k) == smallestDistancePair(copiedArr, k);
        }

    }

    // 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
    //
    //给你一个整数数组 nums 和一个整数 k ，数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length 。返回 所有数对距离中 第 k 小的数对距离。
    private int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length, left = 0, right = nums[n - 1] - nums[0];
        while (left <= right) {
            int mid = (left + right) / 2;
            int cnt = 0;
            for (int i = 0, j = 0; j < n; j++) {
                while (nums[j] - nums[i] > mid) {
                    i++;
                }
                cnt += j - i;
            }
            if (cnt >= k) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    @Test
    public void testJumpSort() {
        for (int i = 0; i < 1000; i++) {
            int n = new Random(System.nanoTime()).nextInt(100) + 10;
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) {
                arr[j] = j + 1;
            }
            shuffle(arr);

            int[] sortedArr = copyArr(arr);
            Arrays.sort(sortedArr);

            JumpSort.sort(arr);
            assert arr.length == sortedArr.length;
            for (int j = 0; j < arr.length; j++) {
                assert arr[j] == sortedArr[j];
            }
        }
    }

    @Test
    public void testKMin() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(10, 200);
            while (arr.length == 0) {
                arr = generateRandomArr(10, 200);
            }
            int[] copiedArr = copyArr(arr);
            int k = new Random(System.nanoTime()).nextInt(arr.length) + 1;

            int[] res1 = KMin.getKMin(arr, k);
            int[] res2 = getKMin(copiedArr, k);
            Arrays.sort(res1);
            assert res1.length == res2.length;
            for (int j = 0; j < res1.length; j++) {
                assert res1[j] == res2[j];
            }

        }
    }

    private int[] getKMin(int[] arr, int k) {
        Arrays.sort(arr);
        int[] res = new int[k];
        System.arraycopy(arr, 0, res, 0, k);
        return res;
    }


    @Test
    public void testLocalMin() {
        for (int i = 0; i < 1000; i++) {
            int n = new Random(System.nanoTime()).nextInt(100);
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) {
                arr[j] = j;
            }
            if (arr.length == 0) {
                assert LocalMin.getLessIndex(arr) == -1;
            } else if (arr.length == 1){
                assert LocalMin.getLessIndex(arr) == 0;
            } else {
                int localMinIndex = LocalMin.getLessIndex(arr);
                if (localMinIndex == 0) {
                    assert arr[localMinIndex] < arr[localMinIndex + 1];
                } else if (localMinIndex == arr.length - 1) {
                    assert arr[localMinIndex] < arr[localMinIndex - 1];
                } else {
                    assert arr[localMinIndex] < arr[localMinIndex - 1] && arr[localMinIndex] < arr[localMinIndex + 1];
                }
            }
        }
    }


    @Test
    public void testSubMatrixSumTarget() {
        for (int i = 0; i < 100; i++) {
            int[][] matrix = generateRandomMatrix(100, 100, 200);
            int[][] copiedMatrix = copyMatrix(matrix);
            int target = new Random(System.nanoTime()).nextInt(300) + 100;
            assert SubMatrixSumTarget.numSubMatrixSumTarget(matrix, target) == numSubmatrixSumTarget(copiedMatrix, target);
        }
    }


    //给出矩阵 matrix 和目标值 target，返回元素总和等于目标值的非空子矩阵的数量。
    //
    //子矩阵 x1, y1, x2, y2 是满足 x1 <= x <= x2 且 y1 <= y <= y2 的所有单元 matrix[x][y] 的集合。
    //
    //如果 (x1, y1, x2, y2) 和 (x1', y1', x2', y2') 两个子矩阵中部分坐标不同（如：x1 != x1'），那么这两个子矩阵也不同。
    private int numSubmatrixSumTarget(int[][] matrix, int target) {
        int ans = 0;
        for (int topRow=0; topRow<matrix.length; topRow++) {
            int[] sum = new int[matrix[0].length];
            for (int bottomRow=topRow; bottomRow<matrix.length; bottomRow++) {
                for (int i=0; i<matrix[0].length; i++) {
                    sum[i] += matrix[bottomRow][i];
                }

                ans += process(sum, target);
            }
        }
        return ans;
    }

    private int process(int[] sum, int target) {
        int ans = 0;
        int preSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int num : sum) {
            preSum += num;
            if (map.containsKey(preSum-target)) {
                ans += map.get(preSum-target);
            }
            if (map.containsKey(preSum)) {
                map.put(preSum, map.get(preSum) + 1);
            } else  {
                map.put(preSum, 1);
            }

        }
        return ans;
    }

    @Test
    public void testLongestOnes() {
        for (int i = 0; i < 1000; i++) {
            int length = new Random(System.nanoTime()).nextInt(100) + 1;
            int k = new Random(System.nanoTime()).nextInt(length + 1);
            int[] arr = new int[length];
            for (int j = 0; j < length; j++) {
                arr[j] = new Random().nextInt(2);
            }
            int[] copiedArr = copyArr(arr);
            assert LongestOnes.longestOnes(arr, k) == longestOnes(copiedArr, k);
        }
    }


    // 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
    private int longestOnes(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int zeros = 0;
        int res = 0;
        while (right < nums.length) {
            if (nums[right] == 0) {
                zeros++;
            }

            while (zeros > k) {
                if (nums[left]==0) {
                    zeros--;
                }
                left++;
            }

            res = Math.max(res, right - left + 1);
            right++;
        }
        return res;
    }

    @Test
    public void testNumSubArraysWithSum() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            int goal = new Random(System.nanoTime()).nextInt(250);
            assert SubArraysWithSum.numSubArraysWithSum(arr, goal) == numSubarraysWithSum(copiedArr, goal);
        }
    }

    // 给你一个二元数组 nums ，和一个整数 goal ，请你统计并返回有多少个和为 goal 的 非空 子数组。
    private int numSubarraysWithSum(int[] nums, int goal) {
        int[] preSum = new int[nums.length+1];
        for (int i=1; i<preSum.length; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }

        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i=1; i<preSum.length; i++) {
            if (map.containsKey(preSum[i-1])) {
                map.put(preSum[i-1], map.get(preSum[i-1])+1);
            } else {
                map.put(preSum[i-1], 1);
            }

            if (map.containsKey(preSum[i]-goal)) {
                ans += map.get(preSum[i]-goal);
            }


        }
        return ans;
    }


    @Test
    public void testShortestSubArray() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            int k = new Random(System.nanoTime()).nextInt(250);
            assert ShortestSubArray.shortestSubarray(arr, k) == shortestSubarray(copiedArr, k);
        }
    }


    // 给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。
    // 如果不存在这样的 子数组 ，返回 -1 。
    private int shortestSubarray(int[] nums, int k) {
        long[] preSum = new long[nums.length+1];
        for (int i=1; i<preSum.length;i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }

        int ans = Integer.MAX_VALUE;
        Deque<Integer> deque = new LinkedList<>();
        for (int i=0; i<=nums.length; i++) {
            while (!deque.isEmpty() && preSum[i] <= preSum[deque.getLast()]) {
                deque.removeLast();
            }

            while (!deque.isEmpty() && preSum[i] - preSum[deque.getFirst()] >= k) {
                ans = Math.min(ans, i - deque.removeFirst());
            }

            deque.addLast(i);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    @Test
    public void testCheckSubArraySum() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            int k = new Random(System.nanoTime()).nextInt(100) + 2;
            assert CheckSubArraySum.checkSubarraySum(arr, k) == checkSubarraySum(copiedArr, k);
        }
    }


    // 给你一个整数数组 nums 和一个整数 k ，编写一个函数来判断该数组是否含有同时满足下述条件的连续子数组：
    //
    //子数组大小 至少为 2 ，且
    //子数组元素总和为 k 的倍数。
    //如果存在，返回 true ；否则，返回 false 。
    //
    //如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。0 始终视为 k 的一个倍数。
    private boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int[] preSum = new int[nums.length+1];
        for (int i=1; i<=nums.length; i++) {
            preSum[i] = preSum[i-1] + nums[i-1];
        }

        Set<Integer> set = new TreeSet<>();
        for (int i=2; i<preSum.length; i++) {
            set.add(preSum[i-2] % k);
            if (set.contains(preSum[i] % k)) {
                return true;
            }
        }
        return false;
    }


    @Test
    public void testMaxSumSubMatrix() {
        for (int i = 0; i < 1000; i++) {
            int[][] matrix = generateRandomMatrix(10, 10, 200);
            int[][] copiedMatrix = copyMatrix(matrix);
            int k = new Random(System.nanoTime()).nextInt(250);
            assert SumSubMatrix.maxSumSubMatrix(matrix, k) == maxSumSubmatrix(copiedMatrix, k);
        }
    }


    // 给你一个 m x n 的矩阵 matrix 和一个整数 k ，找出并返回矩阵内部矩形区域的不超过 k 的最大数值和。
    //
    //题目数据保证总会存在一个数值和不超过 k 的矩形区域。
    private int maxSumSubmatrix(int[][] matrix, int k) {
        int ans = Integer.MIN_VALUE;
        for (int topRow=0; topRow < matrix.length; topRow++) {
            int[] sum = new int[matrix[0].length];
            for (int bottomRow=topRow; bottomRow<matrix.length; bottomRow++) {
                for (int i=0; i<matrix[0].length; i++) {
                    sum[i] += matrix[bottomRow][i];
                }
                TreeSet<Integer> set = new TreeSet<>();
                set.add(0);
                int s = 0;
                for (int i=0; i<sum.length; i++) {
                    s += sum[i];
                    Integer ceiling = set.ceiling(s - k);
                    if (ceiling != null) {
                        ans = Math.max(ans, s - ceiling);
                    }
                    set.add(s);
                }
            }
        }
        return ans;
    }

    @Test
    public void testMinSubArrayLen() {
        for (int i = 0; i < 1000; i++) {
            int[] arr =generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            int target = new Random(System.nanoTime()).nextInt(200);
            assert MinSubArrayLen.minSubArrayLen(target, arr) == minSubArrayLen(target, copiedArr);
        }
    }


    // 给定一个含有 n 个正整数的数组和一个正整数 target 。
    //
    //找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。
    // 如果不存在符合条件的子数组，返回 0 。
    private int minSubArrayLen(int target, int[] nums) {
        int minLen = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        int sum = 0;
        while (right < nums.length) {
            sum += nums[right++];

            while (left < right && sum >= target) {
                minLen = Math.min(minLen, right - left);
                sum -= nums[left++];
            }
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }


    @Test
    public void testSubArraysWithKDistinct() {
        for (int i = 0; i < 1000; i++) {
            int[] arr = generateRandomArr(100, 200);
            int[] copiedArr = copyArr(arr);
            int k = new Random(System.nanoTime()).nextInt(10) + 1;
            assert SubArraysWithKDistinct.subArraysWithKDistinct(arr, k) == subarraysWithKDistinct(copiedArr, k);
        }
    }


    // 给定一个正整数数组 nums和一个整数 k ，返回 num 中 「好子数组」 的数目。
    //
    //如果 nums 的某个子数组中不同整数的个数恰好为 k，则称 nums 的这个连续、不一定不同的子数组为 「好子数组 」。
    //
    //例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。
    private int subarraysWithKDistinct(int[] nums, int k) {
        return doSubarraysWithKDistinct(nums, k) - doSubarraysWithKDistinct(nums, k-1);
    }

    private int doSubarraysWithKDistinct(int[] nums, int k) {
        int left = 0;
        int right = 0;
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        while (right < nums.length) {
            if (!map.containsKey(nums[right])) {
                count++;
                map.put(nums[right], 1);
            } else {
                map.put(nums[right], map.get(nums[right]) + 1);
            }

            right++;

            while (count > k) {
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0) {
                    map.remove(nums[left]);
                    count--;
                }
                left++;
            }

            res += right - left;

        }
        return res;
    }
}
