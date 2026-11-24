package kt9;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectedStationsTest {
    @Test
    void bestSetMayExcludeRootAndInputIsUnchanged() {
        int[] values = {-10, 5, 7, 3};
        int[][] edges = {{1, 2}, {2, 3}, {2, 4}};
        assertEquals(12, ConnectedStations.maxValue(values, edges, 2));
        assertArrayEquals(new int[]{-10, 5, 7, 3}, values);
        assertArrayEquals(new int[][]{{1, 2}, {2, 3}, {2, 4}}, edges);
    }

    @Test
    void mustTakeExactlyKStationsEvenWithNegativeValues() {
        assertEquals(-7, ConnectedStations.maxValue(new int[]{-5, -2, -8}, new int[][]{{1, 2}, {2, 3}}, 2));
    }

    @Test
    void cannotJoinLeavesWithoutTheirConnectingStation() {
        assertEquals(-80, ConnectedStations.maxValue(new int[]{-100, 10, 20}, new int[][]{{1, 2}, {1, 3}}, 2));
    }

    @Test
    void combinesDifferentChildSubtreesOnlyOnce() {
        assertEquals(24, ConnectedStations.maxValue(new int[]{5, 8, 9, 10},
                new int[][]{{4, 1}, {2, 1}, {3, 1}}, 3));
    }

    @Test
    void handlesSingleStationAndKOne() {
        assertEquals(-7, ConnectedStations.maxValue(new int[]{-7}, new int[0][2], 1));
        assertEquals(9, ConnectedStations.maxValue(new int[]{3, -2, 9}, new int[][]{{1, 2}, {2, 3}}, 1));
    }

    @Test
    void wholeTreeIncludesNegativeStations() {
        assertEquals(-4, ConnectedStations.maxValue(new int[]{5, -20, 11}, new int[][]{{1, 2}, {2, 3}}, 3));
    }

    @Test
    void handlesMaximumChainAndLongTotal() {
        int[] values = new int[300];
        Arrays.fill(values, 1_000_000_000);
        int[][] edges = new int[299][2];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new int[]{300 - i, 299 - i};
        }
        assertEquals(300_000_000_000L, ConnectedStations.maxValue(values, edges, 300));
    }

    @Test
    void handlesMaximumStarWithPartialSelection() {
        int[] values = new int[300];
        Arrays.fill(values, 1_000_000_000);
        values[0] = -1_000_000_000;
        int[][] edges = new int[299][2];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new int[]{1, i + 2};
        }
        assertEquals(148_000_000_000L, ConnectedStations.maxValue(values, edges, 150));
    }

    @Test
    void matchesEnumerationOfAllConnectedSetsForEveryK() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int n = 1 + random.nextInt(10);
            int[] values = new int[n];
            int[][] edges = new int[n - 1][2];
            for (int i = 0; i < n; i++) {
                values[i] = random.nextInt(21) - 10;
                if (i > 0) {
                    edges[i - 1] = new int[]{1 + random.nextInt(i), i + 1};
                }
            }
            long[] expected = new long[n + 1];
            Arrays.fill(expected, Long.MIN_VALUE);
            for (int mask = 1; mask < 1 << n; mask++) {
                if (!isConnected(edges, mask)) {
                    continue;
                }
                long sum = 0;
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) != 0) {
                        sum += values[i];
                    }
                }
                int count = Integer.bitCount(mask);
                expected[count] = Math.max(expected[count], sum);
            }
            for (int k = 1; k <= n; k++) {
                assertEquals(expected[k], ConnectedStations.maxValue(values, edges, k));
            }
        }
    }

    private boolean isConnected(int[][] edges, int mask) {
        int reached = Integer.lowestOneBit(mask);
        int previous;
        do {
            previous = reached;
            for (int[] edge : edges) {
                int pair = (1 << (edge[0] - 1)) | (1 << (edge[1] - 1));
                if ((mask & pair) == pair && (reached & pair) != 0) {
                    reached |= pair;
                }
            }
        } while (reached != previous);
        return reached == mask;
    }
}
