package kt5;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSubarrayXorTest {
    @Test
    void bestSubarrayCanCoverWholeArray() {
        assertEquals(7, MaxSubarrayXor.find(new int[]{1, 2, 4}));
    }

    @Test
    void bestSubarrayCanBeInsideArray() {
        assertEquals(7, MaxSubarrayXor.find(new int[]{3, 1, 6, 3}));
    }

    @Test
    void singleNumber() {
        assertEquals(9, MaxSubarrayXor.find(new int[]{9}));
        assertEquals(0, MaxSubarrayXor.find(new int[]{0}));
    }

    @Test
    void handlesZerosAndRepeatedPrefixes() {
        assertEquals(0, MaxSubarrayXor.find(new int[]{0, 0, 0}));
        assertEquals(5, MaxSubarrayXor.find(new int[]{5, 5, 5, 5}));
    }

    @Test
    void handlesAllThirtyBitsWithoutChangingInput() {
        int[] nums = {1 << 29, (1 << 29) - 1};
        assertEquals((1 << 30) - 1, MaxSubarrayXor.find(nums));
        assertArrayEquals(new int[]{1 << 29, (1 << 29) - 1}, nums);
    }

    @Test
    void matchesExhaustiveSearch() {
        Random random = new Random(42);
        for (int test = 0; test < 200; test++) {
            int[] nums = random.ints(1 + random.nextInt(30), 0, 1 << 30).toArray();
            int expected = 0;
            for (int start = 0; start < nums.length; start++) {
                int xor = 0;
                for (int end = start; end < nums.length; end++) {
                    xor ^= nums[end];
                    expected = Math.max(expected, xor);
                }
            }
            assertEquals(expected, MaxSubarrayXor.find(nums));
        }
    }

    @Test
    void handlesMaximumLength() {
        int[] nums = IntStream.range(0, 200_000).toArray();
        assertEquals(262_143, MaxSubarrayXor.find(nums));
    }
}
