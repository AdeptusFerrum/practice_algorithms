package kt4;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SlidingWindowMedianTest {
    @Test
    void findsMedianOfEachWindow() {
        assertArrayEquals(new double[]{1, -1, -1, 3, 5, 6},
                SlidingWindowMedian.calculate(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
    }

    @Test
    void windowOfOneReturnsEachNumber() {
        assertArrayEquals(new double[]{5, -2, 5, 0},
                SlidingWindowMedian.calculate(new int[]{5, -2, 5, 0}, 1));
        assertArrayEquals(new double[]{-3}, SlidingWindowMedian.calculate(new int[]{-3}, 1));
    }

    @Test
    void windowCoversWholeArray() {
        assertArrayEquals(new double[]{2.5}, SlidingWindowMedian.calculate(new int[]{4, 1, 3, 2}, 4));
        assertArrayEquals(new double[]{2}, SlidingWindowMedian.calculate(new int[]{4, 1, 2}, 3));
    }

    @Test
    void removesOnlyOneOccurrence() {
        assertArrayEquals(new double[]{2, 2, 2.5, 2.5},
                SlidingWindowMedian.calculate(new int[]{2, 2, 2, 2, 3, 4, 1}, 4));
    }

    @Test
    void preservesNegativeHalf() {
        assertArrayEquals(new double[]{-0.5, -1, -2.5},
                SlidingWindowMedian.calculate(new int[]{-1, 0, -2, -3}, 2));
    }

    @Test
    void handlesValueLimitsWithoutChangingInput() {
        int[] nums = {1_000_000_000, 1_000_000_000, -1_000_000_000, -1_000_000_000};
        assertArrayEquals(new double[]{1_000_000_000, 0, -1_000_000_000},
                SlidingWindowMedian.calculate(nums, 2));
        assertArrayEquals(new int[]{1_000_000_000, 1_000_000_000, -1_000_000_000, -1_000_000_000}, nums);
    }

    @Test
    void matchesSortingForDifferentWindowSizes() {
        Random random = new Random(42);
        for (int length = 1; length <= 30; length++) {
            int[] nums = random.ints(length, -5, 6).toArray();
            for (int k = 1; k <= length; k++) {
                double[] expected = new double[length - k + 1];
                for (int start = 0; start < expected.length; start++) {
                    int[] window = Arrays.copyOfRange(nums, start, start + k);
                    Arrays.sort(window);
                    expected[start] = ((long) window[(k - 1) / 2] + window[k / 2]) / 2.0;
                }
                assertArrayEquals(expected, SlidingWindowMedian.calculate(nums, k),
                        "length = " + length + ", k = " + k);
            }
        }
    }

    @Test
    void handlesMaximumLengthWithLargeWindow() {
        int[] nums = IntStream.range(0, 200_000).toArray();
        int k = 100_000;
        double[] expected = IntStream.rangeClosed(0, nums.length - k)
                .mapToDouble(start -> start + (k - 1) / 2.0).toArray();
        assertArrayEquals(expected, SlidingWindowMedian.calculate(nums, k));
    }
}
