public class ProductPriceFinder {
    /** First index with prices[i] >= target. */
    static int lowerBound(int[] a, int target) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] < target) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
    /** First index with prices[i] > target. */
    static int upperBound(int[] a, int target) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid] <= target) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    public static void main(String[] args) {
        int[] prices = {100, 150, 150, 200, 300, 450};
        int low = 150, high = 300;
        int l = lowerBound(prices, low), u = upperBound(prices, high);
        System.out.println("Lower bound index " + l);
        System.out.println("Upper bound index " + u);
        System.out.println("Count " + (u - l));
    }
}
