package org.example.string;

import java.util.Random;

public class BaseTest {


    protected String generateRandomString(int lengthBound) {
        return generateRandomString(lengthBound, false);
    }

    protected String generateRandomString(int lengthBound, boolean upper) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;
        char[] chars = new char[length];
        if (upper) {
            for (int i = 0; i < chars.length; i++) {
                chars[i] = (char) (random.nextInt(26) + 'A');
            }
        } else {
            for (int i = 0; i < chars.length; i++) {
                chars[i] = (char) (random.nextInt(26) + 'a');
            }
        }
        return String.valueOf(chars);
    }

    protected String generateRandomString(int lengthLowerBound, int lengthHigherBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthHigherBound) + lengthLowerBound;
        char[] chars = new char[length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (random.nextInt(123 - 97) + 97);
        }
        return String.valueOf(chars);
    }

    protected String generateRandomStringWithFixedLength(int length) {
        Random random = new Random(System.nanoTime());
        char[] chars = new char[length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (random.nextInt(123 - 97) + 97);
        }
        return String.valueOf(chars);
    }

    protected String copyStr(String str) {
        char[] chars = str.toCharArray();
        char[] copiedChars = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            copiedChars[i] = chars[i];
        }
        return String.valueOf(copiedChars);
    }

    protected String reverse(String str) {
        int left = 0;
        int right = str.length() - 1;
        char[] chs = str.toCharArray();
        while (left < right) {
            char tmp = chs[left];
            chs[left] = chs[right];
            chs[right] = tmp;
            left++;
            right--;
        }
        return String.valueOf(chs);
    }


}
