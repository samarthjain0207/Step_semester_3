import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class FireDrillExits {
    static int[][] exitDistance(char[][] plan) {
        int rows = plan.length, cols = plan[0].length;
        int[][] dist = new int[rows][cols];
        Queue<int[]> queue = new ArrayDeque<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (plan[r][c] == 'E') {
                    dist[r][c] = 0;
                    queue.add(new int[]{r, c});
                } else {
                    dist[r][c] = -1;
                }
            }
        }
        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cell[0] + dr[d], nc = cell[1] + dc[d];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && plan[nr][nc] == '.' && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[cell[0]][cell[1]] + 1;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        char[][] plan = {
            {'.', '#', '.', 'E'},
            {'.', '.', '.', '#'},
            {'E', '#', '.', '.'}
        };
        for (int[] row : exitDistance(plan)) System.out.println(Arrays.toString(row));
    }
}
