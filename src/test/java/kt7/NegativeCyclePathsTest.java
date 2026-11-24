package kt7;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NegativeCyclePathsTest {
    @Test
    void distinguishesFiniteUnreachableAndUnboundedVertices() {
        int[][] edges = {{1, 2, 1}, {2, 3, -2}, {3, 2, 1}, {1, 4, 5}};
        assertArrayEquals(new long[]{0, Long.MIN_VALUE, Long.MIN_VALUE, 5, Long.MAX_VALUE},
                NegativeCyclePaths.find(5, edges, 1));
    }

    @Test
    void propagatesUnboundedCostBeyondCycleButNotBeforeIt() {
        int[][] edges = {{5, 6, 10}, {4, 5, 10}, {3, 4, 10}, {2, 3, -2},
                {3, 2, 1}, {1, 2, 100}, {1, 7, 1}};
        assertArrayEquals(new long[]{0, Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE,
                Long.MIN_VALUE, Long.MIN_VALUE, 1}, NegativeCyclePaths.find(7, edges, 1));
    }

    @Test
    void unreachableNegativeCycleCannotAffectReachableVertices() {
        int[][] edges = {{1, 2, 3}, {3, 4, -1}, {4, 3, -1}, {4, 2, -100}};
        assertArrayEquals(new long[]{0, 3, Long.MAX_VALUE, Long.MAX_VALUE},
                NegativeCyclePaths.find(4, edges, 1));
    }

    @Test
    void negativeCycleCanAffectStart() {
        assertArrayEquals(new long[]{Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE},
                NegativeCyclePaths.find(3, new int[][]{{1, 2, -2}, {2, 1, 1}, {1, 3, 0}}, 1));
        assertArrayEquals(new long[]{Long.MIN_VALUE},
                NegativeCyclePaths.find(1, new int[][]{{1, 1, -1}}, 1));
    }

    @Test
    void zeroCycleAndNegativeFiniteDistanceAreNotUnbounded() {
        int[][] edges = {{1, 2, -5}, {2, 3, 2}, {3, 2, -2}, {1, 2, 1}};
        assertArrayEquals(new long[]{0, -5, -3}, NegativeCyclePaths.find(3, edges, 1));
        assertArrayEquals(new int[][]{{1, 2, -5}, {2, 3, 2}, {3, 2, -2}, {1, 2, 1}}, edges);
    }

    @Test
    void handlesEmptyGraphAndArbitraryStart() {
        assertArrayEquals(new long[]{Long.MAX_VALUE, 0},
                NegativeCyclePaths.find(2, new int[0][3], 2));
        assertArrayEquals(new long[]{0}, NegativeCyclePaths.find(1, new int[0][3], 1));
    }

    @Test
    void finiteCostsCanExceedIntRange() {
        int[][] edges = {{3, 4, -1_000_000_000}, {2, 3, -1_000_000_000}, {1, 2, -1_000_000_000}};
        assertArrayEquals(new long[]{0, -1_000_000_000L, -2_000_000_000L, -3_000_000_000L},
                NegativeCyclePaths.find(4, edges, 1));
    }

    @Test
    void handlesMaximumGraphSizeWithoutOverflowDuringRelaxation() {
        int[][] edges = new int[5_000][3];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new int[]{1, 1, -1_000_000_000};
        }
        long[] result = NegativeCyclePaths.find(2_500, edges, 1);
        assertEquals(Long.MIN_VALUE, result[0]);
        for (int i = 1; i < result.length; i++) {
            assertEquals(Long.MAX_VALUE, result[i]);
        }
    }
}
