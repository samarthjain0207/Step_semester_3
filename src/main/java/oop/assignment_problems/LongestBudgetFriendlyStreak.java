/**
 * Problem 2: Longest Budget-Friendly Streak (Sliding Window, variable size)
 *
 * Checking every run: O(n^2) time with a running sum (O(n^3) if each run is re-summed).
 * Final solution:     O(n) time (each index enters and leaves the window at most once),
 *                     O(1) extra space.
 * Why non-negative costs are essential: adding a day can never decrease the sum and
 * removing a day can never increase it. So when the sum exceeds the budget, moving the
 * left end is always the right fix, and a left index that was dropped never needs to be
 * revisited. With negative values this monotonic behaviour breaks and the window
 * would miss valid runs.
 */
public class LongestBudgetFriendlyStreak {

    static int[] longestStreak(int[] costs, long budget) {
        int bestLen = 0, bestStart = -1;
        int left = 0;
        long sum = 0;
        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];
            while (sum > budget && left <= right) sum -= costs[left++];
            int len = right - left + 1;
            if (len > bestLen) {          // strict '>' keeps the earliest start on ties
                bestLen = len;
                bestStart = left;
            }
        }
        return bestLen == 0 ? new int[]{0, -1} : new int[]{bestLen, bestStart};
    }

    static String fmt(int[] r) { return "(" + r[0] + ", " + r[1] + ")"; }

    public static void main(String[] args) {
        System.out.println(fmt(longestStreak(new int[]{4, 2, 1, 7, 3, 1, 2, 1, 5}, 8))); // (4, 4)
        System.out.println(fmt(longestStreak(new int[]{9, 10}, 8)));                     // (0, -1)
    }
}
