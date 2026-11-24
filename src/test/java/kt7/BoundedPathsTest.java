package kt7;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BoundedPathsTest {
    @Test
    void findsCheapestRouteWithinLimit() {
        int[][] edges = {{1, 3, 9}, {1, 2, 2}, {2, 3, 3}};
        assertEquals(new BoundedPaths.Result(9, 1), BoundedPaths.find(3, edges, 1, 3, 1));
        assertEquals(new BoundedPaths.Result(5, 1), BoundedPaths.find(3, edges, 1, 3, 2));
    }

    @Test
    void cannotUseNewlyComputedDistanceInSameLayer() {
        assertNull(BoundedPaths.find(3, new int[][]{{1, 2, 1}, {2, 3, 1}}, 1, 3, 1));
    }

    @Test
    void countsParallelEdgesAndRoutesOfDifferentLengths() {
        int[][] edges = {{1, 3, 2}, {1, 3, 2}, {1, 2, 1}, {2, 3, 1}};
        assertEquals(new BoundedPaths.Result(2, 3), BoundedPaths.find(3, edges, 1, 3, 2));
        assertEquals(new BoundedPaths.Result(2, 3), BoundedPaths.find(3, edges, 1, 3, 5));
        assertArrayEquals(new int[][]{{1, 3, 2}, {1, 3, 2}, {1, 2, 1}, {2, 3, 1}}, edges);
    }

    @Test
    void zeroEdgeLimitAllowsOnlyEmptyRoute() {
        assertEquals(new BoundedPaths.Result(0, 1),
                BoundedPaths.find(1, new int[][]{{1, 1, -1}}, 1, 1, 0));
        assertNull(BoundedPaths.find(2, new int[][]{{1, 2, 0}}, 1, 2, 0));
    }

    @Test
    void negativeCycleCanBeUsedOnlyWithinEdgeLimit() {
        int[][] edges = {{1, 1, -2}, {1, 2, 5}};
        assertEquals(new BoundedPaths.Result(1, 1), BoundedPaths.find(2, edges, 1, 2, 3));
        assertEquals(new BoundedPaths.Result(-6, 1), BoundedPaths.find(2, edges, 1, 1, 3));
    }

    @Test
    void unreachableTargetAndReverseDirection() {
        assertNull(BoundedPaths.find(2, new int[0][3], 1, 2, 500));
        assertNull(BoundedPaths.find(2, new int[][]{{1, 2, 0}}, 2, 1, 3));
    }

    @Test
    void positiveCostsUseLong() {
        int[][] edges = {{1, 2, 1_000_000_000}, {2, 3, 1_000_000_000}, {3, 4, 1_000_000_000}};
        assertEquals(new BoundedPaths.Result(3_000_000_000L, 1), BoundedPaths.find(4, edges, 1, 4, 3));
    }

    @Test
    void countsEmptyRouteAndZeroCostWalksModuloPrime() {
        int expected = BigInteger.TWO.pow(501).subtract(BigInteger.ONE)
                .mod(BigInteger.valueOf(1_000_000_007)).intValue();
        assertEquals(new BoundedPaths.Result(0, expected),
                BoundedPaths.find(1, new int[][]{{1, 1, 0}, {1, 1, 0}}, 1, 1, 500));
    }

    @Test
    void handlesMaximumSizesAndRepeatedEdges() {
        int[][] edges = new int[10_000][3];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new int[]{2_000, 2_000, -1_000_000_000};
        }
        int expected = BigInteger.valueOf(10_000)
                .modPow(BigInteger.valueOf(500), BigInteger.valueOf(1_000_000_007)).intValue();
        assertEquals(new BoundedPaths.Result(-500_000_000_000L, expected),
                BoundedPaths.find(2_000, edges, 2_000, 2_000, 500));
    }

    @Test
    void matchesEnumerationOfAllShortWalks() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int n = 4;
            int[][] edges = new int[random.nextInt(8)][3];
            for (int[] edge : edges) {
                edge[0] = 1 + random.nextInt(n);
                edge[1] = 1 + random.nextInt(n);
                edge[2] = random.nextInt(7) - 3;
            }
            int start = 1 + random.nextInt(n);
            int target = 1 + random.nextInt(n);
            int limit = random.nextInt(5);
            long[] expected = {Long.MAX_VALUE, 0};
            enumerate(edges, start, target, limit, 0, expected);
            BoundedPaths.Result actual = BoundedPaths.find(n, edges, start, target, limit);
            if (expected[0] == Long.MAX_VALUE) {
                assertNull(actual);
            } else {
                assertEquals(new BoundedPaths.Result(expected[0], (int) expected[1]), actual);
            }
        }
    }

    private void enumerate(int[][] edges, int vertex, int target, int remaining, long cost, long[] best) {
        if (vertex == target) {
            if (cost < best[0]) {
                best[0] = cost;
                best[1] = 1;
            } else if (cost == best[0]) {
                best[1]++;
            }
        }
        if (remaining == 0) {
            return;
        }
        for (int[] edge : edges) {
            if (edge[0] == vertex) {
                enumerate(edges, edge[1], target, remaining - 1, cost + edge[2], best);
            }
        }
    }
}
