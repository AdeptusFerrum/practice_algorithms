package kt4;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RunningMedianTest {
    @Test
    void findsMedianAfterEachNumber() {
        assertArrayEquals(new double[]{1, 1.5, 2, 2.5},
                RunningMedian.calculate(new int[]{1, 2, 3, 4}));
    }

    @Test
    void handlesDescendingOrder() {
        assertArrayEquals(new double[]{4, 3.5, 3, 2.5},
                RunningMedian.calculate(new int[]{4, 3, 2, 1}));
    }

    @Test
    void handlesDuplicates() {
        assertArrayEquals(new double[]{2, 2, 2, 2, 2},
                RunningMedian.calculate(new int[]{2, 2, 1, 3, 2}));
    }

    @Test
    void preservesNegativeHalf() {
        assertArrayEquals(new double[]{-1, -0.5, -1, -1.5},
                RunningMedian.calculate(new int[]{-1, 0, -2, -3}));
    }

    @Test
    void handlesValueLimitsWithoutChangingInput() {
        int[] nums = {1_000_000_000, 1_000_000_000, -1_000_000_000, -1_000_000_000};
        assertArrayEquals(new double[]{1_000_000_000, 1_000_000_000, 1_000_000_000, 0},
                RunningMedian.calculate(nums));
        assertArrayEquals(new int[]{1_000_000_000, 1_000_000_000, -1_000_000_000, -1_000_000_000}, nums);
    }

    @Test
    void singleNumber() {
        assertArrayEquals(new double[]{7}, RunningMedian.calculate(new int[]{7}));
    }

    @Test
    void handlesMaximumLength() {
        int[] nums = IntStream.range(0, 200_000).toArray();
        double[] expected = IntStream.range(0, nums.length).mapToDouble(i -> i / 2.0).toArray();
        assertArrayEquals(expected, RunningMedian.calculate(nums));
    }
}
