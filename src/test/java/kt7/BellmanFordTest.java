package kt7;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BellmanFordTest {
    @Test
    void negativeEdgeImprovesPreviouslyReachedVertex() {
        assertArrayEquals(new long[]{0, -5, 5},
                BellmanFord.find(3, new int[][]{{1, 2, 2}, {1, 3, 5}, {3, 2, -10}}, 1));
    }

    @Test
    void handlesReverseEdgeOrder() {
        assertArrayEquals(new long[]{0, -2, -5, -9},
                BellmanFord.find(4, new int[][]{{3, 4, -4}, {2, 3, -3}, {1, 2, -2}}, 1));
    }

    @Test
    void unreachableNegativeCycleDoesNotAffectAnswers() {
        int[][] edges = {{1, 2, 3}, {3, 4, -1}, {4, 3, -1}};
        assertArrayEquals(new long[]{0, 3, Long.MAX_VALUE, Long.MAX_VALUE}, BellmanFord.find(4, edges, 1));
    }

    @Test
    void handlesEmptyGraphAndArbitraryStart() {
        assertArrayEquals(new long[]{0}, BellmanFord.find(1, new int[0][3], 1));
        assertArrayEquals(new long[]{Long.MAX_VALUE, 0, Long.MAX_VALUE},
                BellmanFord.find(3, new int[0][3], 2));
    }

    @Test
    void handlesParallelEdgesAndNonnegativeCyclesWithoutChangingInput() {
        int[][] edges = {{1, 1, 0}, {1, 2, 5}, {1, 2, -2}, {2, 1, 2}};
        assertArrayEquals(new long[]{0, -2}, BellmanFord.find(2, edges, 1));
        assertArrayEquals(new int[][]{{1, 1, 0}, {1, 2, 5}, {1, 2, -2}, {2, 1, 2}}, edges);
    }

    @Test
    void handlesMaximumGraphSizeAndNegativeLongDistances() {
        int n = 2_500;
        int[][] edges = new int[5_000][3];
        for (int i = 0; i < n - 1; i++) {
            int from = n - i - 1;
            edges[2 * i] = new int[]{from, from + 1, -1_000_000_000};
            edges[2 * i + 1] = new int[]{from, from + 1, -1_000_000_000};
        }
        edges[4_998] = new int[]{1, 1, 0};
        edges[4_999] = new int[]{n, n, 0};
        long[] expected = new long[n];
        for (int i = 0; i < n; i++) {
            expected[i] = -i * 1_000_000_000L;
        }
        assertArrayEquals(expected, BellmanFord.find(n, edges, 1));
    }
}
