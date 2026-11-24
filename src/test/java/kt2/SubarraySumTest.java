package kt2;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubarraySumTest {
    @Test
    void countsOverlappingSubarrays() {
        assertEquals(2, SubarraySum.count(new int[]{1, 1, 1}, 2));
        assertEquals(3, SubarraySum.count(new int[]{1, -1, 0}, 0));
    }

    @Test
    void singleElementAndNoMatch() {
        assertEquals(1, SubarraySum.count(new int[]{0}, 0));
        assertEquals(1, SubarraySum.count(new int[]{-3}, -3));
        assertEquals(0, SubarraySum.count(new int[]{1, 2, 3}, 10));
    }

    @Test
    void countsRepeatedPrefixSums() {
        assertEquals(4, SubarraySum.count(new int[]{1, -1, 1, -1}, 0));
    }

    @Test
    void countCanExceedIntegerRange() {
        assertEquals(20_000_100_000L, SubarraySum.count(new int[200_000], 0));
    }

    @Test
    void prefixSumsAndTargetCanExceedIntegerRange() {
        int[] nums = new int[200_000];
        Arrays.fill(nums, 1_000_000_000);
        assertEquals(100_001, SubarraySum.count(nums, 100_000_000_000_000L));
        Arrays.fill(nums, -1_000_000_000);
        assertEquals(100_001, SubarraySum.count(nums, -100_000_000_000_000L));
    }
}
