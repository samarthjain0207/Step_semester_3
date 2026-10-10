import java.util.*;

/**
 * Problem 5: Spiral Stock Audit Route (Boundary Traversal)
 *
 * Time:  O(m * n), every cell is visited exactly once.
 * Space: O(1) additional, excluding the output list (only four boundary variables).
 * The checks before the bottom row and left column stop a single remaining row or
 * column from being walked twice.
 */
public class SpiralStockAuditRoute {

    static List<Integer> auditRoute(int[][] grid) {
        List<Integer> route = new ArrayList<>();
        if (grid.length == 0 || grid[0].length == 0) return route;

        int top = 0, bottom = grid.length - 1;
        int left = 0, right = grid[0].length - 1;

        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) route.add(grid[top][c]);       // top row  ->
            top++;
            for (int r = top; r <= bottom; r++) route.add(grid[r][right]);     // right col down
            right--;
            if (top <= bottom) {
                for (int c = right; c >= left; c--) route.add(grid[bottom][c]); // bottom row <-
                bottom--;
            }
            if (left <= right) {
                for (int r = bottom; r >= top; r--) route.add(grid[r][left]);   // left col up
                left++;
            }
        }
        return route;
    }

    public static void main(String[] args) {
        int[][] grid = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        System.out.println(auditRoute(grid));   // [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]
    }
}
