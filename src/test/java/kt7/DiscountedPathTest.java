package kt7;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountedPathTest {
    @Test
    void roundsDiscountDown() {
        assertEquals(2, DiscountedPath.find(2, new int[][]{{1, 2, 5}}, 1, 2));
        assertEquals(0, DiscountedPath.find(2, new int[][]{{1, 2, 1}}, 1, 2));
    }

    @Test
    void keepsCouponForLaterExpensiveEdge() {
        assertEquals(51, DiscountedPath.find(3, new int[][]{{1, 2, 1}, {2, 3, 100}}, 1, 3));
    }

    @Test
    void couponCanChangeBestRoute() {
        int[][] edges = {{1, 2, 5}, {2, 3, 5}, {1, 3, 11}};
        assertEquals(5, DiscountedPath.find(3, edges, 1, 3));
    }

    @Test
    void couponCannotBeUsedTwice() {
        assertEquals(15, DiscountedPath.find(3, new int[][]{{1, 2, 10}, {2, 3, 10}}, 1, 3));
    }

    @Test
    void emptyRouteAndZeroCostCyclesAreAllowed() {
        assertEquals(0, DiscountedPath.find(1, new int[0][3], 1, 1));
        assertEquals(0, DiscountedPath.find(3,
                new int[][]{{1, 2, 0}, {2, 1, 0}, {2, 3, 0}}, 1, 3));
    }

    @Test
    void unreachableTargetAndReverseDirection() {
        assertEquals(-1, DiscountedPath.find(2, new int[0][3], 1, 2));
        assertEquals(-1, DiscountedPath.find(2, new int[][]{{1, 2, 1}}, 2, 1));
    }

    @Test
    void preservesParallelEdgesAndSelfLoops() {
        int[][] edges = {{1, 1, 1}, {1, 2, 100}, {1, 2, 5}};
        assertEquals(2, DiscountedPath.find(2, edges, 1, 2));
        assertArrayEquals(new int[][]{{1, 1, 1}, {1, 2, 100}, {1, 2, 5}}, edges);
    }

    @Test
    void handlesMaximumVertexCountAndLongCost() {
        int n = 200_000;
        int[][] edges = new int[n - 1][3];
        for (int i = 1; i < n; i++) {
            edges[i - 1] = new int[]{i, i + 1, 1_000_000_000};
        }
        assertEquals(199_998_500_000_000L, DiscountedPath.find(n, edges, 1, n));
    }
}
