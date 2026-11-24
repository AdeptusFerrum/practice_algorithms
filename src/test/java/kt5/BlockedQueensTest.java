package kt5;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockedQueensTest {
    @Test
    void singleCellCanBeAllowedOrBlocked() {
        assertEquals(1, BlockedQueens.count(1, new int[0][2]));
        assertEquals(0, BlockedQueens.count(1, new int[][]{{1, 1}}));
    }

    @Test
    void countsPlacementsOnUnrestrictedBoards() {
        int[] expected = {1, 0, 0, 2, 10, 4, 40, 92};
        for (int n = 1; n <= expected.length; n++) {
            assertEquals(expected[n - 1], BlockedQueens.count(n, new int[0][2]), "n = " + n);
        }
    }

    @Test
    void blockedCellRemovesOnePlacement() {
        assertEquals(1, BlockedQueens.count(4, new int[][]{{1, 2}}));
    }

    @Test
    void fullyBlockedRowHasNoPlacement() {
        assertEquals(0, BlockedQueens.count(4, new int[][]{{3, 1}, {3, 2}, {3, 3}, {3, 4}}));
    }

    @Test
    void fullyBlockedBoardHasNoPlacement() {
        int[][] blocked = new int[14 * 14][2];
        for (int row = 1; row <= 14; row++) {
            for (int column = 1; column <= 14; column++) {
                blocked[(row - 1) * 14 + column - 1] = new int[]{row, column};
            }
        }
        assertEquals(0, BlockedQueens.count(14, blocked));
    }

    @Test
    void usesOneBasedCoordinatesWithoutChangingInput() {
        int[][] blocked = {{4, 2}, {1, 1}};
        assertEquals(1, BlockedQueens.count(4, blocked));
        assertArrayEquals(new int[][]{{4, 2}, {1, 1}}, blocked);
    }

    @Test
    void handlesMaximumBoardSize() {
        assertEquals(365_596, BlockedQueens.count(14, new int[0][2]));
    }
}
