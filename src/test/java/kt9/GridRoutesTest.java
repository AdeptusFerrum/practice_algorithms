package kt9;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GridRoutesTest {
    @Test
    void countsRoutesAroundObstaclesWithoutChangingGrid() {
        char[][] grid = {"...".toCharArray(), ".#.".toCharArray(), "...".toCharArray()};
        assertEquals(2, GridRoutes.count(grid));
        assertArrayEquals(new char[][]{"...".toCharArray(), ".#.".toCharArray(), "...".toCharArray()}, grid);
    }

    @Test
    void blockedStartOrFinishGivesZero() {
        assertEquals(0, GridRoutes.count(new char[][]{"#.".toCharArray(), "..".toCharArray()}));
        assertEquals(0, GridRoutes.count(new char[][]{"..".toCharArray(), ".#".toCharArray()}));
    }

    @Test
    void handlesSingleCellAndSingleRowOrColumn() {
        assertEquals(1, GridRoutes.count(new char[][]{{'.'}}));
        assertEquals(0, GridRoutes.count(new char[][]{{'#'}}));
        assertEquals(1, GridRoutes.count(new char[][]{"....".toCharArray()}));
        assertEquals(0, GridRoutes.count(new char[][]{"..#.".toCharArray()}));
        assertEquals(1, GridRoutes.count(new char[][]{{'.'}, {'.'}, {'.'}}));
        assertEquals(0, GridRoutes.count(new char[][]{{'.'}, {'#'}, {'.'}}));
    }

    @Test
    void impassableBarrierStopsAllRoutes() {
        assertEquals(0, GridRoutes.count(new char[][]{"...".toCharArray(), "###".toCharArray(), "...".toCharArray()}));
    }

    @Test
    void handlesMaximumAreaAndCountsModuloPrime() {
        char[][] grid = new char[2_000][1_000];
        for (char[] row : grid) {
            Arrays.fill(row, '.');
        }
        BigInteger routes = BigInteger.ONE;
        for (int i = 1; i <= 999; i++) {
            routes = routes.multiply(BigInteger.valueOf(1_999 + i)).divide(BigInteger.valueOf(i));
        }
        int expected = routes.mod(BigInteger.valueOf(1_000_000_007)).intValue();
        assertEquals(expected, GridRoutes.count(grid));
    }

    @Test
    void matchesEnumerationOfSmallRoutes() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            char[][] grid = new char[1 + random.nextInt(5)][1 + random.nextInt(5)];
            for (char[] row : grid) {
                for (int column = 0; column < row.length; column++) {
                    row[column] = random.nextInt(4) == 0 ? '#' : '.';
                }
            }
            assertEquals(enumerate(grid, 0, 0), GridRoutes.count(grid));
        }
    }

    private int enumerate(char[][] grid, int row, int column) {
        if (row == grid.length || column == grid[0].length || grid[row][column] == '#') {
            return 0;
        }
        if (row == grid.length - 1 && column == grid[0].length - 1) {
            return 1;
        }
        return enumerate(grid, row + 1, column) + enumerate(grid, row, column + 1);
    }
}
