package org.example.arr.binarysearch;

/**
 * 传送带上的包裹必须在 days 天内从一个港口运送到另一个港口。
 * 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量（weights）的顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。
 * 返回能在 days 天内将传送带上的所有包裹送达的船的最低运载能力。
 */
public class ShipWithinDays {


    public static int shipWithinDays(int[] ws, int d) {
        int left = Integer.MIN_VALUE;
        int right = 0;
        for (int weight : ws) {
            left = Math.max(left, weight);
            right += weight;
        }

        while (left < right) {
            int mid = (left + right) / 2;
            int days = calculateDays(ws, mid);
            if (days <= d) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    private static int calculateDays(int[] ws, int load) {
        int sum = 0;
        int days = 0;
        int index = 0;
        while (index < ws.length) {
            while (index < ws.length && sum + ws[index] <= load) {
                sum += ws[index];
                index++;
            }
            days++;
            sum = 0;
        }
        return days;
    }
}
