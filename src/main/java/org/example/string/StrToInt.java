package org.example.string;

/**
 * 将字符串转换成整数：如果字符串符合日常书写的整数形式，且在32位整数范围内，返回其所代表的整数值，否则返回0
 * 如"123"，返回123
 * "023"，由于不符合日常书写习惯，返回0
 * "A13"，返回0
 * "-123"，返回-123
 * "2147483648"，溢出，返回0
 */
public class StrToInt {


    public int convert(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chars = str.toCharArray();
        if (!isValid(chars)) {
            return 0;
        }

        return doConvert(chars);
    }

    // 核心思路是由于32位整数的负数范围比正数更大，所以先将字符串转换成负数，然后根据它前面是否有符号，返回最终的结果
    private int doConvert(char[] chars) {
        boolean isPositive = chars[0] != '-';
        int startIndex = isPositive ? 0 : 1;

        int minValueDiv10 = Integer.MIN_VALUE / 10;
        int minValueMod10 = Integer.MIN_VALUE % 10;

        int cur;
        int res = 0;
        for (int i = startIndex; i < chars.length; i++) {
            cur = '0' - chars[i];
            if (res < minValueDiv10 || (res == minValueDiv10 && cur < minValueMod10)) {
                return 0;
            }
            res = res * 10 + cur;
        }
        if (isPositive && res == Integer.MIN_VALUE) {
            return 0;
        }
        return isPositive ? -res : res;
    }


    // 判断是否是符合日常书写习惯的整数形式
    private boolean isValid(char[] chars) {
        // 1.如果不以"-"开头，且不以数字字符开头，直接返回false
        if (chars[0] != '-' && (chars[0] < '0' || chars[0] > '9')) {
            return false;
        }

        // 2.以"-"开头，但字符串长度为1，返回false；以"-"开头，字符串长度不为1，但"-"后紧跟"0"，返回false
        if (chars[0] == '-' && (chars.length == 1 || chars[1] == '0')) {
            return false;
        }

        // 3.以"0"开头，但是字符串长度大于1，返回false
        if (chars[0] == '0' && chars.length > 1) {
            return false;
        }

        // 4.检查每一个字符，如果都是数字字符则返回true，否则返回false
        for (char c : chars) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
