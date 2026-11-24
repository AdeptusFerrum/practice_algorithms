package kt4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxTreePathSumTest {
    @Test
    void bestPathCanExcludeRoot() {
        int[][] nodes = {{-10, 1, 2}, {9, -1, -1}, {20, 3, 4}, {15, -1, -1}, {7, -1, -1}};
        assertEquals(42, MaxTreePathSum.find(nodes));
    }

    @Test
    void joinsTwoBranchesThroughRoot() {
        assertEquals(6, MaxTreePathSum.find(new int[][]{{1, 1, 2}, {2, -1, -1}, {3, -1, -1}}));
    }

    @Test
    void allNegativeValuesRequireNonemptyPath() {
        assertEquals(-2, MaxTreePathSum.find(new int[][]{{-8, 1, 2}, {-2, -1, -1}, {-3, -1, -1}}));
        assertEquals(-7, MaxTreePathSum.find(new int[][]{{-7, -1, -1}}));
    }

    @Test
    void skipsNegativeBranch() {
        assertEquals(7, MaxTreePathSum.find(new int[][]{{5, 1, 2}, {-9, -1, -1}, {2, -1, -1}}));
    }

    @Test
    void pathCannotIncludeThreeBranches() {
        int[][] nodes = {{10, 1, -1}, {1, 2, 3}, {8, -1, -1}, {9, -1, -1}};
        assertEquals(20, MaxTreePathSum.find(nodes));
    }

    @Test
    void followsChildIndicesWithoutChangingTree() {
        int[][] nodes = {{5, 3, 1}, {2, -1, -1}, {7, -1, -1}, {3, 2, -1}};
        assertEquals(17, MaxTreePathSum.find(nodes));
        assertArrayEquals(new int[][]{{5, 3, 1}, {2, -1, -1}, {7, -1, -1}, {3, 2, -1}}, nodes);
    }

    @Test
    void handlesMaximumDepthAndLongSum() {
        int[][] nodes = new int[200_000][3];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new int[]{1_000_000_000, i + 1 < nodes.length ? i + 1 : -1, -1};
        }
        assertEquals(200_000_000_000_000L, MaxTreePathSum.find(nodes));
    }
}
