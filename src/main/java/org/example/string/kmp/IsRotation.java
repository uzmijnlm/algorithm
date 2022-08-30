package org.example.string.kmp;

/**
 * 两个字符串是否互为旋转词，即把一个字符串前面任意部分挪到后面形成的字符串与另一个字符串相等
 * 如"cdab"和"abcd"、"2ab1"和"ab12"都互为旋转词
 */
public class IsRotation {

    public boolean isRotation(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        if (a.length() == 0 && b.length() == 0) {
            return true;
        }
        String b2 = b+b;
        return getIndexOf(b2, a) != -1;
    }

    // KMP算法
    private int getIndexOf(String str1, String str2) {
        return KMP.getIndexOf(str1, str2);
    }
}
