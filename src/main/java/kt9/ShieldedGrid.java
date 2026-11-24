package kt9;
import java.util.Arrays;

public class ShieldedGrid {
    public static long maxSum(int[][] grid, int maxShields) {
        int columns = grid[0].length;
        long[][] best = new long[columns][maxShields + 1];
        for (long[] states : best) {
            Arrays.fill(states, Long.MIN_VALUE);
        }
        best[0][0] = grid[0][0];
        if (grid[0][0] < 0 && maxShields > 0) {
            best[0][1] = 0;
        }
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < columns; column++) {
                if (row == 0 && column == 0) {
                    continue;
                }
                // Обратный порядок сохраняет состояние сверху с одним экраном меньше.
                for (int used = maxShields; used >= 0; used--) {
                    long previous = best[column][used];
                    if (column > 0) {
                        previous = Math.max(previous, best[column - 1][used]);
                    }
                    long value = previous == Long.MIN_VALUE ? Long.MIN_VALUE : previous + grid[row][column];
                    if (grid[row][column] < 0 && used > 0) {
                        long shielded = best[column][used - 1];
                        if (column > 0) {
                            shielded = Math.max(shielded, best[column - 1][used - 1]);
                        }
                        value = Math.max(value, shielded);
                    }
                    best[column][used] = value;
                }
            }
        }
        long answer = Long.MIN_VALUE;
        for (long value : best[columns - 1]) {
            answer = Math.max(answer, value);
        }
        return answer;
    }
}
