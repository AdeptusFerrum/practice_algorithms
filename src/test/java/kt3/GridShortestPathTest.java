package kt3;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GridShortestPathTest {
    @Test
    void findsShortestPathAroundWall() {
        assertEquals(4, GridShortestPath.find(grid("S..", ".#.", "..T")));
    }

    @Test
    void unreachableTarget() {
        assertEquals(-1, GridShortestPath.find(grid("S#T")));
    }

    @Test
    void adjacentCellsInRowAndColumn() {
        assertEquals(1, GridShortestPath.find(grid("ST")));
        assertEquals(1, GridShortestPath.find(grid("S", "T")));
    }

    @Test
    void pathCanMoveAwayFromTarget() {
        assertEquals(4, GridShortestPath.find(grid("S#T", "...")));
    }

    @Test
    void diagonalMoveIsNotAllowed() {
        assertEquals(-1, GridShortestPath.find(grid("S#", "#T")));
    }

    @Test
    void findsStartOutsideFirstCellWithoutChangingMap() {
        char[][] map = grid("..T", ".#.", "S..");
        assertEquals(4, GridShortestPath.find(map));
        assertArrayEquals(grid("..T", ".#.", "S.."), map);
    }

    @Test
    void handlesMaximumGridSize() {
        char[][] map = new char[1_000][1_000];
        for (char[] row : map) {
            Arrays.fill(row, '.');
        }
        map[0][0] = 'S';
        map[999][999] = 'T';
        assertEquals(1_998, GridShortestPath.find(map));
    }

    private char[][] grid(String... rows) {
        return Arrays.stream(rows).map(String::toCharArray).toArray(char[][]::new);
    }
}
