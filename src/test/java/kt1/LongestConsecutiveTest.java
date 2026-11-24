package kt1;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LongestConsecutiveTest {
    @Test
    void examples() {
        assertEquals(4, LongestConsecutive.length(new int[]{100, 4, 200, 1, 3, 2}));
        assertEquals(9, LongestConsecutive.length(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        assertEquals(0, LongestConsecutive.length(new int[]{}));
    }

    @Test
    void duplicatesDoNotExtendSequence() {
        assertEquals(1, LongestConsecutive.length(new int[]{7, 7, 7}));
    }

    @Test
    void sequenceCanCrossZero() {
        assertEquals(5, LongestConsecutive.length(new int[]{2, -2, 0, -1, 1, 10}));
    }

    @Test
    void handlesValueLimits() {
        assertEquals(2, LongestConsecutive.length(
                new int[]{-1_000_000_000, -999_999_999, 999_999_999, 1_000_000_000}));
    }

    @Test
    void handlesMaximumLength() {
        int[] nums = IntStream.range(0, 200_000).map(i -> 200_000 - i).toArray();
        assertEquals(200_000, LongestConsecutive.length(nums));
    }
}
