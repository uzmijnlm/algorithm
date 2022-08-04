package org.example.arr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 * 注意：答案中不可以包含重复的三元组。
 */
public class ThreeSum {

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();

        Arrays.sort(nums);

        for (int first = 0; first < nums.length; first++) {
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }

            int left = first + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[first] + nums[left] + nums[right] == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[first]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    res.add(list);
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (nums[first] + nums[left] + nums[right] > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return res;
    }


    // 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
    //返回这三个数的和。
    //假定每组输入只存在恰好一个解。
    public static int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);

        int diff = Integer.MAX_VALUE;
        int res = 0;

        for (int first = 0; first < nums.length; first++) {
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }

            int left = first + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[first] + nums[left] + nums[right];
                if (sum == target) {
                    return target;
                }
                if (Math.abs(sum-target) < diff) {
                    diff = Math.abs(sum - target);
                    res = sum;
                }

                if (sum < target) {
                    while (left < right && nums[left] == nums[left+1]) {
                        left++;
                    }

                    left++;
                } else {
                    while (left < right && nums[right] == nums[right-1]) {
                        right--;
                    }
                    right--;
                }
            }
        }
        return res;
    }


    // 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
    // 请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
    //0 <= a, b, c, d < n
    //a、b、c 和 d 互不相同
    //nums[a] + nums[b] + nums[c] + nums[d] == target
    //你可以按 任意顺序 返回答案 。
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        if (nums == null || nums.length < 4) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();

        for (int first = 0; first < nums.length; first++) {
            if (first > 0 && nums[first] == nums[first-1]) {
                continue;
            }

            for (int second = first + 1; second < nums.length; second++) {
                if (second > first + 1 && nums[second] == nums[second-1]) {
                    continue;
                }

                int left = second + 1;
                int right = nums.length - 1;
                while (left < right) {
                    int sum = nums[first] + nums[second] + nums[left] + nums[right];
                    if (sum == target) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[first]);
                        list.add(nums[second]);
                        list.add(nums[left]);
                        list.add(nums[right]);
                        res.add(list);
                        while (left < right && nums[left]==nums[left+1]) {
                            left++;
                        }
                        while (left <right && nums[right]==nums[right-1]) {
                            right--;
                        }
                        left++;
                        right--;
                    } else if (sum > target) {
                        right--;
                    } else {
                        left++;
                    }
                }
            }
        }
        return res;
    }


    // 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列 ，
    // 请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
    //以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
    //你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
    //你所设计的解决方案必须只使用常量级的额外空间。
    public static int[] twoSum(int[] numbers, int target) {
        if (numbers == null || numbers.length < 2) {
            return new int[]{-1, -1};
        }

        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left+1, right+1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }
}
