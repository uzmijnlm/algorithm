package org.example.string.manacher;

/**
 * 给定一个字符串，返回str中最长回文子串的长度
 * 要求：时间复杂度O(N)
 */
public class Manacher {

    // 基本思路就是遍历每个字符，找到每个字符的回文半径。不过Manacher算法对这个过程有加速。步骤如下：
    // 1.先将字符串变换成"#a#b#c#a#c#"的形式（每两个字符间插入特殊符号，保证长度无论是奇数还是偶数都能用同样方式处理）
    // 2.引入几个概念：
    //   pArr数组，pArr[i]的意义是以i位置上的字符作为回文中心，最大回文半径是多少
    //   变量r，表示遍历过程中所有回文半径中最右的位置
    //   变量c，表示最右半径对应的回文中心（显然这个变量会跟r一起更新）
    // 3.遍历过程中，记当前遍历到的位置是i，右边界是r，r对应的中心是c，那么i关于c的对称点是i`，左边界是l。有以下几种情况：
    //   3.1 i超过了r。比如一开始r为-1，那么0位置肯定在r的外部。此时只能老老实实向左右扩
    //   3.2 i`的半径在l以内，那么i的半径一定也不超过r，此时不用更新
    //   3.3 i`的半径超过了l，此时也不用更新
    //   3.4 i`的半径刚好落在l上，此时i需要从r的下一个位置开始比较
    // 4.基本思路如上，但是在实现时这样实现：
    //   由于3.2和3.3都不用扩，3.4也有一部分不用考虑，是直接从r外面开始判断，因此在代码实现上不需要分成4个条件分支来做
    //   可以先计算出不用考虑的那部分回文半径，然后从下一位开始判断。对于不需要再判断的那些情况，会直接跳出循环
    public static int manacher(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }

        char[] manacher = manacherString(str);
        int[] pArr = new int[manacher.length];
        int max = 0;
        int c = 0;
        int r = 0;
        for (int i = 0; i < manacher.length; i++) {
            pArr[i] = r > i ? Math.min(pArr[2 * c - i], r - i) : 1;

            while (i + pArr[i] < manacher.length && i - pArr[i] >= 0) {
                if (manacher[i+pArr[i]] == manacher[i-pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }


            if (i + pArr[i] > r) {
                r = i + pArr[i];
                c = i;
            }

            max = Math.max(max, pArr[i]);
        }


        return max - 1;
    }

    private static char[] manacherString(String str) {
        char[] chars = str.toCharArray();
        char[] manacher = new char[chars.length * 2 + 1];
        int index = 0;
        while (index < manacher.length) {
            if ((index & 1) == 0) {
                manacher[index] = '#';
            } else {
                manacher[index] = chars[index / 2];
            }
            index++;
        }
        return manacher;
    }


    // 进阶问题：在字符串的最后添加最少字符，使整个字符串都成为回文串
    // 思路：查找必须要包含最后一个字符的情况下，最长的回文子串是什么，之前不是最长回文子串的部分逆序后添加在末尾就构成了结果
    // 在Manacher算法的思路下，从左到右计算回文半径时，关注回文半径最右即将到达的位置r，一旦发现已经到达最后，直接退出检查
    public static String shortestEnd(String str) {
        char[] manacher = manacherString(str);
        int[] pArr = new int[manacher.length];
        int c = -1;
        int r = -1;
        int maxContainsEnd = Integer.MIN_VALUE;
        for (int i = 0; i < manacher.length; i++) {
            pArr[i] = r > i ? Math.min(pArr[2 * c - i], r - i) : 1;

            while (i + pArr[i] < manacher.length && i - pArr[i] >= 0) {
                if (manacher[i + pArr[i]] == manacher[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            if (i + pArr[i] > r) {
                r = i + pArr[i];
                c = i;
            }

            if (i + pArr[i] == manacher.length) {
                maxContainsEnd = pArr[i];
                break;
            }
        }

        char[] res = new char[str.length() - (maxContainsEnd - 1)];
        int index = 0;
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = str.charAt(index++);
        }
        return String.valueOf(res);
    }
}
