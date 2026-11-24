package kt10;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KthElementTest {
    @Test
    void countsDuplicatesAsSeparatePositions() {
        assertEquals(3, KthElement.find(new int[]{7, 1, 5, 3, 3}, 3));
        assertEquals(3, KthElement.find(new int[]{7, 1, 5, 3, 3}, 2));
    }

    @Test
    void handlesSortedAndReverseSortedArrays() {
        for (int k = 1; k <= 5; k++) {
            assertEquals(k, KthElement.find(new int[]{1, 2, 3, 4, 5}, k));
            assertEquals(k, KthElement.find(new int[]{5, 4, 3, 2, 1}, k));
        }
    }

    @Test
    void handlesFullIntegerRangeWithoutSubtractingValues() {
        int[] values = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE, -1};
        assertEquals(Integer.MIN_VALUE, KthElement.find(values.clone(), 1));
        assertEquals(-1, KthElement.find(values.clone(), 2));
        assertEquals(Integer.MAX_VALUE, KthElement.find(values.clone(), 4));
    }

    @Test
    void handlesSingleElement() {
        assertEquals(-7, KthElement.find(new int[]{-7}, 1));
    }

    @Test
    void handlesMaximumSize() {
        int[] values = new int[5_000_000];
        for (int i = 0; i < values.length; i++) {
            values[i] = values.length - i - 1;
        }
        assertEquals(2_499_999, KthElement.find(values, 2_500_000));
    }

    @Test
    void handlesMaximumArrayOfEqualValues() {
        int[] values = new int[5_000_000];
        Arrays.fill(values, -9);
        assertEquals(-9, KthElement.find(values, 2_500_000));
    }

    @Test
    void matchesSortingForEveryPositionAndPreservesElements() {
        Random random = new Random(42);
        for (int test = 0; test < 100; test++) {
            int[] values = new int[1 + random.nextInt(80)];
            for (int i = 0; i < values.length; i++) {
                values[i] = random.nextInt(21) - 10;
            }
            int[] sorted = values.clone();
            Arrays.sort(sorted);
            for (int k = 1; k <= values.length; k++) {
                int[] working = values.clone();
                assertEquals(sorted[k - 1], KthElement.find(working, k));
                Arrays.sort(working);
                assertArrayEquals(sorted, working);
            }
        }
    }
}
