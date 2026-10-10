/**
 * Problem 4: Exam Score Band Counter (Binary Search: lower/upper bound)
 *
 * Linear scan: O(n) time per query, O(1) space.
 * Final:       O(log n) time per query, O(1) extra space (two binary searches).
 * Duplicates: a standard binary search can stop on ANY copy of a value, which may be in
 * the middle of a run of equal scores. To count a band exactly we need the boundaries:
 *   first index with score >= low   (lower bound)
 *   first index with score >  high  (upper bound)
 * so the answer is upper - lower, with all copies of low and high included.
 */
public class ExamScoreBandCounter {

    // first index i such that scores[i] >= target (n if none)
    private static int firstAtLeast(int[] scores, int target) {
        int lo = 0, hi = scores.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (scores[mid] >= target) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    static int countInBand(int[] scores, int low, int high) {
        // first score > high is the same as first score >= high + 1 (integer scores)
        return firstAtLeast(scores, high + 1) - firstAtLeast(scores, low);
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        System.out.println(countInBand(scores, 42, 58));   // 6
        System.out.println(countInBand(scores, 90, 100));  // 0
    }
}
