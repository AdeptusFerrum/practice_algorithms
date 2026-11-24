package kt9;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AdjacentMergesTest {
    @Test
    void findsCheapestMergeOrderWithoutChangingSizes() {
        int[] sizes = {10, 20, 30};
        assertEquals(90, AdjacentMerges.minCost(sizes));
        assertArrayEquals(new int[]{10, 20, 30}, sizes);
    }

    @Test
    void handlesOneOrTwoFragments() {
        assertEquals(0, AdjacentMerges.minCost(new int[]{1_000_000_000}));
        assertEquals(7, AdjacentMerges.minCost(new int[]{3, 4}));
    }

    @Test
    void cannotMergeNonAdjacentSmallestFragments() {
        assertEquals(22, AdjacentMerges.minCost(new int[]{4, 5, 4}));
    }

    @Test
    void greedySmallestAdjacentPairCanBeWorse() {
        assertEquals(40, AdjacentMerges.minCost(new int[]{6, 4, 4, 6}));
    }

    @Test
    void usesLongForSumsAndCosts() {
        assertEquals(5_000_000_000L, AdjacentMerges.minCost(new int[]{1_000_000_000, 1_000_000_000, 1_000_000_000}));
    }

    @Test
    void handlesMaximumLength() {
        int[] sizes = new int[500];
        Arrays.fill(sizes, 1_000_000_000);
        assertEquals(4_488_000_000_000L, AdjacentMerges.minCost(sizes));
    }

    @Test
    void matchesEnumerationOfAllMergeOrders() {
        Random random = new Random(42);
        for (int test = 0; test < 80; test++) {
            int[] sizes = new int[1 + random.nextInt(7)];
            long[] groups = new long[sizes.length];
            for (int i = 0; i < sizes.length; i++) {
                sizes[i] = 1 + random.nextInt(20);
                groups[i] = sizes[i];
            }
            assertEquals(enumerate(groups), AdjacentMerges.minCost(sizes));
        }
    }

    private long enumerate(long[] groups) {
        if (groups.length == 1) {
            return 0;
        }
        long best = Long.MAX_VALUE;
        for (int i = 0; i < groups.length - 1; i++) {
            long[] merged = new long[groups.length - 1];
            System.arraycopy(groups, 0, merged, 0, i);
            merged[i] = groups[i] + groups[i + 1];
            System.arraycopy(groups, i + 2, merged, i + 1, groups.length - i - 2);
            best = Math.min(best, merged[i] + enumerate(merged));
        }
        return best;
    }
}
