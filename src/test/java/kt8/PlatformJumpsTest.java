package kt8;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlatformJumpsTest {
    @Test
    void choosesCheapestCombinationOfJumps() {
        int[] heights = {10, 30, 40, 20};
        assertEquals(30, PlatformJumps.minCost(heights));
        assertArrayEquals(new int[]{10, 30, 40, 20}, heights);
    }

    @Test
    void canSkipExpensivePlatform() {
        assertEquals(0, PlatformJumps.minCost(new int[]{7, 100, 7}));
    }

    @Test
    void handlesTwoPlatformsAndNegativeHeights() {
        assertEquals(2_000_000_000L, PlatformJumps.minCost(new int[]{-1_000_000_000, 1_000_000_000}));
        assertEquals(8, PlatformJumps.minCost(new int[]{-10, -2}));
    }

    @Test
    void equalHeightsCostNothing() {
        assertEquals(0, PlatformJumps.minCost(new int[]{4, 4, 4, 4}));
    }

    @Test
    void handlesMaximumLengthAndLongTotalCost() {
        int[] heights = new int[200_000];
        for (int i = 0; i < heights.length; i++) {
            heights[i] = i / 2 % 2 == 0 ? -1_000_000_000 : 1_000_000_000;
        }
        assertEquals(199_998_000_000_000L, PlatformJumps.minCost(heights));
    }

    @Test
    void matchesEnumerationOfAllShortRoutes() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int[] heights = new int[2 + random.nextInt(10)];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = random.nextInt(41) - 20;
            }
            assertEquals(enumerate(heights, 0), PlatformJumps.minCost(heights));
        }
    }

    private long enumerate(int[] heights, int index) {
        if (index == heights.length - 1) {
            return 0;
        }
        long best = Long.MAX_VALUE;
        for (int next = index + 1; next <= index + 2 && next < heights.length; next++) {
            best = Math.min(best, Math.abs((long) heights[next] - heights[index]) + enumerate(heights, next));
        }
        return best;
    }
}
