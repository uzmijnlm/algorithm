package org.example.arr;

import java.util.Arrays;

/**
 * 冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
 * 在加热器的加热半径范围内的每个房屋都可以获得供暖。
 * 现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置，请你找出并返回可以覆盖所有房屋的最小加热半径。
 * 说明：所有供暖器都遵循你的半径标准，加热的半径也一样。
 */
public class FindRadius {


    public static int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);

        int houseIndex = 0;
        int heaterIndex = 0;
        int ans = 0;
        while (houseIndex < houses.length) {
            int distance = Math.abs(houses[houseIndex] - heaters[heaterIndex]);
            while (heaterIndex < heaters.length - 1 && Math.abs(houses[houseIndex] - heaters[heaterIndex + 1]) <= distance) {
                heaterIndex++;
                distance = Math.abs(houses[houseIndex] - heaters[heaterIndex]);
            }
            ans = Math.max(ans, distance);
            houseIndex++;
        }
        return ans;
    }
}
