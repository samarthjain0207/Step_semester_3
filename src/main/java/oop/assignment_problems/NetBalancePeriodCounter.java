import java.util.*;

/**
 * Problem 3: Net-Balance Period Counter (Prefix Sum + Hash Map)
 *
 * Brute force: O(n^2) time with a running sum per start (O(n^3) if each subarray is
 *              re-summed), O(1) extra space.
 * Optimized:   O(n) time (expected, hash map operations), O(n) extra space.
 *
 * Idea: if S is the running total up to day j, a period ending at j sums to k exactly
 * for every earlier prefix total equal to S - k. Keep a count of each prefix total seen.
 * Seed with {0: 1} so periods starting at index 0 are counted.
 * Prefix sums use long to be safe against overflow.
 */
public class NetBalancePeriodCounter {

    static long countPeriods(int[] transactions, long k) {
        Map<Long, Integer> seen = new HashMap<>();
        seen.put(0L, 1);
        long sum = 0, count = 0;
        for (int t : transactions) {
            sum += t;
            count += seen.getOrDefault(sum - k, 0);
            seen.merge(sum, 1, Integer::sum);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countPeriods(new int[]{3, 4, -7, 1, 3, 3, 1, -4}, 7)); // 4
        System.out.println(countPeriods(new int[]{1, 2, 3}, 10));                 // 0
    }
}
