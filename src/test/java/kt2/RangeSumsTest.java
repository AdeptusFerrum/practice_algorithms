package kt2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RangeSumsTest {
    @Test
    void calculatesInclusiveRanges() {
        assertArrayEquals(new long[]{4, 7, 3, 9}, RangeSums.calculate(
                new int[]{2, -1, 3, 5}, new int[][]{{0, 2}, {1, 3}, {2, 2}, {0, 3}}));
    }

    @Test
    void singleElement() {
        assertArrayEquals(new long[]{-5}, RangeSums.calculate(new int[]{-5}, new int[][]{{0, 0}}));
    }

    @Test
    void handlesZerosAndRepeatedQueries() {
        assertArrayEquals(new long[]{0, 0, 0}, RangeSums.calculate(
                new int[]{0, 0, 0}, new int[][]{{0, 2}, {0, 2}, {1, 1}}));
    }

    @Test
    void sumsCanExceedIntegerRange() {
        int[] nums = new int[200_000];
        Arrays.fill(nums, -1_000_000_000);
        assertArrayEquals(new long[]{-200_000_000_000_000L},
                RangeSums.calculate(nums, new int[][]{{0, 199_999}}));
        Arrays.fill(nums, 1_000_000_000);
        assertArrayEquals(new long[]{200_000_000_000_000L},
                RangeSums.calculate(nums, new int[][]{{0, 199_999}}));
    }

    @Test
    void handlesMaximumNumberOfQueriesWithoutChangingInput() {
        int[] nums = {7};
        int[][] queries = new int[200_000][2];
        long[] expected = new long[200_000];
        Arrays.fill(expected, 7);
        assertArrayEquals(expected, RangeSums.calculate(nums, queries));
        assertArrayEquals(new int[]{7}, nums);
        assertArrayEquals(new int[200_000][2], queries);
    }
}
