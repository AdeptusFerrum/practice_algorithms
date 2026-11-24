package kt9;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IndependentStationsTest {
    @Test
    void choosesBestIndependentSetWithoutChangingInput() {
        int[] values = {5, 1, 4, 10};
        int[][] edges = {{1, 2}, {1, 3}, {2, 4}};
        assertEquals(15, IndependentStations.maxValue(values, edges));
        assertArrayEquals(new int[]{5, 1, 4, 10}, values);
        assertArrayEquals(new int[][]{{1, 2}, {1, 3}, {2, 4}}, edges);
    }

    @Test
    void handlesSingleStationAndEmptySelection() {
        assertEquals(7, IndependentStations.maxValue(new int[]{7}, new int[0][2]));
        assertEquals(0, IndependentStations.maxValue(new int[]{-7}, new int[0][2]));
        assertEquals(0, IndependentStations.maxValue(new int[]{0}, new int[0][2]));
        assertEquals(0, IndependentStations.maxValue(new int[]{-1, -2, -3}, new int[][]{{1, 2}, {2, 3}}));
    }

    @Test
    void canChooseGrandparentAndGrandchild() {
        assertEquals(30, IndependentStations.maxValue(new int[]{10, -100, 20}, new int[][]{{3, 2}, {2, 1}}));
    }

    @Test
    void comparesCenterWithAllLeaves() {
        int[][] edges = {{1, 2}, {1, 3}, {1, 4}};
        assertEquals(12, IndependentStations.maxValue(new int[]{10, 4, 4, 4}, edges));
        assertEquals(20, IndependentStations.maxValue(new int[]{20, 4, 4, 4}, edges));
    }

    @Test
    void handlesMaximumChainWithoutRecursionAndUsesLong() {
        int n = 200_000;
        int[] values = new int[n];
        Arrays.fill(values, 1_000_000_000);
        int[][] edges = new int[n - 1][2];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new int[]{n - i, n - i - 1};
        }
        assertEquals(100_000_000_000_000L, IndependentStations.maxValue(values, edges));
    }

    @Test
    void handlesMaximumStar() {
        int n = 200_000;
        int[] values = new int[n];
        Arrays.fill(values, 1_000_000_000);
        int[][] edges = new int[n - 1][2];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new int[]{i + 2, 1};
        }
        assertEquals(199_999_000_000_000L, IndependentStations.maxValue(values, edges));
    }

    @Test
    void matchesEnumerationOfAllStationSets() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int n = 1 + random.nextInt(12);
            int[] values = new int[n];
            int[][] edges = new int[n - 1][2];
            for (int i = 0; i < n; i++) {
                values[i] = random.nextInt(21) - 10;
                if (i > 0) {
                    edges[i - 1] = new int[]{i + 1, 1 + random.nextInt(i)};
                }
            }
            long best = 0;
            for (int mask = 0; mask < 1 << n; mask++) {
                boolean valid = true;
                for (int[] edge : edges) {
                    if ((mask & (1 << (edge[0] - 1))) != 0 && (mask & (1 << (edge[1] - 1))) != 0) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    long sum = 0;
                    for (int i = 0; i < n; i++) {
                        if ((mask & (1 << i)) != 0) {
                            sum += values[i];
                        }
                    }
                    best = Math.max(best, sum);
                }
            }
            assertEquals(best, IndependentStations.maxValue(values, edges));
        }
    }
}
