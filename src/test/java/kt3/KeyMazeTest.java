package kt3;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyMazeTest {
    @Test
    void worksWithoutKeys() {
        assertEquals(4, KeyMaze.find(grid("S..", ".#.", "..T")));
        assertEquals(-1, KeyMaze.find(grid("S#T")));
    }

    @Test
    void collectsKeyBeforeDoor() {
        assertEquals(3, KeyMaze.find(grid("SaAT")));
        assertEquals(5, KeyMaze.find(grid("SaAbBT")));
    }

    @Test
    void doorWithoutAccessibleKeyBlocksPath() {
        assertEquals(-1, KeyMaze.find(grid("SAT")));
        assertEquals(-1, KeyMaze.find(grid("SAaT")));
    }

    @Test
    void revisitsCellsWithNewKeyWithoutChangingMap() {
        char[][] map = grid("a.SAT");
        assertEquals(6, KeyMaze.find(map));
        assertArrayEquals(grid("a.SAT"), map);
    }

    @Test
    void canAvoidDoorOrUnusedKey() {
        assertEquals(4, KeyMaze.find(grid("SAT", "...")));
        assertEquals(1, KeyMaze.find(grid("STa")));
    }

    @Test
    void supportsSparseKeyLetters() {
        assertEquals(3, KeyMaze.find(grid("SjJT")));
    }

    @Test
    void supportsAllTenKeysAndDoors() {
        assertEquals(21, KeyMaze.find(grid("SabcdefghijABCDEFGHIJT")));
    }

    @Test
    void handlesMaximumGridWithTenKeys() {
        char[][] map = new char[30][30];
        for (char[] row : map) {
            Arrays.fill(row, '.');
        }
        map[0][0] = 'S';
        map[29][29] = 'T';
        for (int i = 0; i < 10; i++) {
            map[0][i + 1] = (char) ('a' + i);
            map[29][i] = (char) ('A' + i);
        }
        assertEquals(58, KeyMaze.find(map));
    }

    private char[][] grid(String... rows) {
        return Arrays.stream(rows).map(String::toCharArray).toArray(char[][]::new);
    }
}
