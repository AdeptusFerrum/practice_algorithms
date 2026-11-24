package kt8;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ExactCargoTest {
    @Test
    void choosesFewestLoadsWhenProfitIsEqual() {
        int[][] types = {{2, 4}, {3, 6}, {6, 12}};
        assertEquals(new ExactCargo.Result(12, 1), ExactCargo.find(types, 6));
        assertArrayEquals(new int[][]{{2, 4}, {3, 6}, {6, 12}}, types);
    }

    @Test
    void maximizesProfitBeforeMinimizingCount() {
        assertEquals(new ExactCargo.Result(12, 4), ExactCargo.find(new int[][]{{1, 3}, {4, 10}}, 4));
    }

    @Test
    void mustFillCapacityExactly() {
        assertEquals(new ExactCargo.Result(1, 1), ExactCargo.find(new int[][]{{2, 100}, {3, 1}}, 3));
    }

    @Test
    void reportsUnreachableMass() {
        assertNull(ExactCargo.find(new int[][]{{4, 7}, {6, 13}}, 7));
    }

    @Test
    void reusesTypesAndCombinesDifferentWeights() {
        assertEquals(new ExactCargo.Result(20, 4), ExactCargo.find(new int[][]{{3, 5}}, 12));
        assertEquals(new ExactCargo.Result(17, 2), ExactCargo.find(new int[][]{{3, 10}, {2, 7}}, 5));
    }

    @Test
    void handlesMinimumCapacity() {
        assertEquals(new ExactCargo.Result(9, 1), ExactCargo.find(new int[][]{{1, 4}, {1, 9}}, 1));
    }

    @Test
    void handlesMaximumCapacityAndLongProfit() {
        int[][] types = new int[150][2];
        for (int[] type : types) {
            type[0] = 1;
            type[1] = 1_000_000_000;
        }
        assertEquals(new ExactCargo.Result(200_000_000_000_000L, 200_000), ExactCargo.find(types, 200_000));
    }

    @Test
    void matchesEnumerationOfLoadQuantities() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int capacity = 1 + random.nextInt(12);
            int[][] types = new int[1 + random.nextInt(4)][2];
            for (int[] type : types) {
                type[0] = 1 + random.nextInt(capacity);
                type[1] = 1 + random.nextInt(10);
            }
            long[] best = {Long.MIN_VALUE, Integer.MAX_VALUE};
            enumerate(types, 0, capacity, 0, 0, best);
            ExactCargo.Result actual = ExactCargo.find(types, capacity);
            if (best[0] == Long.MIN_VALUE) {
                assertNull(actual);
            } else {
                assertEquals(new ExactCargo.Result(best[0], (int) best[1]), actual);
            }
        }
    }

    private void enumerate(int[][] types, int index, int remaining, long profit, int count, long[] best) {
        if (index == types.length) {
            if (remaining == 0 && (profit > best[0] || profit == best[0] && count < best[1])) {
                best[0] = profit;
                best[1] = count;
            }
            return;
        }
        for (int quantity = 0; quantity * types[index][0] <= remaining; quantity++) {
            enumerate(types, index + 1, remaining - quantity * types[index][0],
                    profit + (long) quantity * types[index][1], count + quantity, best);
        }
    }
}
