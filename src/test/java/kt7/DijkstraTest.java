package kt7;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DijkstraTest {
    @Test
    void findsShortestPathsAndSkipsOutdatedQueueEntries() {
        int[][] edges = {{1, 2, 10}, {1, 3, 1}, {3, 2, 2}, {2, 4, 1}};
        assertArrayEquals(new long[]{0, 3, 1, 4}, Dijkstra.find(4, edges, 1));
    }

    @Test
    void respectsEdgeDirectionAndStartVertex() {
        assertArrayEquals(new long[]{Long.MAX_VALUE, 0, 7},
                Dijkstra.find(3, new int[][]{{1, 2, 2}, {2, 3, 7}}, 2));
    }

    @Test
    void handlesEmptyGraphAndSingleVertex() {
        assertArrayEquals(new long[]{0}, Dijkstra.find(1, new int[0][3], 1));
        assertArrayEquals(new long[]{Long.MAX_VALUE, 0, Long.MAX_VALUE},
                Dijkstra.find(3, new int[0][3], 2));
    }

    @Test
    void handlesParallelEdgesSelfLoopsAndZeroCycles() {
        int[][] edges = {{1, 1, 0}, {1, 2, 9}, {1, 2, 0}, {2, 1, 0}, {2, 3, 4}};
        assertArrayEquals(new long[]{0, 0, 4}, Dijkstra.find(3, edges, 1));
    }

    @Test
    void preservesEdgesAndUsesLongDistances() {
        int[][] edges = {{1, 2, 1_000_000_000}, {2, 3, 1_000_000_000}, {3, 4, 1_000_000_000}};
        assertArrayEquals(new long[]{0, 1_000_000_000L, 2_000_000_000L, 3_000_000_000L},
                Dijkstra.find(4, edges, 1));
        assertArrayEquals(new int[][]{{1, 2, 1_000_000_000}, {2, 3, 1_000_000_000},
                {3, 4, 1_000_000_000}}, edges);
    }

    @Test
    void handlesMaximumGraphSize() {
        int n = 200_000;
        int[][] edges = new int[400_000][3];
        for (int i = 1; i < n; i++) {
            edges[2 * (i - 1)] = new int[]{i, i + 1, 1_000_000_000};
            edges[2 * (i - 1) + 1] = new int[]{i, i + 1, 1_000_000_000};
        }
        edges[399_998] = new int[]{1, 1, 0};
        edges[399_999] = new int[]{n, n, 0};
        long[] expected = new long[n];
        for (int i = 0; i < n; i++) {
            expected[i] = i * 1_000_000_000L;
        }
        assertArrayEquals(expected, Dijkstra.find(n, edges, 1));
    }
}
