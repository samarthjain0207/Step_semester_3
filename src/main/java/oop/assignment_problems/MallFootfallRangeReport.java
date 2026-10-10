import java.util.*;

/**
 * Problem 1: Mall Footfall Range Report (Prefix Sums)
 *
 * Naive:     O(q * n) time, O(1) extra space (re-add the range for every query).
 * Optimized: O(n) preprocessing + O(1) per query = O(n + q) total time,
 *            O(n) extra space for the prefix array.
 * Worth it when there are many queries on the same data (q is large): the one-time
 * O(n) cost is paid back after a handful of queries. For one or two queries, a plain
 * loop is just as good.
 */
public class MallFootfallRangeReport {

    static List<Long> footfallReport(int[] visitors, int[][] queries) {
        int n = visitors.length;
        long[] prefix = new long[n + 1];                  // prefix[i] = sum of visitors[0..i-1]
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + visitors[i];

        List<Long> result = new ArrayList<>(queries.length);
        for (int[] q : queries)
            result.add(prefix[q[1] + 1] - prefix[q[0]]);  // the +1 offset handles start == 0
        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};
        System.out.println(footfallReport(visitors, queries));   // [22, 31, 27, 9]
    }
}
