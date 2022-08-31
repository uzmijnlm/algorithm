package org.example.arr.twopointer;

/**
 * 给你一个有序数组 nums ，请你原地删除重复出现的元素，使得出现次数超过两次的元素只出现两次，返回删除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 */
public class RemoveDup {

    // slow表示修改后的数组的最新的位置
    // fast表示原数组的指针
    // 之所以用slow-2跟fast比较，是因为可以出现2次，所以slow-1是否与fast相等无所谓，只要slow-2不相等，fast的值就应该保留下来
    // 同理，如果题意要求出现3次，则用slow-3比较即可
    public static int removeDup(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int slow = 0;
        int fast = 0;
        while (fast < nums.length) {
            if (slow < 2 || nums[slow-2] != nums[fast]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }
}
