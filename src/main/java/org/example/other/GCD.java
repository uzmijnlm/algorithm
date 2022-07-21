package org.example.other;

/**
 * 给定两个不等于0的整数M和N，求最大公约数
 */
public class GCD {

    // 思路：如果q和r分别是m除以n的商和余数，即m=nq+r，那么m和n的最大公约数等于n和r的最大公约数。证明略
    public int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }
}
