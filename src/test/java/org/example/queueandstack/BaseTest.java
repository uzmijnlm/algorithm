package org.example.queueandstack;

import java.util.*;

public class BaseTest {


    protected int[] generateRandomArr(int lengthBound, int maxBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;
        int max = random.nextInt(maxBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = random.nextInt(max);
        }
        return arr;
    }

    protected int[] generateRandomArr(int lengthBound, int lowerBound, int upperBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            arr[j] = random.nextInt(upperBound - lowerBound) + lowerBound;
        }
        return arr;
    }

    protected int[] generateRandomArrWithoutDup(int lengthBound, int maxBound) {
        if (maxBound < lengthBound) {
            throw new RuntimeException("length too long");
        }

        Set<Integer> set = new HashSet<>();
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;

        int[] arr = new int[length];
        for (int j = 0; j < arr.length; j++) {
            int num = random.nextInt(maxBound);
            while (set.contains(num)) {
                num = random.nextInt(maxBound);
            }
            arr[j] = num;
            set.add(num);
        }
        return arr;
    }

    protected void shuffle(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int num : arr) {
            list.add(num);
        }
        Collections.shuffle(list, new Random(System.nanoTime()));
        for (int i = 0; i < arr.length; i++) {
            arr[i] = list.get(i);
        }
    }

    protected int[] getRandomSubArray(int[] arr) {
        int length = arr.length;
        int subLength = new Random(System.nanoTime()).nextInt(length) + 1;
        int[] subArr = new int[subLength];

        Set<Integer> pickedIndexes = new HashSet<>();
        for (int i = 0; i < subArr.length; i++) {
            int index = new Random(System.nanoTime()).nextInt(length);
            while (pickedIndexes.contains(index)) {
                index = new Random(System.nanoTime()).nextInt(length);
            }
            subArr[i] = arr[index];
            pickedIndexes.add(index);
        }

        return subArr;
    }

    protected int[] copyArr(int[] arr) {
        int[] arr1 = new int[arr.length];
        System.arraycopy(arr, 0, arr1, 0, arr.length);
        return arr1;
    }


    protected String generatePath(int levelBound) {
        int level = new Random(System.nanoTime()).nextInt(levelBound);
        if (level == 0) {
            return "/";
        }
        StringBuilder sb = new StringBuilder();
        while (level > 0) {
            sb.append("/");
            int random = new Random(System.nanoTime()).nextInt(5);
            if (random == 0) {
                sb.append(".");
            } else if (random == 1) {
                sb.append("..");
            } else if (random == 2) {
                sb.append("/");
            } else {
                sb.append(generatePath());
            }
            level--;
        }
        return sb.toString();
    }

    private String generatePath() {
        int length = new Random(System.nanoTime()).nextInt(4) + 1;
        char[] chs = new char[length];
        for (int i = 0; i < length; i++) {
            chs[i] = (char) (new Random(System.nanoTime()).nextInt(26) + 'a');
        }
        return String.valueOf(chs);
    }

    protected String copyStr(String str) {
        char[] chars = str.toCharArray();
        char[] copiedChars = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            copiedChars[i] = chars[i];
        }
        return String.valueOf(copiedChars);
    }

    protected String generateRandomString(int lengthBound) {
        return generateRandomString(lengthBound, false);
    }

    protected String generateRandomNumString(int lengthBound) {
        Random random = new Random(System.nanoTime());
        int length = random.nextInt(lengthBound) + 1;
        char[] chars = new char[length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (random.nextInt(10) + '0');
        }
        return String.valueOf(chars);
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


}
