package kt2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeIntervalsTest {
    @Test
    void mergesOverlappingAndTouchingIntervals() {
        assertArrayEquals(new int[][]{{1, 7}},
                MergeIntervals.merge(new int[][]{{5, 7}, {1, 3}, {3, 5}}));
    }

    @Test
    void sortsSeparateIntervals() {
        assertArrayEquals(new int[][]{{1, 2}, {3, 4}, {8, 10}},
                MergeIntervals.merge(new int[][]{{8, 10}, {3, 4}, {1, 2}}));
    }

    @Test
    void handlesNestedIntervalsAndDuplicates() {
        assertArrayEquals(new int[][]{{1, 10}},
                MergeIntervals.merge(new int[][]{{1, 10}, {2, 3}, {1, 10}, {4, 8}}));
    }

    @Test
    void emptyArrayAndSinglePoint() {
        assertArrayEquals(new int[0][], MergeIntervals.merge(new int[0][]));
        assertArrayEquals(new int[][]{{3, 3}},
                MergeIntervals.merge(new int[][]{{3, 3}, {3, 3}}));
    }

    @Test
    void handlesValueLimitsWithoutChangingInput() {
        int[][] intervals = {{0, 1_000_000_000}, {-1_000_000_000, 0}};
        assertArrayEquals(new int[][]{{-1_000_000_000, 1_000_000_000}},
                MergeIntervals.merge(intervals));
        assertArrayEquals(new int[][]{{0, 1_000_000_000}, {-1_000_000_000, 0}}, intervals);
    }

    @Test
    void handlesMaximumLength() {
        int[][] intervals = new int[200_000][2];
        for (int i = 0; i < intervals.length; i++) {
            intervals[i][0] = 200_000 - i - 1;
            intervals[i][1] = 200_000 - i;
        }
        assertArrayEquals(new int[][]{{0, 200_000}}, MergeIntervals.merge(intervals));
    }
}
