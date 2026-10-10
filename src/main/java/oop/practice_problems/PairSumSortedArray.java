import java.util.Arrays;

/**
 * Problem 1: Pair Sum in a Sorted Array (Two Pointers)
 *
 * Time:  O(n) - each pointer only moves inward, so at most n steps in total.
 * Space: O(1) - just two indices.
 * Ignoring the sorted property: brute force is O(n^2) time, O(1) space; a hash set
 * gets O(n) time but needs O(n) extra space. Two pointers gets O(n) time AND O(1) space.
 */
public class PairSumSortedArray {

    // returns the pair of values, or null if no two distinct elements add up to target
    static int[] pairSumSorted(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {                       // left < right keeps the two elements distinct
            long sum = (long) nums[left] + nums[right];
            if (sum == target) return new int[]{nums[left], nums[right]};
            if (sum < target) left++;                // need a bigger sum
            else right--;                            // need a smaller sum
        }
        return null;
    }

    static String show(int[] pair) {
        return pair == null ? "Not Found" : "(" + pair[0] + ", " + pair[1] + ")";
    }

    public static void main(String[] args) {
        System.out.println(show(pairSumSorted(new int[]{-4, -1, 0, 3, 5, 9}, 4)));  // (-1, 5)
        System.out.println(show(pairSumSorted(new int[]{1, 2, 3}, 100)));           // Not Found
    }
}
