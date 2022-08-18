package org.example.string.twopointer;

/**
 * 给你一个字符数组 chars ，请使用下述算法压缩：
 * 从一个空字符串 s 开始。对于 chars 中的每组 连续重复字符 ：
 * 如果这一组长度为 1 ，则将字符追加到 s 中。
 * 否则，需要向 s 追加字符，后跟这一组的长度。
 * 压缩后得到的字符串 s 不应该直接返回 ，需要转储到字符数组 chars 中。
 * 需要注意的是，如果组长度为 10 或 10 以上，则在 chars 数组中会被拆分为多个字符。
 * 请在 修改完输入数组后 ，返回该数组的新长度。
 * 你必须设计并实现一个只使用常量额外空间的算法来解决此问题。
 */
public class Compress {


    // 思路：
    // 令输入数组 cs 长度为 n。
    // 使用两个指针 i 和 j 分别指向「当前处理到的位置」和「答案待插入的位置」：
    // i 指针一直往后处理，每次找到字符相同的连续一段 [i,idx)，令长度为 cnt；
    // 将当前字符插入到答案，并让 j 指针后移：cs[j++] = cs[i]；
    // 检查长度 cnt 是否大于 1，如果大于 1，需要将数字拆分存储。
    // 由于简单的实现中，我们只能从个位开始处理 cnt，因此需要使用 start 和 end 记录下存储数字的部分，再处理完 cnt 后，将 [start,end) 部分进行翻转，并更新 j 指针；
    // 更新 i 为 idx，代表循环处理下一字符。
    public static int compress(char[] chars) {
        int n = chars.length;
        int left = 0;
        int right = 0;
        while (right < n) {
            int index = right;
            while (index < n && chars[index] == chars[right]) {
                index++;
            }
            int cnt = index - right;
            chars[left++] = chars[right];
            if (cnt > 1) {
                int start = left;
                int end = start;
                while (cnt != 0) {
                    chars[end++] = (char) ((cnt % 10) + '0');
                    cnt /= 10;
                }
                reverse(chars, start, end - 1);
                left = end;
            }
            right = index;
        }
        return left;
    }

    private static void reverse(char[] chars, int start, int end) {
        while (start < end) {
            char tmp = chars[start];
            chars[start] = chars[end];
            chars[end] = tmp;
            start++;
            end--;
        }
    }

}
