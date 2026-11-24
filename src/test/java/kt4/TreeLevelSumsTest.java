package kt4;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TreeLevelSumsTest {
    @Test
    void sumsEachLevel() {
        int[][] nodes = {{-10, 1, 2}, {9, -1, -1}, {20, 3, 4}, {15, -1, -1}, {7, -1, -1}};
        assertArrayEquals(new long[]{-10, 29, 22}, TreeLevelSums.calculate(nodes));
    }

    @Test
    void singleNegativeNode() {
        assertArrayEquals(new long[]{-7}, TreeLevelSums.calculate(new int[][]{{-7, -1, -1}}));
    }

    @Test
    void followsChildIndicesWithoutChangingTree() {
        int[][] nodes = {{5, 3, 1}, {2, -1, -1}, {7, -1, -1}, {3, 2, -1}};
        assertArrayEquals(new long[]{5, 5, 7}, TreeLevelSums.calculate(nodes));
        assertArrayEquals(new int[][]{{5, 3, 1}, {2, -1, -1}, {7, -1, -1}, {3, 2, -1}}, nodes);
    }

    @Test
    void keepsZeroLevelSum() {
        int[][] nodes = {{0, 1, 2}, {-5, -1, -1}, {5, -1, -1}};
        assertArrayEquals(new long[]{0, 0}, TreeLevelSums.calculate(nodes));
    }

    @Test
    void handlesMaximumDepth() {
        int[][] nodes = new int[200_000][3];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new int[]{1, -1, i + 1 < nodes.length ? i + 1 : -1};
        }
        long[] expected = new long[nodes.length];
        Arrays.fill(expected, 1);
        assertArrayEquals(expected, TreeLevelSums.calculate(nodes));
    }

    @Test
    void wideTreeSumsExceedIntRange() {
        int[][] nodes = new int[200_000][3];
        for (int i = 0; i < nodes.length; i++) {
            int left = 2 * i + 1;
            int right = left + 1;
            nodes[i] = new int[]{1_000_000_000,
                    left < nodes.length ? left : -1, right < nodes.length ? right : -1};
        }
        long[] expected = new long[18];
        for (int level = 0; level < 17; level++) {
            expected[level] = (1L << level) * 1_000_000_000;
        }
        expected[17] = 68_929_000_000_000L;
        assertArrayEquals(expected, TreeLevelSums.calculate(nodes));
    }
}
