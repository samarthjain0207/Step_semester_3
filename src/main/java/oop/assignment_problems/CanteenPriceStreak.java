import java.util.Arrays;

public class CanteenPriceStreak {
    static int[] priceStreak(int[] price) {
        int n = price.length;
        int[] streak = new int[n];
        int[] stack = new int[n];
        int top = -1;
        for (int i = 0; i < n; i++) {
            while (top >= 0 && price[stack[top]] <= price[i]) top--;
            streak[i] = top < 0 ? i + 1 : i - stack[top];
            stack[++top] = i;
        }
        return streak;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(priceStreak(new int[]{100, 80, 60, 70, 60, 75, 85})));
        System.out.println(Arrays.toString(priceStreak(new int[]{10, 20, 30})));
    }
}
