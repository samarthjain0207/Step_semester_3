/**
 * Problem 2: Warehouse Bin Grid Scan (2D array traversal)
 *
 * Time:  O(m * n) - every cell is read exactly once.
 * Space: O(1) additional - a running total and the best value/row/col.
 * Strict '>' keeps the FIRST maximum in row-major (top-to-bottom, left-to-right) order.
 */
public class WarehouseBinGridScan {

    static class Summary {
        final long total; final int row, col;
        Summary(long total, int row, int col) { this.total = total; this.row = row; this.col = col; }
        @Override public String toString() { return "total = " + total + ", maxCoordinate = (" + row + ", " + col + ")"; }
    }

    static Summary warehouseSummary(int[][] grid) {
        long total = 0;
        int best = -1, bestRow = -1, bestCol = -1;   // all counts are >= 0, so -1 is safe
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                total += grid[r][c];
                if (grid[r][c] > best) { best = grid[r][c]; bestRow = r; bestCol = c; }
            }
        }
        return new Summary(total, bestRow, bestCol);
    }

    public static void main(String[] args) {
        int[][] grid = {{4, 9, 2}, {7, 1, 6}, {3, 12, 5}};
        System.out.println(warehouseSummary(grid));   // total = 49, maxCoordinate = (2, 1)
    }
}
