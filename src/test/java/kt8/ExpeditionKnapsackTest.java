package kt8;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpeditionKnapsackTest {
    @Test
    void choosesBestSubsetWithoutChangingItems() {
        int[][] items = {{3, 30}, {4, 50}, {5, 60}};
        assertEquals(90, ExpeditionKnapsack.maxValue(items, 8));
        assertArrayEquals(new int[][]{{3, 30}, {4, 50}, {5, 60}}, items);
    }

    @Test
    void usesEachItemAtMostOnce() {
        assertEquals(7, ExpeditionKnapsack.maxValue(new int[][]{{2, 7}}, 6));
    }

    @Test
    void allowsUnusedCapacity() {
        assertEquals(10, ExpeditionKnapsack.maxValue(new int[][]{{3, 10}}, 4));
    }

    @Test
    void identicalItemsCanBothBeTaken() {
        assertEquals(14, ExpeditionKnapsack.maxValue(new int[][]{{2, 7}, {2, 7}}, 4));
    }

    @Test
    void doesNotChooseItemsOnlyByValueOrValuePerWeight() {
        assertEquals(11, ExpeditionKnapsack.maxValue(new int[][]{{1, 2}, {3, 5}, {4, 6}}, 7));
        assertEquals(12, ExpeditionKnapsack.maxValue(new int[][]{{4, 9}, {3, 6}, {3, 6}}, 6));
    }

    @Test
    void handlesMaximumSizesAndLongValues() {
        int[][] items = new int[200][2];
        for (int[] item : items) {
            item[0] = 500;
            item[1] = 1_000_000_000;
        }
        assertEquals(200_000_000_000L, ExpeditionKnapsack.maxValue(items, 100_000));
    }

    @Test
    void matchesEnumerationOfAllSubsets() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int capacity = 1 + random.nextInt(20);
            int[][] items = new int[1 + random.nextInt(10)][2];
            for (int[] item : items) {
                item[0] = 1 + random.nextInt(capacity);
                item[1] = 1 + random.nextInt(30);
            }
            long best = 0;
            for (int mask = 0; mask < 1 << items.length; mask++) {
                int weight = 0;
                long value = 0;
                for (int i = 0; i < items.length; i++) {
                    if ((mask & (1 << i)) != 0) {
                        weight += items[i][0];
                        value += items[i][1];
                    }
                }
                if (weight <= capacity) {
                    best = Math.max(best, value);
                }
            }
            assertEquals(best, ExpeditionKnapsack.maxValue(items, capacity));
        }
    }
}
