package kt9;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShieldedGridTest {
    @Test
    void choosesBestRouteAndLeavesInputUnchanged() {
        int[][] grid = {{1, -2}, {-3, 4}};
        assertEquals(5, ShieldedGrid.maxSum(grid, 1));
        assertArrayEquals(new int[][]{{1, -2}, {-3, 4}}, grid);
    }

    @Test
    void permitsShieldsOnStartAndFinish() {
        int[][] grid = {{-5, 2}, {1, -7}};
        assertEquals(2, ShieldedGrid.maxSum(grid, 2));
        assertEquals(-3, ShieldedGrid.maxSum(grid, 1));
    }

    @Test
    void handlesSingleCellAndUnusedShields() {
        assertEquals(-7, ShieldedGrid.maxSum(new int[][]{{-7}}, 0));
        assertEquals(0, ShieldedGrid.maxSum(new int[][]{{-7}}, 1));
        assertEquals(7, ShieldedGrid.maxSum(new int[][]{{7}}, 10));
        assertEquals(0, ShieldedGrid.maxSum(new int[][]{{0}}, 10));
        assertEquals(8, ShieldedGrid.maxSum(new int[][]{{1, 2}, {3, 4}}, 10));
    }

    @Test
    void usesAtMostTheAvailableNumberOfShields() {
        int[][] grid = {{-1, -2}, {-3, -4}};
        assertEquals(-7, ShieldedGrid.maxSum(grid, 0));
        assertEquals(-3, ShieldedGrid.maxSum(grid, 1));
        assertEquals(-1, ShieldedGrid.maxSum(grid, 2));
        assertEquals(0, ShieldedGrid.maxSum(grid, 10));
    }

    @Test
    void canSaveShieldForLaterInSingleRowOrColumn() {
        assertEquals(-1, ShieldedGrid.maxSum(new int[][]{{-1, -100}}, 1));
        assertEquals(-1, ShieldedGrid.maxSum(new int[][]{{-1}, {-100}}, 1));
    }

    @Test
    void shieldCanChangeTheBestRoute() {
        int[][] grid = {{0, -200, 100}, {-1, -1, 0}};
        assertEquals(-2, ShieldedGrid.maxSum(grid, 0));
        assertEquals(100, ShieldedGrid.maxSum(grid, 1));
    }

    @Test
    void handlesMaximumDimensionsAndLongSums() {
        int[][] grid = new int[500][500];
        for (int[] row : grid) {
            Arrays.fill(row, 1_000_000_000);
        }
        assertEquals(999_000_000_000L, ShieldedGrid.maxSum(grid, 10));
        for (int[] row : grid) {
            Arrays.fill(row, -1_000_000_000);
        }
        assertEquals(-989_000_000_000L, ShieldedGrid.maxSum(grid, 10));
    }

    @Test
    void matchesEnumerationOfPathsWithBestShieldsOnEachPath() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int[][] grid = new int[1 + random.nextInt(4)][1 + random.nextInt(4)];
            for (int[] row : grid) {
                for (int column = 0; column < row.length; column++) {
                    row[column] = random.nextInt(21) - 10;
                }
            }
            int k = random.nextInt(5);
            int[] path = new int[grid.length + grid[0].length - 1];
            assertEquals(enumerate(grid, 0, 0, path, k), ShieldedGrid.maxSum(grid, k));
        }
    }

    private long enumerate(int[][] grid, int row, int column, int[] path, int k) {
        path[row + column] = grid[row][column];
        if (row == grid.length - 1 && column == grid[0].length - 1) {
            int[] sorted = path.clone();
            Arrays.sort(sorted);
            long sum = 0;
            for (int value : sorted) {
                sum += value;
            }
            for (int i = 0; i < Math.min(k, sorted.length) && sorted[i] < 0; i++) {
                sum -= sorted[i];
            }
            return sum;
        }
        long best = Long.MIN_VALUE;
        if (row + 1 < grid.length) {
            best = enumerate(grid, row + 1, column, path, k);
        }
        if (column + 1 < grid[0].length) {
            best = Math.max(best, enumerate(grid, row, column + 1, path, k));
        }
        return best;
    }
}
