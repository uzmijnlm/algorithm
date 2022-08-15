package org.example.string;

import java.util.Random;

public class BaseTest {


    protected String generateRandomString(int lengthBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;
        char[] chars = new char[length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (random.nextInt(123 - 97) + 97);
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


}
