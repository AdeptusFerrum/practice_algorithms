package kt3;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MaximalRectangleTest {
    @Test
    void findsRectangleAcrossRows() {
        assertEquals(6, MaximalRectangle.maxArea(grid("10100", "10111", "11111", "10010")));
    }

    @Test
    void zeroMatrixAndSingleCell() {
        assertEquals(0, MaximalRectangle.maxArea(grid("000", "000")));
        assertEquals(0, MaximalRectangle.maxArea(grid("0")));
        assertEquals(1, MaximalRectangle.maxArea(grid("1")));
    }

    @Test
    void singleRowAndColumn() {
        assertEquals(3, MaximalRectangle.maxArea(grid("110111")));
        assertEquals(2, MaximalRectangle.maxArea(grid("1", "1", "0", "1")));
    }

    @Test
    void handlesEqualHeights() {
        assertEquals(6, MaximalRectangle.maxArea(grid("111", "111")));
    }

    @Test
    void resetsHeightAtZeroWithoutChangingInput() {
        char[][] matrix = grid("111", "101", "111");
        assertEquals(3, MaximalRectangle.maxArea(matrix));
        assertArrayEquals(grid("111", "101", "111"), matrix);
    }

    @Test
    void handlesMaximumArea() {
        char[][] matrix = new char[1_000][2_000];
        for (char[] row : matrix) {
            Arrays.fill(row, '1');
        }
        assertEquals(2_000_000, MaximalRectangle.maxArea(matrix));
    }

    private char[][] grid(String... rows) {
        return Arrays.stream(rows).map(String::toCharArray).toArray(char[][]::new);
    }
}
